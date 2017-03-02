package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginBean implements Serializable {

    private boolean login = false;
    private List<String> groups = new ArrayList<>();
    private int err_code = 0;

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
     *                 2 = felaktigt format för anv.namn och/eller lösenord.
     */
    public void setErrorCode(int err_code) {
        this.err_code = err_code;
    }

    public int getErrorCode() {
        return err_code;
    }

    /**
     * Sätter vilka projektgrupper som finns i databasen.
     * @param groups Stränglista innehållandes grupper.
     */
    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public List<String> getGroups() {
        return groups;
    }

}
