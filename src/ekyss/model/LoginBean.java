package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginBean implements Serializable {

    private boolean login = false;
    private List<String> groups = new ArrayList<>();
    private int err_code = 0;
    private String username = null;
    private String password = null;
    private String group = null;

    /**
     * Sätter status i bönan för att veta om användaren är inloggad eller ej.
     * @param status 1 = inloggadm 0 = ej inloggad
     */
    public void setLogin(boolean status) {
        login = status;
    }

    public boolean isLogin() {
        return login;
    }

    /**
     * Sätter felkod, som används i login.jsp
     * @param err_code 0 = inget felmeddelande, 1 = fel anv.namn och/eller lösenord
     *                 2 = utloggningsmeddelande efter 30 minuter
     *                 3 = meddelande vid aktiv utloggning
     */
    public void setErrorCode(int err_code) {
        if ( (err_code-0)*(err_code-3) <= 0 ) {
            this.err_code = err_code;
        }
    }

    public int getErrorCode() {
        return err_code;
    }

    /**
     * Lägger till en lista av grupper till listan över tillgängliga projektgrupper.
     * @param groups Stränglista innehållandes grupper.
     */
    public void setAllGroups(ArrayList<String> groups) {
        this.groups.addAll(groups);
    }

    /**
     * Lägger till en projektgrupp i listan över tillgängliga projektgrupper.
     * @param group Namn på projektgrupp.
     */
    public void setAllGroups(String group) {
        this.groups.add(group);
    }

    public List<String> getAllGroups() {
        return groups;
    }

    /**
     * Lägger in användarnamn.
     * @param username Användarnamn.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Lägger in lösenord.
     * @param password Lösenord.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Lägger in den projektgrupp användaren loggar in under.
     * @param group Projektgrupp.
     */
    public void setSelectedGroup(String group) {
        this.group = group;
    }

    public String getSelectedGroup() {
        return group;
    }

}
