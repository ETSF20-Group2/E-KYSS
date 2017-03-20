package ekyss.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseHandler {
    Database db;
    Connection conn;
    private final String[] TIMEREPORTCOLUMNS = {"d_11", "i_11", "f_11", "r_11", "t_11",
            "d_12", "i_12", "f_12", "r_12", "t_12",
            "d_13", "i_13", "f_13", "r_13", "t_13",
            "d_14", "i_14", "f_14", "r_14", "t_14",
            "d_15", "i_15", "f_15", "r_15", "t_15",
            "d_16", "i_16", "f_16", "r_16", "t_16",
            "d_17", "i_17", "f_17", "r_17", "t_17",
            "d_18", "i_18", "f_18", "r_18", "t_18",
            "d_19", "i_19", "f_19", "r_19", "t_19",
            "t_21", "t_22", "t_23", "t_30", "t_41",
            "t_42", "t_43", "t_44", "t_100"};

    public DatabaseHandler(){
		/* Endast för test, vet inte riktigt hur det ska se ut här. Tänker att man har
		 * en tom konstrukor och får conn genom getConnection() eller något liknande
		 */
		db = Database.getInstance();
        conn = db.getConnection();
    }

	/*------  LoginServlet ----------------*/
		/* BeanTransaction */

    /**
     * Visar om en viss användare är projektledare för en viss grupp.
     * @param userName användaren
     * @param group gruppen
     * @return true om användaren är projektledare, annars false.
     */
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
     * Kollar om användaren får logga in (rätt användarnamn, lösenord och grupp)
     * @param username Användarnamnet
     * @param password Lösenordet
     * @param group Den valda gruppen (om username != admin)
     * @return true om användaren får logga in, annars false.
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
                printError(e);
                return false;
            }
        }
    }

	/*------ GroupManagementServlet -------*/
		/* BeanTransaction */

    /**
     * Lägger till en projektgrupp i databasen. Gruppnamnet måste vara unikt.
     * @param group Namnet på gruppen som ska skapas.
     * @return true om gruppen kan skapas, annars false (gruppnamn är inte unikt).
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
     * Tar bort en eller flera grupper från databasen.
     * @param groups En vektor med namnen på grupperna som ska raderas.
     * @return true om grupperna raderats, annars false.
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
     * Utser rollen PL (Projektledare) till en användare. Användaren blir då projektledare för en specifik grupp.
     * @param group Namnet på gruppen användaren ska vara ledare för.
     * @param leader Användarnamnet för ledaren.
     * @return true om användaren blev tilldelad rollen PL, annars false.
     */
    public boolean assignLeader(String group, String leader){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("UPDATE MemberOf set role = 'PL' WHERE member = ? AND groupName = ?");
            ps.setString(1, leader);
            ps.setString(2, group);
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
            else{
                ps = conn.prepareStatement("INSERT INTO MemberOf(groupName, userName, role) VALUES(?,?, 'PL')");
                if(ps.executeUpdate() > 0){
                    return true;
                }
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }


    public boolean unAssignLeaders(String[] deleteArray){
        PreparedStatement ps = null;
        String where = " WHERE";
        for(String s:deleteArray){
            where += " member = ? AND groupName = ? OR";
        }
        if(where.endsWith("OR")){
            where = where.substring(0, where.lastIndexOf("OR"));
        }
        try{
            ps = conn.prepareStatement("UPDATE MemberOf SET role = ''" + where);
            int i = 1;
            for(String s: deleteArray){
                String s1[] = new String[2];
                s1 = s.split("\\s");
                ps.setString(i++, s1[1]);
                ps.setString(i++,  s1[0]);
            }
            print(ps);
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch(SQLException e){
            printError(e);
        }
        return false;
    }

	/* BeanFactory */
    /**
     * Hämtar en lista med alla gruppnamn som finns i databasen
     * @return En lista som innehåller namn på alla grupper.
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

    /**
     * Hämtar en lista med all info om alla användare. Varje list-item är en vektor där [] är användarnamn,
     * [1] är lösenord och [2] är email.
     * @return En lista som innehåller all info om användarna.
     */
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

	/*------ UserManagementServlet --------*/
		/* BeanTransaction */

    /**
     * Lägger till en användare i databasen. Användarnamnet måste vara unikt.
     * @param userName Användarnamn
     * @param email E-post
     * @param password Lösenord
     * @return true om användaren har lagts till, annars false (användarnamet fanns redan i databasen).
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
            printError(e);
        }
        return false;
    }

    /**
     * Tar bort en eller flera användare från databasen
     * @param users En vektor som innehåller namnet på användarna som ska tas bort.
     * @return true om alla användare har raderats, annars false.
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
     * Tilldelar en grupp till en användare. En användare kan vara medlem i flera grupper, men
     * bara en gång per grupp.
     * @param userName användarnamn på den som ska bli tilldelad.
     * @param group namn på gruppen som användaren ska tilldelas.
     * @return true om användaren tilldelades gruppen, annars false.
     */
    public boolean assignGroup(String userName, String group){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("INSERT INTO MemberOf(groupName, member) VALUES(?,?)");
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
     * Tilldelar en roll till en användare i en grupp.
     * @param group Den aktuella gruppen.
     * @param assigns Vektor som innehåller strängar som beskriver de roller som ska tilldelas.
     * @return true om tilldelningen lyckades, annars false.
     */
    public boolean assignRoles(String group, String[] assigns){
        PreparedStatement ps = null;
        try{
            int i = 1;
            for(String s:assigns){
                ps = conn.prepareStatement("UPDATE MemberOf SET role = ? WHERE member = ? AND groupName = ?");
                String[] s1 = s.split("\\s");
                ps.setString(1, s1[1]);
                ps.setString(2, s1[0]);
                ps.setString(3, group);
                System.out.print(i + ": ");
                print(ps);
                ps.executeUpdate();
                i++;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Tar bort en användare från en grupp.
     * @param userName Användaren som ska tas bort.
     * @param group Gruppen användaren ska tas bort ifrån
     * @return true om användaren är borttagen, annars false.
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
     * Hämtar en lista med alla användarnamn som finns i databasen.
     * @return En lista som innehåller alla användarnamn.
     */
    public List<String> getUserList(){
        List<String> users = new ArrayList<String>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT userName FROM Users");
            print(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String user = rs.getString("userName");
                if(user.equals("admin")) continue;
                users.add(user);
            }
        } catch (SQLException e){
            printError(e);
        }
        return users;
    }

	/*------ ReportManagementServlet ------*/
			/* BeanTransaction */

    /**
     * Signerar en eller flera tidrapporter. Bara projektledaren kan göra detta.
     * @param group gruppen det gäller.
     * @param sign En vektor som innehåller strängar i formatet "<b><u>vecka</u></b> <b><u>användarnamn</u></b>"
     *             (t.ex. "<b><u>11</u></b> <b><u>Kalle</u></b>").
     *             <br> Beskriver vilka rapporter som ska signeras.
     * @return true om alla rapporter signerats, annars false.
     */
    public boolean signReports(String group, String[] sign){
        PreparedStatement ps = null;
        String where = " WHERE";
        for(String s:sign){
                where += " groupName = ? AND user = ? AND Week = ? OR";
        }

        if(where.endsWith("OR"))
            where = where.substring(0, where.length()-2);
        try {
            ps = conn.prepareStatement("UPDATE TimeReports SET Signed = TRUE" + where);
            int c = 1;
            for (String s : sign) {
                String[] s1 = s.split("\\s");
                ps.setString(c++, group);
                ps.setString(c++, s1[1]);
                ps.setInt(c++, Integer.parseInt(s1[0]));
            }
            print(ps);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Annullerar en eller flera tidrapporter. Bara projektledaren kan göra detta.
     * @param group gruppen det gäller.
     * @param unsign En vektor som innehåller strängar i formatet "<b><u>vecka</u></b> <b><u>användarnamn</u></b>"
     *             (t.ex. "<b><u>11</u></b> <b><u>Kalle</u></b>").
     *             <br> Beskriver vilka rapporter som ska annulleras.
     * @return true om alla rapporter annullerats, annars false.
     */
    public boolean unsignReports(String group, String[] unsign){
        PreparedStatement ps = null;
        String where = " WHERE";
        for(String s : unsign){
            where += " groupName = ? AND user = ? AND Week = ? OR";
        }
        if(where.endsWith("OR"))
            where = where.substring(0, where.length()-2);
        try{
            ps = conn.prepareStatement("UPDATE TimeReports SET Signed = FALSE" + where);
            int c = 1;
            for(String s:unsign){
                String s1[] = s.split("\\s");
                ps.setString(c++, group);
                ps.setString(c++, s1[1]);
                ps.setInt(c++, Integer.parseInt(s1[0]));
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
     * Lägger till en tidrapport i databasen. Om det är projektledarens rapport så signeras den automatiskt.
     * @param user Användaren som rapporten gäller för.
     * @param group Gruppen som rapporten gäller för.
     * @param week Veckan som rapporten gäller för.
     * @param reportValues En Map som beskriver vilka kolumner(aktiviteter) som ska rapporteras.
     *                     <br> Nyckeln är kolumnnamnet och värdet är antalet minuter.
     * @return true om tidrapporten skapats, annars false (fanns redan användare-vecka-grupp par i databasen).
     */
    public boolean createTimeReport(String user, String group, int week, Map<String, Integer> reportValues){
       boolean pl = false;

        PreparedStatement ps = null;
        try{
            // Kolla om användaren är Projektledare.
            ps = conn.prepareStatement("SELECT role FROM MemberOf WHERE member = ? AND groupName = ?");
            ps.setString(1, user);
            ps.setString(2, group);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                pl = "pl".equals(rs.getString("role").toLowerCase());
            }

            String columns = "(user, groupname, week,";
            String values = "VALUES(?, ?, ?,";
            // Om användaren är projektledare, lägg till signed i columns och ett ? i values.
            if(pl){
                columns += " signed,";
                values += " ?,";
            }
            for(String s:reportValues.keySet()){
                columns += s + ", ";
                values += "?,";
            }
            values = values.substring(0, values.lastIndexOf("?")+1);
            columns = columns.substring(0, columns.lastIndexOf(","));
            columns += ")";
            values += ")";


            ps = conn.prepareStatement("INSERT INTO TimeReports" + columns + " " + values);
            int i = 1;
            ps.setString(i++, user);
            ps.setString(i++, group);
            ps.setInt(i++, week);
            // Om användaren är pl måste det extra ? sättas (till true)
            if(pl){
                ps.setBoolean(i++, true);
            }
            for(String s : reportValues.keySet()){
                ps.setInt(i++, reportValues.get(s));
            }
            print(ps);
            if(ps.executeUpdate()>0){
                return true;
            }


        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

    /**
     * Returnerar en lista med alla veckor en användare har rapporterat för som inte är signerade än.
     * @param user Användaren det gäller.
     * @param group Gruppen det gäller.
     * @return En lista med alla veckor som inte är signerade.
     */
    public List<String> getUnsignedReportWeeks(String user, String group){
        List<String> allWeeks = new ArrayList<String>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT week FROM TimeReports WHERE user = ? AND groupName = ? AND signed = FALSE");
            ps.setString(1, user);
            ps.setString(2, group);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                allWeeks.add(rs.getString("week"));
            }
        } catch (SQLException e){
            printError(e);
        }
        return allWeeks;
    }

    /**
     * Returnerar en lista med alla veckor en användare har rapporterat för som är signerade.
     * @param user Användaren det gäller.
     * @param group Gruppen det gäller.
     * @return En lista med alla veckor som är signerade.
     */
    public List<String> getSignedReportWeeks(String user, String group){
        List<String> allWeeks = new ArrayList<String>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT week FROM TimeReports WHERE user = ? AND groupName = ? AND signed = TRUE");
            ps.setString(1, user);
            ps.setString(2, group);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                allWeeks.add(rs.getString("week"));
            }
        } catch (SQLException e){
            printError(e);
        }
        return allWeeks;
    }


    /**
     * Uppdaterar en tidrapport i databasen med nya värden.
     * @param user Användaren som rapporten gäller för.
     * @param group Gruppen som rapporten gäller för.
     * @param week Veckan som rapporten gäller för.
     * @param reportValues En Map som beskriver vilka kolumner(aktiviteter) som ska ändras (även värden som inte ska
     *                     ändras finns med här).
     *                     <br> Nyckeln är kolumnnamnet och värdet är antalet minuter.
     * @return true om rapporten uppdateras, annars false.
     */
    public boolean updateTimeReport(String user, String group, int week, Map<String, Integer> reportValues){
        return removeTimeReport(user, group, week) && createTimeReport(user, group, week, reportValues);
    }

    /**
     * Tar bort en tidrapport från databasen.
     * @param user Användaren som rapporten gäller för.
     * @param group Gruppen som rapporten gäller för.
     * @param week Veckan som rapporten gäller för.
     * @return true om rapporten tagits bort, annars false.
     */
    public boolean removeTimeReport(String user, String group, int week){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("DELETE FROM TimeReports WHERE groupName = ? AND user = ? AND week = ?");
            ps.setString(1, group);
            ps.setString(2, user);
            ps.setInt(3, week);
            print(ps);
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
     * Byter lösenord för en användare.
     * @param userName Användaren det gäller.
     * @param oldPassword Det gamla lösenordet.
     * @param newPassword Det nya lösenordet.
     * @return true om lösenordet har ändrats, annars false (gamla lösenordet var inte rätt).
     */
    public boolean changePassword(String userName, String oldPassword, String newPassword){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT password FROM Users WHERE username = ?");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                if (rs.getString("password").equals(oldPassword)) {
                    ps = conn.prepareStatement("UPDATE Users SET password = ? WHERE userName = ?");
                    ps.setString(1, newPassword);
                    ps.setString(2, userName);
                    print(ps);
                    if (ps.executeUpdate() > 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException e){
            printError(e);
        }
        return false;
    }

		/* BeanFactory */

    /**
     * Hämtar en lista med alla grupper en användare är medlem i och användarens roll i gruppen.
     * @param user Användaren som man vill veta grupperna och rollerna för.
     * @return En lista av vektorer där [0] är gruppnamnet och [1] är användarens roll i den gruppen.
     */
    public List<String[]> getMemberOf(String user){
    	List<String[]> groups = new ArrayList<String[]>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT groupName, role FROM MemberOf WHERE member = ?");
            ps.setString(1, user);
            print(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String[] s = new String[2];
                s[0] = rs.getString("groupName");
                s[1] = rs.getString("role");
                groups.add(s);
            }

        } catch (SQLException e){
            printError(e);
        }

        return groups;
    }

	/*------ DashboardServlet -------------*/
		/* BeanFactory */
    /**
     * Hämtar en tidrapport (eller sammanställning) från databasen. Kan hämta på flera olika sätt beroende på vilka parametrar som är ifyllda.
     * @param group Gruppen som rapporten är för. <b><i><u>(Denna parameter bör alltid vara ifylld)</u></i></b>.
     * @param user Användaren som rapporten ska formas efter. <i>(Denna parameter kan markeras som oifylld med <u>""</u>)</i>
     * @param role Rollen som rapporten ska formas efter. <i>(Denna parameter kan markeras som oifylld med <u>""</u>)</i>
     * @param week Veckan som rapporten ska formas efter. <i>(Denna parameter kan markeras som oifylld med <u>0</u>)</i>
     * @return En Map som innehåller alla kolumner som finns i en tidrapport (även de med value 0).
     * Key i Map är namnet på kolumnen och value är antalet minuter.
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
     * Ger all tid rapporterad för ett specifikt dokument (11,12,...) inom en viss grupp.
     * @param group Gruppen det gäller.
     * @param document Numret på dokumentet.
     * @return Antalet minuter rapporterad för dokumentet.
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
     * Ger all tid rapporterad för ett specifikt dokument (d,i,f,r) inom en viss grupp.
     * @param group Gruppen det gäller.
     * @param activity Bokstaven för aktiviteten.
     * @return A DashboardBean containing an integer describing the time reported to the activity (
     * activitySummary attribute in the bean).
     */
    public int getActivitySummary(String group, String activity){
        if(activity.contains("'"))
            return 0;
        PreparedStatement ps = null;
        int sum = 0;
        try{
            ps = conn.prepareStatement("SELECT SUM(" + activity + "_?) as sum FROM TimeReports WHERE groupName = ?");
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


    /**
     * Returnerar alla signerade rapporter för en viss grupp.
     * @param group Gruppen det gäller.
     * @return En lista med Sträng-vektorer. I vektorn är:
     * <b>[0]</b> = veckan<br>
     * <b>[1]</b> = användaren<br>
     * <b>[2]</b> = tid rapporterad för t_d<br>
     * <b>[3]</b> = tid rapporterad för t_i<br>
     * <b>[4]</b> = tid rapporterad för t_f<br>
     * <b>[5]</b> = tid rapporterad för t_r<br>
     * <b>[6]</b> = tid rapporterad för övrigt (21-100)<br>
     * <b>[7]</b> = total tid rapporterad.
     */
    public List<String[]> getSignedReports(String group){
        PreparedStatement ps = null;
        List<String[]> reports = new ArrayList<String[]>();
        try{
            ps = conn.prepareStatement("SELECT week, user, t_d, t_i, t_f, t_r, total FROM TimeReports WHERE groupName = ? AND signed = TRUE");
            ps.setString(1, group);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String[] s = new String[8];
                int t_d = rs.getInt("t_d");
                int t_i = rs.getInt("t_i");
                int t_f = rs.getInt("t_f");
                int t_r = rs.getInt("t_r");
                int total = rs.getInt("total");
                s[0] = rs.getString("week");
                s[1] = rs.getString("user");
                s[2] = String.valueOf(t_d);
                s[3] = String.valueOf(t_i);
                s[4] = String.valueOf(t_f);
                s[5] = String.valueOf(t_r);
                s[6] = String.valueOf(total-t_d-t_i-t_f-t_r);
                s[7] = String.valueOf(total);
                reports.add(s);
            }
        } catch(SQLException e){
            printError(e);
        }
        return reports;
    }
    /**
     * Returnerar alla signerade rapporter för en viss grupp.
     * @param group Gruppen det gäller.
     * @return En lista med Sträng-vektorer. I vektorn är:
     * <b>[0]</b> = veckan<br>
     * <b>[1]</b> = användaren<br>
     * <b>[2]</b> = tid rapporterad för t_d<br>
     * <b>[3]</b> = tid rapporterad för t_i<br>
     * <b>[4]</b> = tid rapporterad för t_f<br>
     * <b>[5]</b> = tid rapporterad för t_r<br>
     * <b>[6]</b> = tid rapporterad för övrigt (21-100)<br>
     * <b>[7]</b> = total tid rapporterad.
     */
    public List<String[]> getUnsignedReports(String group){
        PreparedStatement ps = null;
        List<String[]> reports = new ArrayList<String[]>();
        try{
            ps = conn.prepareStatement("SELECT week, user, t_d, t_i, t_f, t_r, total FROM TimeReports WHERE groupName = ? AND signed = FALSE");
            ps.setString(1, group);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String[] s = new String[8];
                int t_d = rs.getInt("t_d");
                int t_i = rs.getInt("t_i");
                int t_f = rs.getInt("t_f");
                int t_r = rs.getInt("t_r");
                int total = rs.getInt("total");
                s[0] = rs.getString("week");
                s[1] = rs.getString("user");
                s[2] = String.valueOf(t_d);
                s[3] = String.valueOf(t_i);
                s[4] = String.valueOf(t_f);
                s[5] = String.valueOf(t_r);
                s[6] = String.valueOf(total-t_d-t_i-t_f-t_r);
                s[7] = String.valueOf(total);
                reports.add(s);
            }
        } catch(SQLException e){
            printError(e);
        }
        return reports;
    }

    public List<String[]> getAllPl(){
        List<String[]> allPl = new ArrayList<String[]>();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("SELECT member, groupName from MemberOf WHERE role = 'pl'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String s[] = new String[2];
                s[0] = rs.getString("groupName");
                s[1] = rs.getString("member");
                allPl.add(s);
            }
        } catch(SQLException e){
            printError(e);
        }
        return allPl;
    }

    public List<String[]> getAllMembers(String group){
        List<String[]> members = new ArrayList<String[]>();
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement("SELECT member, role, email FROM Users LEFT JOIN MemberOf ON Users.userName = MemberOf.member WHERE groupName = ?");
            ps.setString(1, group);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String s[] = new String[3];
                s[0] = rs.getString("member");
                s[1] = rs.getString("email");
                s[2] = rs.getString("role");
                members.add(s);
            }
        } catch(SQLException e){
            printError(e);
        }
        return members;
    }

    /**
     * Stänger anslutningen till databasen.
     */
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
