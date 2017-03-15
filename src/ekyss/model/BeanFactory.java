package ekyss.model;

public class BeanFactory {


    public static LoginBean getLoginBean() {
        LoginBean bean = new LoginBean();

        DatabaseHandler db = new DatabaseHandler();

        bean.setAllGroups(db.getAllGroupsList());

        return bean;
    }

}
