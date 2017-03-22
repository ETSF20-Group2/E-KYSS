package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Denna klass är bönan för för webbsidan "Användarinställningar".
 */
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

    private final int ERR_NO_MSG = 0;
    private final int ERR_NOT_MATCHING = 1;
    private final int ERR_WRONG_FORMAT = 2;
    private final int ERR_SAME_AS_OLD = 3;
    private final int ERR_WRONG_PASS = 4;
    private final int SUCCESS = 5;

    /**
     * Sätter felkod, som används för att visa upp meddelanden i vy-nivån.
     * @param err_code <b>0</b>: Inget felmeddelande,
     *             <br><b>1</b>: Lösenorden matchar inte.
     *             <br><b>2</b>: Lösenordet är i fel format.
     *             <br><b>3</b>: Det nya lösenordet är samma som det gamla.
     *             <br><b>4</b>: Det gamla lösenordet var fel.
     *             <br><b>5</b>: Ändringen är lyckad.
     *
     */
    public void setErrorCode(int err_code) {
            this.err_code = err_code;
    }

    /**
     * Hämtar en felkod, som används för att visa upp meddelanden i vy-nivån.
     * @return <b>0</b>: Inget felmeddelande,
     *             <br><b>1</b>: Lösenorden matchar inte.
     *             <br><b>2</b>: Lösenordet är i fel format.
     *             <br><b>3</b>: Det nya lösenordet är samma som det gamla.
     *             <br><b>4</b>: Det gamla lösenordet var fel.
     *             <br><b>5</b>: Ändringen är lyckad.
     *
     */
    public int getErrorCode() {
        return err_code;
    }

    /**
     * Hämtar användarnamnet på användaren som är inloggad.
     * @return Användarnamnet.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sätter användarnamnet på användaren som är inloggad.
     * @param userName Användarnamnet.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Hämtar e-postadressen för användaren som är inloggad.
     * @return e-postadressen
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sätter e-postadressen för användaren som är inloggad.
     * @param email e-postadressen
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Hämtar gruppen som användaren är inloggad för.
     * @return Namnet på gruppen.
     */
    public String getGroup() {
        return group;
    }


    /**
     * Sätter gruppen som användaren är inloggad för.
     * @param group Namnet på gruppen.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Hämtar rollen för användaren i gruppen den är inloggad för.
     * @return Namnet på rollen.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sätter rollen för användaren i gruppen den är inloggad för.
     * @param role Namnet på rollen.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Hämtar en lista av strängvektorer som visar alla grupper användaren är medlem i.
     * @return En lista av strängvektorer, där vektorerna är uppbyggda enligt:
     * <b>[0]</b>: Namnet på gruppen.
     * <br><b>[1]</b>: Användarens roll i gruppen.
     */
    public List<String[]> getGroupList() {
        return groupList;
    }

    /**
     * Sätter en lista av strängvektorer som visar alla grupper användaren är medlem i.
     * @param groupList En lista av strängvektorer, där vektorerna är uppbyggda enligt:
     * <b>[0]</b>: Namnet på gruppen.
     * <br><b>[1]</b>: Användarens roll i gruppen.
     */
    public void setGroupList(List<String[]> groupList) {
        this.groupList = groupList;
    }

    /**
     * Hämtar det gamla lösenordet för användaren.
     * @return Det gamla lösenordet.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sätter det gamla lösenordet för användaren.
     * @param oldPassword Det gamla lösenordet.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Hämtar lösenordet användaren önskar byta till. Måste matcha med newPassword2.
     * @return Det nya lösenordet.
     */
    public String getNewPassword1() {
        return newPassword1;
    }

    /**
     * Sätter lösenordet användaren önskar byta till. Måste matcha med newPassword2.
     * @param newPassword1 Det nya lösenordet.
     */
    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    /**
     * Hämtar lösenordet användaren önskar byta till. Måste matcha med newPassword1.
     * @return Det nya lösenordet.
     */
    public String getNewPassword2() {
        return newPassword2;
    }

    /**
     * Sätter lösenordet användaren önskar byta till. Måste matcha med newPassword1.
     * @param newPassword2 Det nya lösenordet.
     */
    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
