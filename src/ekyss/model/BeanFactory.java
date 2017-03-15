package ekyss.model;

public class BeanFactory {

    /**
     * Returnerar en standardböna av typen LoginBean.
     * @return LoginBean
     */
    public static LoginBean getLoginBean() {
        LoginBean bean = new LoginBean();
        DatabaseHandler db = new DatabaseHandler();
        bean.setAllGroups(db.getAllGroupsList());
        return bean;
    }

    /**
     * Kollar om bönan är behörig att logga in i systemet.
     * @return LoginBean
     */
    public static void checkLoginBean(LoginBean bean) {
        DatabaseHandler db = new DatabaseHandler();

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
