package ekyss.model;

public class BeanTransaction {


	/**
	 * Skapar en ny projektgrupp
	 * @param name Namn på projektgruppen
	 */
	public static void createNewProjectGroup(String name) {
		new DatabaseHandler().addGroup(name);
	}

	public static void deleteProjectGroup(String[] groups) {
		new DatabaseHandler().deleteGroups(groups);
	}


	
	/* LoginServlet */
	
	/**
	 * Checks if the user is able to sign in.
	 * @param bean A LoginBean containing username, password and the group the user
	 * wishes to sign in to (userName, password and group attributes in the bean).
	 * @return true if the user is able to sign in, else false.
	 */
	/*public boolean loginUser(LoginBean bean){
		return db.loginUser(bean.getUsername(), bean.getPassword(), bean.getSelectedGroup());
	}*/
	
	
	/* GroupManagementServlet */
	
	/**
	 * Adds a group to the database.
	 * @param bean A GroupManagementBean containing the name of the group (group attribute in the bean).
	 * @return true if the group is added, else false.
	 */
	/*public boolean addGroup(GroupManagementBean bean){
		return db.addGroup(bean.getGroup());
	}*/
	
	/**
	 * Deletes one or many groups from the database.
	 * @param bean A GroupManagementBean that contains a list of groups (groups attribute in the bean)
	 * @return true if the group(s) is deleted, else false (most likely because some of the groups doesn't
	 * exist in the database.
	 */
	/*public boolean deleteGroups(GroupManagementBean bean){
		return db.deleteGroups(bean.getGroups());
	}*/
	
	/**
	 * Assigns a leader to a group. The user must be a member of the group.
	 * @param bean A GroupManagementBean that contains the Leader and Group attributes.
	 * @return true if the user is assigned leader, else false (most likely because
	 * the user is not in the group).
	 */
	/*public boolean assignLeader(GroupManagementBean bean){
		return db.assignLeader(bean.getGroup(), bean.getLeader());
	}*/
	
	
	/* UserManagementServlet */
	
	/**
	 * Adds a user to the database. Username must be unique.
	 * @param bean A UserManagementBean that contains UserName, Email and Password for the new user.
	 * @return true if the user is added, else false.
	 */
	/*public boolean addUser(UserManagementBean bean){
		return db.addUser(bean.getUserName(), bean.getEmail(), bean.getPassword());
	}*/
	
	/** 
	 * Deletes one or many users from the database.
	 * @param bean A bean that contains a List of the users to be deleted (userList attribute in the Bean).
	 * @return true if all the users are deleted, else false.
	 */
	/*public boolean deleteUsers(UserManagementBean bean){
		return db.deleteUsers(bean.getUserList());
	}*/
	
	/**
	 * Assigns an user to a group. A user can be a member of many groups, but only one time
	 * to the same group.
	 * @param bean A UserManagementBean that contains an username and the group the user should be
	 * assigned to (group and userName attribute in the bean).
	 * @return true if the user is assigned to the group, else false.
	 */
	/*public boolean assignGroup(UserManagementBean bean){
		return db.assignGroup(bean.getGroup(), bean.getUserName());
	}*/
	
	/**
	 * Assigns a role to a user in a group.
	 * @param bean A UserManagementBean that contains the role, username and group for which the role
	 * should be assigned (role, group and userName attributes in the bean).
	 * @return true if the role is assigned, else false.
	 */
	/*public boolean assignRole(UserManagementBean bean){
		return db.assignRole(bean.getGroup(), bean.getUserName(), bean.getRole());
	}*/
	
	/**
	 * Deletes a user from a group.
	 * @param bean A UserManagementBean that contains the username and the group from which the 
	 * user should be deleted (userName and group attributes in the bean).
	 * @return true if the user is deleted from the group, else false.
	 */
	/*public boolean deleteFromGroup(UserManagementBean bean){
		return db.deleteFromGroup(bean.getUserName(), bean.getGroup());
	}*/
	
	
	/* ReportManagementServlet */
	
	/**
	 * Signs one or many time reports. Only the project leader should be able to use this.
	 * @param bean A ReportManagementBean that contains the group name and a map for which weeks
	 * to sign for each user (group and signMap attributes in the bean).
	 * <br><br>
	 * << NOTE >> In the map, the key is a username and the value is a List< Integer> containing all weeks.
	 * @return true if all the reports are signed, else false.
	 */
	/*public boolean signReports(ReportManagementBean bean){
		return db.signReports(bean.getGroup(), bean.getSignMap());
	}*/
	
	/**
	 * Unsigns one or many reports. Only the project leader should be able to do this.
	 * @param bean A ReportManagementBean that contains the group name and a map for which weeks
	 * to unsign for each user (group and signMap attributes in the bean).
	 * <br><br>
	 * << NOTE >> In the map, the key is a username and the value is a List< Integer> containing all weeks.
	 * @return true if all the reports are unsigned, else false.
	 */
	/*public boolean unsignReport(ReportManagementBean bean){
		return db.unsignReports(bean.getGroup(), bean.getSignMap());
	}*/
	
	
	/* ReportServlet */
	
	/** 
	 * Adds a time report to the database. Only adds the values that is in the report Map
	 * @param bean A ReportBean containing username, group, week, and all the columns that 
	 * should be added to the database (user, group, week and reportValues in the bean).
	 * @return true if the time report is added, else false.
	 */
	/*public boolean createTimeReport(ReportBean bean){
		return db.createTimeReport(bean.getUser(), bean.getGroup(), bean.getWeek(), bean.getReportValues());
	}*/
	
	/**
	 * Updates a time report in the database with new values.
	 * @param bean A ReportBean containing username, group, week and all the columns that 
	 * should be updated in the database (user, group, week and reportValues in the bean).
	 * @return true if the time report is updated, else false.
	 */
	/*public boolean updateTimeReport(ReportBean bean){
		return db.updateTimeReport(bean.getUser(), bean.getGroup(), bean.getWeek(), bean.getReportValues());
	}*/
	
	/**
	 * Removes a time report from the database.
	 * @param bean A ReportBean that contains group, username and week for which the time report
	 * should be deleted (group, user and week in the bean).
	 * @return true if the time report is deleted, else false.
	 */
	/*public boolean removeTimeReport(ReportBean bean){
		return db.removeTimeReport(bean.getUser(), bean.getGroup(), bean.getWeek());
	}*/
	
	/* UserServlet */
	
	/**
	 * Changes the password for a user.
	 * @param bean A UserBean that contains username and the new password to use (userName and
	 * password attributes in the bean).
	 * @return true if the password is changed, else false.
	 */
	/*public boolean changePassword(UserBean bean){
		return db.changePassword(bean.getUserName(), bean.getNewPassword());
	}*/
	
}
