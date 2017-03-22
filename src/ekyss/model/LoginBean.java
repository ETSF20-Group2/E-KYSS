package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Denna klass är bönan för för webbapplikationens inloggningssida.
 */
public class LoginBean implements Serializable {

    private boolean login = false;
    private List<String> groups = new ArrayList<>();
    private int err_code = 0;
    private String username = null;
    private String password = null;
    private String group = null;
    private boolean adminLogin = false;

    /**
     * Sätter status i bönan för att veta om användaren är inloggad eller ej.
     * @param status 1 = inloggadm 0 = ej inloggad
     */
    public void setLogin(boolean status) {
        login = status;
    }

    /**
     * Returnerar om en användare är inloggad i systemet.
     * @return true om användaren är inloggad, annars false.
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * Sätter felkod, som används i login.jsp
     * @param err_code 0 = inget felmeddelande, 1 = fel anv.namn och/eller lösenord
     *                 2 = utloggningsmeddelande efter 30 minuter
     *                 3 = meddelande vid aktiv utloggning
     *                 4 = meddelande när användaren loggar in och går bakåt i webbläsaren för att sedan försöka logga in på nytt.
     */
    public void setErrorCode(int err_code) {
        this.err_code = err_code;
    }

    /**
     * Hämtar felkoden, som används i login.jsp
     * @return <b>0</b> = inget felmeddelande
     *         <br><b>1</b>  = fel anv.namn och/eller lösenord
     *         <br><b>2</b>  = utloggningsmeddelande efter 30 minuter
     *         <br><b>3</b>  = meddelande vid aktiv utloggning
     *         <br><b>4</b>  = meddelande när användaren loggar in och går bakåt i webbläsaren för att sedan försöka logga in på nytt.
     */
    public int getErrorCode() {
        return err_code;
    }

    /**
     * Lägger till en lista av grupper till listan över tillgängliga projektgrupper.
     * @param groups Stränglista innehållandes grupper.
     */
    public void setAllGroups(List<String> groups) {
        this.groups.addAll(groups);
    }

    /**
     * Lägger till en projektgrupp i listan över tillgängliga projektgrupper.
     * @param group Namn på projektgrupp.
     */
    public void setAllGroups(String group) {
        this.groups.add(group);
    }

    /**
     * Hämtar en lista av alla tillgängliga projektgrupper.
     * @return En lista av alla tillgängliga projektgrupper.
     */
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

    /**
     * Hämtar ett användarnamn.
     * @return Användarnamn.
     */
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

    /**
     * Hämtar lösenordet.
     * @return Lösenordet.
     */
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

    /**
     * Hämtar den projektgrupp användaren loggar in för.
     * @return Projektgruppen.
     */
    public String getSelectedGroup() {
        return group;
    }

    /**
     * Val av layout för inloggningssidan.
     * @param adminLogin <b>true:</b>: administrationsinloggning
     *               <br><b>false</b>: ordinarie användarinloggning
     */
    public void setAdminLogin(boolean adminLogin) {
        this.adminLogin = adminLogin;
    }

    /**
     * Hämtar om det ska vara administrationsinloggning eller inte.
     * @return <b>true:</b>: administrationsinloggning
     *     <br><b>false</b>: ordinarie användarinloggning
     */
    public boolean getAdminLogin() {
        return adminLogin;
    }

}
