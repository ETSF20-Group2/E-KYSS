package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserManagementBean implements Serializable {

    private List<String[]> list = new ArrayList<>();

    public void setAllUsers(List<String[]> users) {
        list.addAll(users);
    }

    public List<String[]> getAllUsers() {
        return list;
    }

    private int err_code = 0;
    private String username;
    private String password;
    private String group;
    private String role;
    private String type;
    private List<String> userList = new ArrayList<String>();
    private String[] deleteUserList;
    private String[] assignRole;
    private final String[] allRoles = {"PL", "SG", "UG", "TG"};
    private List<String[]> plUserList = new ArrayList<String[]>();
    private String email; //Måste läggas till då man använder det i addUser() metoden.


    /**
     * Hämtar en felkod som används i UserManagement.jsp
     * @return <b>0</b>: Ingen felkod
     *     <br><b>1</b>: Alla användare har tilldelats roller.
     *     <br><b>2</b>: Något blev fel vid tilldelning av roller.
     *     <br><b>3</b>: En användare har skapats.
     *     <br><b>4</b>: Användarnamnet var för långt.
     *     <br><b>5</b>: Användarnamnet var för kort.
     *     <br><b>6</b>: Användarnamnet var ogiltigt.
     *     <br><b>7</b>: Användarna har tagits bort.
     *     <br><b>8</b>: Något gick fel vid borttagning av användare.
     *     <br><b>9</b>: Ingen användare var markerad vid borttagning.
     *     <br><b>10</b>: E-postadressen var ogiltig.
     */
    public int getErr_code() {
        return err_code;
    }

    /**
     * Sätter en felkod som används i UserManagement.jsp
     * @param err_code <b>0</b>: Ingen felkod
     *     <br><b>1</b>: Alla användare har tilldelats roller.
     *     <br><b>2</b>: Något blev fel vid tilldelning av roller.
     *     <br><b>3</b>: En användare har skapats.
     *     <br><b>4</b>: Användarnamnet var för långt.
     *     <br><b>5</b>: Användarnamnet var för kort.
     *     <br><b>6</b>: Användarnamnet var ogiltigt.
     *     <br><b>7</b>: Användarna har tagits bort.
     *     <br><b>8</b>: Något gick fel vid borttagning av användare.
     *     <br><b>9</b>: Ingen användare var markerad vid borttagning.
     *     <br><b>10</b>: E-postadressen var ogiltig.
     */
    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    /**
     * Hämtar vilken typ av formulär informationen skickats ifrån.
     * @return <b>"add"</b>: En ny användare ska läggas till. (admin)
     * <br><b>"delete"</b>: En eller flera användare ska tas bort. (admin)
     * <br><b>"assign"</b>: En eller flera användare ska tilldelas en roll i en viss grupp. (projektledare)
     */
    public String getType() {
        return type;
    }

    /**
     * Sätter vilken typ av formulär informationen skickats ifrån.
     * @param type <b>"add"</b>: En ny användare ska läggas till. (admin)
     * <br><b>"delete"</b>: En eller flera användare ska tas bort. (admin)
     * <br><b>"assign"</b>: En eller flera användare ska tilldelas en roll i en viss grupp. (projektledare)
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Hämtar en strängvektor som visar vilka användare som ska tas bort. <i>(admin)</i>
     * @return En strängvektor där strängarna innehåller användarnamnet.
     */
    public String[] getDeleteUserList(){
        return deleteUserList;
    }

    /**
     * Sätter en strängvektor som visar vilka användare som ska tas bort. <i>(admin)</i>
     * @param deleteUserList En strängvektor där strängarna innehåller användarnamnet.
     */
    public void setDeleteUserList(String[] deleteUserList){
        this.deleteUserList = deleteUserList;
    }

    /**
     * Hämtar användarnamnet för användaren som ska skapas. <i>(admin)</i>
     * @return Användarnamnet.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sätter användarnamnet för användaren som ska skapas. <i>(admin)</i>
     * @param username Användarnamnet.
     */
    public void setUsername(String username){
        this.username = username;
    }


    /**
     * Hämtar lösenordet för användaren som ska skapas. <i>(admin)</i>
     * @return Lösenordet.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sätter lösenordet för användaren som ska skapas. <i>(admin)</i>
     * @param password Lösenordet.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Hämtar en lista av strängar som beskriver alla användare i systemet. <i>(admin)</i>
     * @return En lista över alla användare i systemet.
     */
    public List<String> getUserList() {
        return userList;
    }

    /**
     * Sätter en lista av strängar som beskriver alla användare i systemet. <i>(admin)</i>
     * @param userList En lista över alla användare i systemet.
     */
    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    /**
     * Hämtar e-postadressen för användaren som ska skapas. <i>(admin)</i>
     * @return e-postadressen.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sätter e-postadressen för användaren som ska skapas. <i>(admin)</i>
     * @param email e-postadressen.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Hämtar gruppen som rollerna ska tilldelas för. <i>(projektledare)</i>
     * @return Namnet på gruppen.
     */
    public String getGroup() {
        return group;
    }


    /**
     * Sätter gruppen som rollerna ska tilldelas för. <i>(projektledare)</i>
     * @param group Namnet på gruppen.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Hämtar rollen som användaren har i projektgruppen. <i>(projektledare)</i>
     * @return Rollen som användaren har i projektgruppen.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sätter rollen som användaren har i projektgruppen. <i>(projektledare)</i>
     * @param role Rollen som användaren har i gruppen.
     */
    public void setRole(String role) {
        this.role = role;
    }


    /**
     * Lägger till en användare i listan över alla användare. <i>(projektledare)</i>
     * @param user Användarnamnet
     */
    public void setUserList(String user){
        userList.add(user);
    }


    /**
     * Hämtar en lista av strängar som visar alla användare i en viss grupp. <i>(projektledare)</i>
     * @return En lista av användarnamn.
     */
    public List<String[]> getPlUserList() {
        return plUserList;
    }

    /**
     * Sätter en lista av strängar som visar alla användare i en viss grupp. <i>(projektledare)</i>
     * @param plUserList En lista av användarnamn.
     */
    public void setPlUserList(List<String[]> plUserList) {
        this.plUserList = plUserList;
    }




    /**
     * Hämtar en strängvektor som innehåller alla roller som finns att tilldela (PL, SG, UG, TG) <i>(projektledare)</i>
     * @return en strängvektor som innehåller PL, SG, UG och TG.
     */
    public String[] getAllRoles() {
        return allRoles;
    }

    /**
     * Hämtar en strängvektor som innehåller alla användare och roller som de ska tilldelas <i>(projektledare)</i>
     * @return En strängvektor där strängarna är uppbyggda enligt "<u><b>roll</b></u> <u><b>användarnamn</b></u>".
     */
    public String[] getAssignRole() {
        return assignRole;
    }

    /**
     * Sätter en strängvektor som innehåller alla användare och roller som de ska tilldelas <i>(projektledare)</i>
     * @param assignRole En strängvektor där strängarna är uppbyggda enligt "<u><b>roll</b></u> <u><b>användarnamn</b></u>".
     */
    public void setAssignRole(String[] assignRole) {
        this.assignRole = assignRole;
    }



}
