package ekyss.model;

public class BeanFactory {

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
     * Returnerar en standardböna av typen GroupManagementBean.
     * @return GroupManagementBean
     */
    public static GroupManagementBean getGroupManagementBean() {
        GroupManagementBean bean = new GroupManagementBean();
        // TODO: implementera
        return bean;
    }

    /**
     * Returnerar en standardböna av typen DashboardBean.
     * @return DashboardBean
     */
    public static DashboardBean getDashboardBean() {
        DashboardBean bean = new DashboardBean();
        // TODO: implementera
        return bean;
    }

    /**
     * Returnerar en standardböna av typen ReportBean.
     * @return ReportBean
     */
    public static ReportBean getReportBean() {
        ReportBean bean = new ReportBean();
        // TODO: implementera
        return bean;
    }

    /**
     * Returnerar en standardböna av typen ReportManagementBean.
     * @return ReportManagementBean
     */
    public static ReportManagementBean getReportManagementBean() {
        ReportManagementBean bean = new ReportManagementBean();
        // TODO: implementera
        return bean;
    }

    /**
     * Returnerar en standardböna av typen UserBean.
     * @return UserBean
     */
    public static UserBean getUserBean() {
        UserBean bean = new UserBean();
        // TODO: implementera
        return bean;
    }

    /**
     * Returnerar en standardböna av typen UserManagementBean.
     * @return UserManagementBean
     */
    public static UserManagementBean getUserManagementBean() {
        UserManagementBean bean = new UserManagementBean();
        // TODO: implementera
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

}
