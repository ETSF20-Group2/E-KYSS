package ekyss.model;

public class BeanTransaction {

	/**
	 * Tilldelar roller till en eller flera användare inom en grupp.
	 * @param group Gruppen som tilldelningen sker inom.
	 * @param assigns En vektor som består av strängar med formatet "<b>användarnamn roll</b>"
	 * @return
	 */
	public static boolean assignRoles(String group, String[] assigns){
		return new DatabaseHandler().assignRoles(group, assigns);
	}

	/**
	 * Tilldelar rollen projektledare (PL) över en grupp till en användare
	 * @param bean En GroupManagementBean som måste innehålla attrubuten assignGroupPl och assignGroupPl
	 *             <br> <b><i>assignGroupPl</i></b> innehåller gruppens namn som tilldelningen sker till.
	 *             <br> <b><i>assignUserPl</i></b> innehåller användaren som ska tilldelas rollen.
	 * @return true om tilldelningen lyckades, annars false.
	 */
	public static boolean assignPlToGroup(GroupManagementBean bean){
		return new DatabaseHandler().assignLeader(bean.getAssignGroupPl(), bean.getAssignUserPl());
	}

	public static void deletePlGroup(String[] deleteArray){
		new DatabaseHandler().unAssignLeaders(deleteArray);
	}

	/**
	 * Skapar en ny projektgrupp.
	 * @param name Namn på projektgruppen.
	 */
	public static void createNewProjectGroup(String name) {
		new DatabaseHandler().addGroup(name);
	}


	/**
	 * Tar bort en eller flera projektgrupper.
	 * @param groups En vektor som innehåller namnen på grupperna som ska tas bort.
	 */
	public static void deleteProjectGroup(String[] groups) {
		new DatabaseHandler().deleteGroups(groups);
	}

	/**
	 * Tar bort en eller flera användare.
	 * @param users En vektor som innehåller namnen på användarna som ska tas bort.
	 */
	public static void deleteUsers(String[] users) { new DatabaseHandler().deleteUsers(users); }

	public static boolean changePassword(UserBean b) {
		return new DatabaseHandler().changePassword(b.getUserName(), b.getOldPassword(), b.getNewPassword1());
	}

	/**
	 * Lägger till en användare. Användarnamnet måste vara unikt.
	 * @param bean En UserManagementBean som måste innehålla attributen username, email och password.
	 *             <br><b><i>username</i></b> namnet på användaren som ska skapas.
	 *             <br><b><i>email</i></b> e-post adress för användaren (lösenordet skickas till denna adress).
	 *             <br><b><i>password</i></b> lösenordet som har valts till användaren.
	 * @return true om användaren har lagts till, annars false.
	 */
	public static void addUser(UserManagementBean bean){
		new DatabaseHandler().addUser(bean.getUsername(), bean.getEmail(), bean.getPassword());
	}

	/**
	 * Kopplar användare till en projektgrupp.
	 * @param bean en GroupManagementBean som måste innehålla attributen assignUser och assignGroup.
	 *             <br><b><i>assignUser</i></b> användaren som ska tilldelas en grupp.
	 *             <br><b><i>assignGroup</i></b> gruppen användaren ska tilldelas.
	 * @return true om tilldelingen lyckats, annars false
	 */
	public static boolean assignUserToGroup(GroupManagementBean bean) {
		return new DatabaseHandler().assignGroup(bean.getAssignUser(), bean.getAssignGroup());
	}

	
	/**
	 * Signerar en eller flera rapporter.
	 * @param bean En ReportManagementBean som måste innehålla attributen group och signReport.
	 *             <br><b><i>group</i></b> namnet på gruppen rapporterna ska signeras för.
	 *             <br><b><i>signReport</i></b> en vektor som innehåller vecka och användarnamn på formen
	 *             "<b>week username</b>"
	 * @return true om alla signeringar lyckas, annars false.
	 */
	public static boolean signReports(ReportManagementBean bean){
		return new DatabaseHandler().signReports(bean.getGroup(), bean.getSignReport());
	}
	
	/**
	 * Annullerar en eller flera rapporter
	 * @param bean En ReportManagementBean som måste innehålla attributen group och unsignReport.
	 *             <br><b><i>group</i></b> namnet på gruppen rapporterna ska signeras för.
	 *             <br><b><i>unsignReport</i></b> en vektor som innehåller vecka och användarnamn på formen
	 *             "<b>week username</b>"
	 * @return true om alla annulleringar lyckas, annars false.
	 */
	public static boolean unsignReport(ReportManagementBean bean){
		return new DatabaseHandler().unsignReports(bean.getGroup(), bean.getUnsignReport());
	}


	/**
	 * Skapar en tidrapport. Om det är projektledarens rapport så signeras den automatiskt.
	 * @param bean En ReportBean som måste innehålla attributen user, group, week och reportValues.
	 * 	<br><b><i>user</i></b> Användaren som rapporten gäller för.
	 *  <br><b><i>group</i></b> Gruppen som rapporten gäller för.
	 *  <br><b><i>week</i></b> Veckan som rapporten gäller för.
	 *  <br><b><i>reportValues</i></b> En Map som beskriver vilka kolumner som ska rapporteras.
	 *                     <br>             Nyckeln är kolumnnamnet och värdet är antalet minuter.
	 * @return true om tidrapporten skapats, annars false (fanns redan användare-vecka-grupp par i databasen).
	 */
	public static boolean createTimeReport(ReportBean bean){
		return new DatabaseHandler().createTimeReport(bean.getUser(), bean.getGroup(), bean.getWeek(), bean.getReportValues());
	}

	/**
	 * Uppdaterar en tidrapport.
	 * @param bean En ReportBean som måste innehålla attributen user, group, week och reportValues.
	 * 	<br><b><i>user</i></b> Användaren som rapporten gäller för.
	 *  <br><b><i>group</i></b> Gruppen som rapporten gäller för.
	 *  <br><b><i>week</i></b> Veckan som rapporten gäller för.
	 *  <br><b><i>reportValues</i></b> En Map som beskriver vilka kolumner som ska rapporteras.
	 *                     <br>             Nyckeln är kolumnnamnet och värdet är antalet minuter.
	 * @return true om rapporten uppdateras, annars false.
	 */
	public static boolean updateTimeReport(ReportBean bean){
		return new DatabaseHandler().updateTimeReport(bean.getUser(), bean.getGroup(), bean.getWeek(), bean.getReportValues());
	}

	/**
	 * Tar bort en tidrapport.
	 * @param bean En ReportBean som måste innehålla attributen user, group och week.
	 * 	<br><b><i>user</i></b> Användaren som rapporten gäller för.
	 *  <br><b><i>group</i></b> Gruppen som rapporten gäller för.
	 *  <br><b><i>week</i></b> Veckan som rapporten gäller för.
	 * @return true om rapporten uppdateras, annars false.
	 */
	public static boolean removeTimeReport(ReportBean bean){
		return new DatabaseHandler().removeTimeReport(bean.getUser(), bean.getGroup(), bean.getWeek());
	}
	
}
