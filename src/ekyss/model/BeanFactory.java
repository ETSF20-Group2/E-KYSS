package ekyss.model;

public class BeanFactory {
	DatabaseHandler db = new DatabaseHandler();

	/**
	 * Returnerar en standardböna av typen LoginBean.
	 * @return LoginBean
	 */
	public static LoginBean getLoginBean() {
	    LoginBean bean = new LoginBean();
	    bean.setAllGroups(new DatabaseHandler().getAllGroupsList());
	    return bean;
	}

	/**
	 * Returnerar en standardböna av typen UserManagementBean.
	 */
	public static GroupManagementBean getGroupManagementBean() {
		GroupManagementBean bean = new GroupManagementBean();
		bean.setAllGroups(new DatabaseHandler().getAllGroupsList());
		return bean;
	}

	/**
	 * Returnerar en standardböna av typen UserManagementBean.
	 */
	public static UserManagementBean getUserManagementBean() {
		UserManagementBean bean = new UserManagementBean();
		bean.setAllUsers(new DatabaseHandler().getUserTable());
		return bean;
	}

	/**
	 * Kollar om bönan är behörig att logga in i systemet.
	 * @return LoginBean
	 */
	public static void checkLoginBean(LoginBean bean) {
		bean.setLogin(
				new DatabaseHandler().
						loginUser(
								bean.getUsername(),
								bean.getPassword(),
								bean.getSelectedGroup()
						)
		);
	}

	public static boolean isProjectLeader(LoginBean bean){
		return new DatabaseHandler().isProjectLeader(bean.getUsername(),bean.getSelectedGroup());
	}







	
	    /* GroupManagementServlet */
	
	/**
	 * Function that is used to fetch a list of all the groups in the database.
	 * @return A GroupManagementBean that contains a List<String> of all the groups (as the groups
	 * attribute in bean).
	 */
	/*public static GroupManagementBean getAllGroupsList(){
		GroupManagementBean bean = new GroupManagementBean();
		bean.setGroups(new DatabaseHandler().getAllGroupsList());
		return bean;
	}*/
	
	/**
	 * Function that is used to fetch a list of all the users in the database.
	 * @return A GroupManagementBean that contains a List<String> of all the users (as the users
	 * attribute in bean).
	 */
	/*public GroupManagementBean getUserListG(){
		GroupManagementBean bean = new GroupManagementBean();
		bean.setUsers(db.getUserListG());
		return bean;
	}*/
	
	
	/* UserManagementServlet */
	
	/**
	 * Gets a list of all the users in the database.
	 * @return A UserManagementBean containing a list of all the users (userList attribute in
	 * the bean).
	 */
	/*public UserManagementBean getUserListU(){
		UserManagementBean bean = new UserManagementBean();
		bean.setUserList(db.getUserListU());
		return bean;
	}*/
	
	
	/* DashBoardServlet */
	
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
	/*public DashboardBean getTimeReport(String group, String user, String role, int week){
		DashboardBean bean = new DashboardBean();
		bean.setReportValues(db.getTimeReport(group, user, role, week));
		return bean;
	}*/
	
	/**
	 * Gives all time reported to a specific document (e.g 11,12...)
	 * @param group the group for which to summarize the document.
	 * @param document the number for the document.
	 * @return A DashboardBean containing an integer describing the time reported to the document (
	 * documentSummary attribute in the bean).
	 */
	/*public DashboardBean getDocumentSummary(String group, int document){
		DashboardBean bean = new DashboardBean();
		bean.setDocumentSummary(db.getDocumentSummary(group, document));
		return bean;
	}*/
	
	/**
	 * Gives all time reported to a specific activity (e.g d, i,....)
	 * @param group the group for which to summarize the document.
	 * @param activity the letter for the activity.
	 * @return A DashboardBean containing an integer describing the time reported to the activity (
	 * activitySummary attribute in the bean).
	 */
	/*public DashboardBean getActivitySummary(String group, String activity){
		DashboardBean bean = new DashboardBean();
		bean.setActivitySummary(db.getActivitySummary(group, activity));
		return bean;
	}*/
	
	
	/* UserServlet */
	
	/**
	 * Returns a list containing all groups a user is a member of.
	 * @param user the user for which to give the list.
	 * @return A UserBean that contains a list of all groups the user is member of (groupList attribute
	 * in the bean).
	 */
	/*public UserBean getMemberOf(String user){
		UserBean bean = new UserBean();
		bean.setGroupList(db.getMemberOf(user));
		return bean;
	}*/
}
