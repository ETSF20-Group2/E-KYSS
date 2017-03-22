package ekyss.model;

import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

	/**
	 * Skapar en ReportManagementBean som är fylld med en sorterad lista av alla användares rapporter (förenklat)
	 * @param group Gruppen det gäller.
	 * @return En ReportManagementBean med värden på attributen allSignedReports och allUnsignedReports.
	 */
	public static ReportManagementBean getReportManagementBean(String group){
		Comparator<String[]> reportComparator = new Comparator<String[]>() {
				@Override
				public int compare(String[] o1, String[] o2) {
					Integer f = Integer.parseInt(o1[0]);
					Integer s = Integer.parseInt(o2[0]);
					return f.compareTo(s);
				}
			};

		ReportManagementBean bean = new ReportManagementBean();
		bean.setAllSignedReports(new DatabaseHandler().getSignedReports(group));
		bean.setAllUnsignedReports(new DatabaseHandler().getUnsignedReports(group));
		bean.getAllSignedReports().sort(reportComparator);
		bean.getAllUnsignedReports().sort(reportComparator);
		return bean;
	}




	/**
	 * Returnerar en ReportBean där allWeeks är fylld med veckorna en användare har raporterar till en projekgrupp
	 * @param user användarnamnet
	 * @param group gruppnamnet
	 * @return En ReportBean med värden på attributen user, group och allWeeks.
	 */
	public static ReportBean getReportBean(String user, String group){
		ReportBean bean = new ReportBean();
		bean.setUser(user);
		bean.setGroup(group);
		bean.setAllWeeks(new DatabaseHandler().getUnsignedReportWeeks(user, group));
		return bean;
	}

	public static ReportBean fillReportBean(ReportBean bean, String user, String group, int week){
		bean.setReportValues(new DatabaseHandler().getTimeReport(group, user, "", week));
		return bean;
	}

	/**
	 * Returnerar en böna av typen UserBean, formad efter en specifik användare.
	 * @param user Användaren bönan ska formas efter.
	 * @return En UserBean med värden på attributen username och groupList.
	 */
	public static UserBean getUserBean(String user){
		UserBean bean = new UserBean();
		bean.setUserName(user);
		bean.setGroupList(new DatabaseHandler().getMemberOf(user));
		return bean;
	}

	/**
	 * Returnerar en standardböna av typen LoginBean.
	 * @return En LoginBean med värde på attributet allGroups.
	 */
	public static LoginBean getLoginBean() {
	    LoginBean bean = new LoginBean();
	    bean.setAllGroups(new DatabaseHandler().getAllGroupsList());
	    return bean;
	}

	/**
	 * Returnerar en standardböna av typen UserManagementBean.
	 * @return En GroupManagementBean som med värden på attributen allGroup, allUsers och allPl
	 */
	public static GroupManagementBean getGroupManagementBean() {
		GroupManagementBean bean = new GroupManagementBean();
		bean.setAllGroups(new DatabaseHandler().getAllGroupsList());
		bean.setAllUsers(new DatabaseHandler().getUserList());
		bean.setAllPl(new DatabaseHandler().getAllPl());
		return bean;
	}

	/**
	 * Fyller på en böna med viss information.
	 * @param bean Bönan som ska fyllas på.
	 * @return Den påfyllda bönan. Det som har fyllts på är attributen allPl, allGroupsList ch userList.
	 */
	public static GroupManagementBean fillGroupManagementBean(GroupManagementBean bean) {
	    bean.setAllPl(new DatabaseHandler().getAllPl());
	    bean.setAllGroups(new DatabaseHandler().getAllGroupsList());
	    bean.setAllUsers(new DatabaseHandler().getUserList());
	    return bean;
    }

	/**
	 * Returnerar en standardböna av typen UserManagementBean.
	 * @param group En UserManagementBean som måste innehålla attributet group.
	 * @return En UserManagementBean som innehåller värden på attributet userTable (används för admin) och attributet
	 * allMembers (används för projektledare)
	 */
	public static UserManagementBean getUserManagementBean(String group) {
		UserManagementBean bean = new UserManagementBean();
		bean.setAllUsers(new DatabaseHandler().getUserTable());
		bean.setPlUserList(new DatabaseHandler().getAllMembers(group));
		return bean;
	}

	/**
	 * Kollar om bönan är behörig att logga in i systemet. För att ta reda på om användaren
	 * var behörig kollas bean.getLogin();
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

	/**
	 * Kollan om en användare är projektledare för en viss grupp.
	 * @param bean En LoginBean som måste innehålla username och selectedGroup.
	 * @return true om användaren är projektledare, annars false.
	 */
	public static boolean isProjectLeader(LoginBean bean){
		return new DatabaseHandler().isProjectLeader(bean.getUsername(),bean.getSelectedGroup());
	}

	/**
	 * Genererar en DashboardBean för en specifik användare.
	 * @param user Användaren som bönan ska vara formad för.
	 * @param group Gruppen som bönan ska vara formad för.
	 * @return En DashboardBean som innehåller värden på all tid som rapporterats till en
	 *  specifik grupp av en specifik användare.
	 */
	public static DashboardBean getDashboardBean(String user, String group) {
		DashboardBean bean = new DashboardBean();
		bean.setUser(user);
		bean.setGroup(group);
		Map<String, Integer> map = new DatabaseHandler().getTimeReport(group, user, null, 0);
		bean.setReportValuesSum(map);
		return bean;
	}

	/**
	 * Genererar en DashboardBean för projektledar-sidan. Vilken information som kommer ut beror på
	 * vilken tab man är inne på.
	 * @param tab Aktuell tab på sidan.
	 * @param group Vilken grupp bönan ska hämtas för.
	 * @param user Om den ska formas efter en specifik användare är detta användarnamnet.
	 * @param role Om den ska formas efter en specifik roll så är detta rollen.
	 * @param week Om den ska formas efter en specifik vecka så är detta veckan.
	 * @param stage Om den ska formas efter en viss fas så är detta fasen.
	 * @return En DashboardBean som innehåller en sammanställning av all tid som rapporterats
	 * på formen som önskas.
	 */
	public static DashboardBean getDashboardBeanPL(String tab, String group, String user, String role, String week, String stage) {
		DashboardBean bean = null;

		switch (tab) {
			case "user":
				List<String[]> users = new DatabaseHandler().getAllMembers(group);
				if (user == null) {
					bean = getDashboardBean(users.get(0)[0], group);
				} else {
					bean = getDashboardBean(user, group);
				}
				bean.setUserList(users);
				bean.setGroup(group);
				bean.setTab("user");
				break;
			case "role":
				bean = new DashboardBean();
				bean.setTab("role");
				if (role == null) {
					bean.setRole("PL");
				} else {
					bean.setRole(role);
				}
				bean.setReportValuesSum(new DatabaseHandler().getTimeReport(group, null, bean.getRole(), 0));
				break;
			case "week":
				bean = new DashboardBean();
				bean.setTab("week");
				if (week == null) {
					bean.setReportValuesSum(new DatabaseHandler().getTimeReport(group, null, null, 1));
					bean.setWeek(1);
				} else {
					int w = 1;
					try {
						w = Integer.parseInt(week);
						bean.setWeek(w);
					} catch (NumberFormatException e) {

					}
					bean.setReportValuesSum(new DatabaseHandler().getTimeReport(group, null, null, w));
				}
				List<Integer> w = new DatabaseHandler().getReportWeeks(group);
				Collections.sort(w);
				w = w.stream().distinct().collect(Collectors.toList());
				bean.setWeeks(w);
				break;
			default:
				bean = new DashboardBean();
				bean.setTab("all");
				bean.setReportValuesSum(new DatabaseHandler().getTimeReport(group, null, null, 0));
				break;
		}
		return bean;
	}
}
