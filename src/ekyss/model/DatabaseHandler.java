package ekyss.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    Database db;
    Connection conn;
    private final String[] TIMEREPORTCOLUMNS = {"11_d", "11_i", "11_f", "11_r", "11_t",
            "12_d", "12_i", "12_f", "12_r", "12_t",
            "13_d", "13_i", "13_f", "13_r", "13_t",
            "14_d", "14_i", "14_f", "14_r", "14_t",
            "15_d", "15_i", "15_f", "15_r", "15_t",
            "16_d", "16_i", "16_f", "16_r", "16_t",
            "17_d", "17_i", "17_f", "17_r", "17_t",
            "18_d", "18_i", "18_f", "18_r", "18_t",
            "19_d", "19_i", "19_f", "19_r", "19_t",
            "21_t", "22_t", "23_t", "30_t", "41_t",
            "42_t", "43_t", "44_t", "100_t"};

    public DatabaseHandler(){
		/* Endast för test, vet inte riktigt hur det ska se ut här. Tänker att man har
		 * en tom konstrukor och får conn genom getConnection() eller något liknande
		 */
		db = Database.getInstance();
        conn = db.getConnection();
    }

	/*------  LoginServlet ----------------*/
		/* BeanTransaction */


    public boolean isProjectLeader(String userName, String group){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT role FROM MemberOf WHERE member = ? AND groupName = ?");
            ps.setString(1, userName);
            ps.setString(2, group);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String role = rs.getString("role");
                if(role != null && role.equals("PL"))
                    return true;
                return false;
            }
        } catch(SQLException e){
            printError(e);
        }
        return false;
    }
    /**
     * Function that checks if the user is able to log in (If username is in database and matches
     * with the entered password and the selected group).
     * @param username The username
     * @param password The password
     * @param group The selected group (if the user is not admin)
     * @return True if the user is able to log in, else false.
     */
    public boolean loginUser(String username, String password, String group){
        PreparedStatement ps = null;
        System.out.println(username);
        System.out.println(password);
        System.out.println(group);
        if(group == null) {
            if (username.equals("admin") && password.equals("adminp")) {
                return true;
            } else {
                return false;
            }
        } else {
            try {
                ps = conn.prepareStatement("SELECT * FROM Users LEFT JOIN MemberOf ON member = userName WHERE userName = ? AND password = ? AND groupName = ?");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, group);
                ResultSet rs = ps.executeQuery();
                print(ps);
                if (rs.next()) {
                    System.out.print(rs.getString("username") + "\t");
                    System.out.print(rs.getString("password") + "\t");
                    System.out.println(rs.getString("groupName"));
                    return true;
                }
                return false;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
                System.out.println(e.getErrorCode());
                return false;
            }
        }
    }

	/*------ GroupManagementServlet -------*/
		/* BeanTransaction */

    /**
     * Function that adds a group to the database. Group name must be unique.
     * @param group A GroupManagementBean that contains the groupName (group attribute in bean).
     * @return true if the group is added, else false (most likely because the group name already
     * exists).
     */
    public boolean addGroup(String group){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ProjectGroups(groupName) VALUES(?)");
            ps.setString(1, group);
            print(ps);
            if(ps.executeUpdate() > 0)
                return true;

        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Deletes one or many groups from the database.
     * @param groups A GroupManagementBean that contains a list of groups (groups attribute in the bean)
     * @return true if the group(s) is deleted, else false (most likely because some of the groups doesn't
     * exist in the database.
     */
    public boolean deleteGroups(String[] groups){
        String sql = "WHERE ";
        for(String s:groups){
            sql += "GroupName = ? OR ";
        }
        if(sql.endsWith(" OR ")){
            sql = sql.substring(0, sql.length()-4);
        }
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("DELETE FROM ProjectGroups " + sql);
            int i = 1;
            for(String s: groups){
                ps.setString(i, s);
                i++;
            }
            print(ps);
            if(ps.executeUpdate() > 0)
                return true;
        } catch(SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Assigns a leader to a group. The user must be a member of the group.
     * @param group The Leader attributes.
     * @param leader The Leader attributes.
     * @return true if the user is assigned leader, else false (most likely because
     * the user is not in the group).
     */
    public boolean assignLeader(String group, String leader){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("UPDATE MemberOf set role = 'PG' WHERE userName = ? AND groupName = ?");
            ps.setString(1, leader);
            ps.setString(2, group);
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
            else{
                ps = conn.prepareStatement("INSERT INTO MemberOf(groupName, userName, role) VALUES(?,?, 'PG')");
                if(ps.executeUpdate() > 0){
                    return true;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
        }
        return false;
    }

	/* BeanFactory */
    /**
     * Function that is used to fetch a list of all the groups in the database.
     * @return A List<String> of all the groups.
     */
    public List<String> getAllGroupsList(){
        PreparedStatement ps = null;
        List<String> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement("SELECT * FROM ProjectGroups");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("groupName"));
            }
        } catch(SQLException e){
            printError(e);
        }
        return list;
    }

    public List<String[]> getUserTable() {
        List<String[]> list = new ArrayList<>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT * FROM Users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String[] user = new String[3];
                user[0] = rs.getString("userName");
                user[1] = rs.getString("password");
                user[2] = rs.getString("email");
                list.add(user);
            }
        } catch(SQLException e){
            printError(e);
        }
        return list;
    }

    /**
     * Function that is used to fetch a list of all the users in the database.
     * @return A GroupManagementBean that contains a List<String> of all the users (as the users
     * attribute in bean).
     */
    public List<String> getUserListG(){
        PreparedStatement ps = null;
        List<String> users = new ArrayList<String>();
        try{
            ps = conn.prepareStatement("SELECT * FROM Users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                users.add(rs.getString("userName"));
            }
        } catch(SQLException e){
            printError(e);
        }
        return users;
    }

	/*------ UserManagementServlet --------*/
		/* BeanTransaction */

    /**
     * A function that adds a user to the database. Username must be unique.
     * @param userName
     * @param email
     * @param password
     * @return true if the user is added, else false.
     */
    public boolean addUser(String userName, String email, String password){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("INSERT INTO Users(userName, email, password) VALUES (?,?,?)");
            ps.setString(1, userName);
            ps.setString(2, email);
            ps.setString(3, password);
            print(ps);
            if(ps.executeUpdate() > 0)
                return true;
        } catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
        }
        return false;
    }

    /**
     * Deletes one or many users from the database.
     * @param users A list of the users to be deleted.
     * @return true if all the users are deleted, else false.
     */
    public boolean deleteUsers(String[] users){
        String where = " WHERE";
        for(String s: users){
            where += " userName = ? OR";
        }
        if(where.endsWith("OR")){
            where = where.substring(0, where.length()-2);
        }
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement("DELETE FROM Users" + where);
            int i = 1;
            for(String s : users)
                ps.setString(i++, s);
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch(SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Assigns an user to a group. A user can be a member of many groups, but only one time
     * to the same group.
     * @param group A UserManagementBean that contains an username and the group the user should be
     * assigned to (group and userName attribute in the bean).
     * @return true if the user is assigned to the group, else false.
     */
    public boolean assignGroup(String group, String userName){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("INSERT INTO memberOf(groupName, userName) VALUES(?,?)");
            ps.setString(1, group);
            ps.setString(2, userName);
            print(ps);
            if(ps.executeUpdate() > 0)
                return true;
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Assigns a role to a user in a group.
     * @param group A UserManagementBean that contains the role, username and group for which the role
     * should be assigned (role, group and userName attributes in the bean).
     * @return true if the role is assigned, else false.
     */
    public boolean assignRole(String group, String userName, String role){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("UPDATE memberOf SET role = ? WHERE groupName = ? AND userName = ?");
            ps.setString(1, role);
            ps.setString(2, group);
            ps.setString(3, userName);
            print(ps);
            if(ps.executeUpdate() > 0)
                return true;
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Deletes a user from a group.
     * @param group A UserManagementBean that contains the username and the group from which the
     * user should be deleted (userName and group attributes in the bean).
     * @return true if the user is deleted from the group, else false.
     */
    public boolean deleteFromGroup(String userName, String group){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("DELETE FROM MemberOf WHERE UserName = ? AND groupName = ?");
            ps.setString(1, userName);
            ps.setString(2, group);
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

		/* BeanFactory */

    /**
     * Gets a list of all the users in the database.
     * @return A UserManagementBean containing a list of all the users (userList attribute in
     * the bean).
     */
    public List<String> getUserListU(){
        List<String> users = new ArrayList<String>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT userName FROM Users");
            print(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                users.add(rs.getString("userName"));
            }
        } catch (SQLException e){
            printError(e);
        }
        return users;
    }

	/*------ ReportManagementServlet ------*/
			/* BeanTransaction */

    /**
     * Signs one or many time reports. Only the project leader should be able to use this.
     * @param group A ReportManagementBean that contains the group name and a map for which weeks
     * to sign for each user (group and signMap attributes in the bean).
     * <br><br>
     * << NOTE >> In the map, the key is a username and the value is a List< Integer> containing all weeks.
     * @return true if all the reports are signed, else false.
     */
    public boolean signReports(String group, Map<String, List<Integer>> signMap){
        PreparedStatement ps = null;
        String where = " WHERE";
        for(String s:signMap.keySet()){
            for(int i:signMap.get(s)){
                where += " groupName = ? AND user = ? AND Week = ? OR";
            }
        }
        if(where.endsWith("OR"))
            where = where.substring(0, where.length()-2);
        try{
            ps = conn.prepareStatement("UPDATE TimeReports SET Signed = TRUE" + where);
            int c = 1;
            for(String s:signMap.keySet()){
                for(int i:signMap.get(s)){
                    ps.setString(c++, group);
                    ps.setString(c++, s);
                    ps.setInt(c++, i);
                }
            }
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Unsigns one or many reports. Only the project leader should be able to do this.
     * @param group A ReportManagementBean that contains the group name and a map for which weeks
     * to unsign for each user (group and signMap attributes in the bean).
     * <br><br>
     * << NOTE >> In the map, the key is a username and the value is a List< Integer> containing all weeks.
     * @return true if all the reports are unsigned, else false.
     */
    public boolean unsignReports(String group, Map<String, List<Integer>> signMap){
        PreparedStatement ps = null;
        String where = " WHERE";
        for(String s:signMap.keySet()){
            for(int i:signMap.get(s)){
                where += " groupName = ? AND user = ? AND Week = ? OR";
            }
        }
        if(where.endsWith("OR"))
            where = where.substring(0, where.length()-2);
        try{
            ps = conn.prepareStatement("UPDATE TimeReports SET Signed = FALSE" + where);
            int c = 1;
            for(String s:signMap.keySet()){
                for(int i:signMap.get(s)){
                    ps.setString(c++, group);
                    ps.setString(c++, s);
                    ps.setInt(c++, i);
                }
            }
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

	/*------ ReportServlet ----------------*/
			/* BeanTransaction */

    /**
     * Adds a time report to the database. Only adds the values that is in the report Map
     * @param group A ReportBean containing username, group, week, and all the columns that
     * should be added to the database (user, group, week and reportValues in the bean).
     * @return true if the time report is added, else false.
     */
    public boolean createTimeReport(String user, String group, int week, Map<String, Integer> reportValues){
        String columns = "(user, groupname, week,";
        String values = "VALUES(?, ?, ?,";
        for(String s:reportValues.keySet()){
            columns += s + ", ";
            values += "?,";
        }

        values = values.substring(0, values.lastIndexOf("?")+1);
        columns = columns.substring(0, columns.lastIndexOf(","));
        columns += ")";
        values += ")";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("INSERT INTO TimeReports" + columns + " " + values);
            int i = 1;
            ps.setString(i++, user);
            ps.setString(i++, group);
            ps.setInt(i++, week);
            for(String s : reportValues.keySet()){
                ps.setInt(i++, reportValues.get(s));
            }
            print(ps);
            if(ps.executeUpdate()>0)
                return true;

        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Updates a time report in the database with new values.
     * @param group A ReportBean containing username, group, week and all the columns that
     * should be updated in the database (user, group, week and reportValues in the bean).
     * @return true if the time report is updated, else false.
     */
    public boolean updateTimeReport(String user, String group, int week, Map<String, Integer> reportValues){
        return removeTimeReport(user, group, week) && createTimeReport(user, group, week, reportValues);
    }

    /**
     * Removes a time report from the database.
     * @param group A ReportBean that contains group, username and week for which the time report
     * should be deleted (group, user and week in the bean).
     * @return true if the time report is deleted, else false.
     */
    public boolean removeTimeReport(String user, String group, int week){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("DELETE FROM TimeReports WHERE groupName = ? AND user = ? AND week = ?");
            ps.setString(1, group);
            ps.setString(2, user);
            ps.setInt(3, week);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

	/*------ UserServlet ------------------*/
		/* BeanTransaction */

    /**
     * Changes the password for a user.
     * @param userName A UserBean that contains username and the new password to use (userName and
     * password attributes in the bean).
     * @return true if the password is changed, else false.
     */
    public boolean changePassword(String userName, String newPassword){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE Users SET password = ? WHERE userName = ?");
            ps.setString(1, newPassword);
            ps.setString(2, userName);
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

		/* BeanFactory */

    /**
     * Returns a list containing all groups a user is a member of.
     * @param user the user for which to give the list.
     * @return A UserBean that contains a list of all groups the user is member of (groupList attribute
     * in the bean).
     */
    public List<String> getMemberOf(String user){
    	List<String> groups = new ArrayList<String>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT groupName FROM memberOf WHERE userName = ?");
            ps.setString(1, user);
            print(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                groups.add(rs.getString("groupName"));
        } catch (SQLException e){
            printError(e);
        }

        return groups;
    }

	/*------ DashboardServlet -------------*/
		/* BeanFactory */
    /**
     * Gets a time report or a time report summary. This method can be called in
     * a number of different ways and give different results. This depends on which
     * parameters are filled.
     * @param group The group for which the summary is to. <b><i><u>(This parameter should always be filled)</u></i></b>.
     * @param user The user for which the summary are formed after. <i>(This parameter can be marked as unfilled with <u>""</u>)</i>
     * @param role The role for which the summary are formed after. <i>(This parameter can be marked as unfilled with <u>""</u>)</i>
     * @param week The week for which the summary are formed after. <i>(This parameter can be marked as unfilled with <u>0</u>)</i>
     * @return A DashboardBean containing all columns that are present in a time report (even those with values 0).
     * The columns are placed in a Map with the column as the key and the amount of time as the value (reportValues attribute
     * in the bean).
     */
    public Map<String, Integer> getTimeReport(String group, String user, String role, int week){
        Map<String, Integer> reportValues = new HashMap<String, Integer>();
        PreparedStatement ps = null;
        group = group == null? "":group;
        user = user == null? "":user;
        role = role == null? "":role;
        try{
            ps = conn.prepareStatement("CALL sumHelp(?, ?, ?, ?)");
            ps.setString(1, group);
            ps.setString(2, user);
            ps.setString(3, role);
            ps.setInt(4, week);
            print(ps);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                for(String s:TIMEREPORTCOLUMNS){
                    reportValues.put(s, rs.getInt(s));
                }
            }
        } catch (SQLException e){
            printError(e);
        }
        return reportValues;
    }

    /**
     * Gives all time reported to a specific document (e.g 11,12...)
     * @param group the group for which to summarize the document.
     * @param document the number for the document.
     * @return A DashboardBean containing an integer describing the time reported to the document (
     * documentSummary attribute in the bean).
     */
    public int getDocumentSummary(String group, int document){
        int docSummary = 0;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT SUM(?_t) as sum FROM TimeReports WHERE groupName = ?");
            ps.setInt(1, document);
            ps.setString(2, group);
            print(ps);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                docSummary = rs.getInt("sum");

        } catch (SQLException e){
            printError(e);
        }
        return docSummary;
    }

    /**
     * Gives all time reported to a specific activity (e.g d, i,....)
     * @param group the group for which to summarize the document.
     * @param activity the letter for the activity.
     * @return A DashboardBean containing an integer describing the time reported to the activity (
     * activitySummary attribute in the bean).
     */
    public int getActivitySummary(String group, String activity){
        if(activity.contains("'"))
            return 0;
        PreparedStatement ps = null;
        int sum = 0;
        try{
            ps = conn.prepareStatement("SELECT SUM(?_" + activity + ") as sum FROM TimeReports WHERE groupName = ?");
            ps.setString(2, group);
            ResultSet rs = null;
            for(int i = 11; i<20; i++){
                ps.setInt(1, i);
                print(ps);
                rs = ps.executeQuery();
                if(rs.next()){
                    sum += rs.getInt("sum");
                }
            }
        } catch(SQLException e){
            printError(e);
        }
        return sum;
    }


    public void disconnect(){
        db.close();
    }

    private void print(PreparedStatement ps){
        String s = ps.toString();
        int i = 0;
        if(s.indexOf("SELECT") >= 0)
            i = s.indexOf("SELECT");
        else if(s.indexOf("UPDATE") >= 0)
            i = s.indexOf("UPDATE");
        else if(s.indexOf("DELETE") >= 0)
            i = s.indexOf("DELETE");
        else if(s.indexOf("INSERT") >=0)
            i = s.indexOf("INSERT");
        else if(s.indexOf("CALL") >= 0)
            i = s.indexOf("CALL");

        System.out.println(s.substring(i));
    }

    private void printError(SQLException e){
        System.out.println(e.getMessage());
    }
}
