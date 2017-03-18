package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserBean implements Serializable {
    private String userName;
    private String email;
    private String group;
    private String role;
    private List<String[]> groupList = new ArrayList<String[]>();
    private int err_code = 0;

    private String type;

    private String oldPassword;
    private String newPassword1;
    private String newPassword2;

    /**
     * Sätter felkod, som används för att visa upp meddelanden i vy-nivån.
     * @param err_code 0 = inget felmeddelande,
     *                 1 = Fel lösenord
     *                 2 = Lösenorden matchar inte.
     */
    public void setErrorCode(int err_code) {
      // if ( (err_code-0)*(err_code-2) <= 0 ) {
            this.err_code = err_code;
      //  }
    }

    public int getErrorCode() {
        return err_code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String[]> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String[]> groupList) {
        this.groupList = groupList;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
