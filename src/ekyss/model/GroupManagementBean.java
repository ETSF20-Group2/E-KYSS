package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupManagementBean implements Serializable {

    private List<String> groups = new ArrayList<>();
    private List<String> users = new ArrayList<>();
    private String type = null;
    private String groupName = null;
    private int err_code = 0;
    private String[] deleteGroup;
    private String assignGroup;
    private String assignUser;
    private String assignGroupPl;
    private String assignUserPl;
    private String tab = null;
    private List<String[]> allPl = new ArrayList<String[]>();
    private String[] removePl;

    /**
     * Sätter en lista innehållandes samtliga existerande grupper i databasen.
     * @param groups En lista innahållandes samtliga existerande grupper i databasen.
     */
    public void setAllGroups(List<String> groups) {
        this.groups = groups;
    }

    /**
     * Hämtar en lista innehållandes samtliga existerande grupper i databasen.
     * @return En lista innehållandes samtliga existerande grupper.
     */
    public List<String> getAllGroups() {
        return groups;
    }


    private final String TYPE_ADD = "add";
    private final String TYPE_DELETE = "delete";
    private final String TYPE_ASSIGN = "assign";
    private final String TYPE_ASSIGNPL = "assignPl";
    private final String TYPE_DELETEPL = "deletePl";

    /**
     * Sätt vilken form av formulär som skickas: 'add' eller 'delete'.
     * 'add'-formulär: är den formulär som användas för att skapa en grupp i databasen.
     * 'delete'-formulär: är den formulär som tart bort grupp/grupper ur databasen.
     * @param type <b>"add"</b>: En ny grupp ska läggas till i databasen.
     *             <br><b>"delete"</b>: En grupp ska tas bort från databasen.
     *             <br><b>"assign"</b>: En användare ska tilldelas en grupp.
     *             <br><b>"assignPl"</b>: En användare ska tilldelas rollen projektledare för en viss grupp.
     *             <br><b>"deletePl"</b>: En användare ska fråntas rollen som projektledare för en viss grupp.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Hämtar vilken form av formulär som skickats.
     * @return <b>"add"</b>: En ny grupp ska läggas till i databasen.
     *             <br><b>"delete"</b>: En grupp ska tas bort från databasen.
     *             <br><b>"assign"</b>: En användare ska tilldelas en grupp.
     *             <br><b>"assignPl"</b>: En användare ska tilldelas rollen projektledare för en viss grupp.
     *             <br><b>"deletePl"</b>: En användare ska fråntas rollen som projektledare för en viss grupp.
     */
    public String getType() {
        return type;
    }

    /**
     * Sätter in det projektgruppnamn som ska skapas i databasen.
     * @param name Gruppnamnet.
     */
    public void setGroupName(String name) {
        groupName = name;
    }

    /**
     * Hämtar gruppnamnet som ska skapas i databasen.
     * @return Ett gruppnamn.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sätter felkod, som används för att visa upp meddelanden i vy-nivån.
     * @param err_code <b>0:</b> Inget felmeddelande.
     *         <br><b>1:</b> Gruppnamnet finns redan i databasen.
     *         <br><b>2:</b> Inget gruppnamn har fyllts i.
     *         <br><b>3:</b> En användare har tilldelats en grupp.
     *         <br><b>4:</b> En användare tillhör redan den valda projektgruppen.
     *         <br><b>5:</b> En eller flera grupper har tagits bort.
     *         <br><b>6:</b> Fel när man skulle ta bort grupper. (Inga grupper markerade)
     *         <br><b>7:</b> En användare har tilldelats rollen projektledare.
     *         <br><b>8:</b> Användaren tilldelades inte rollen projektledare.
     *         <br><b>9:</b> En eller flera projektledare har tagits bort.
     *         <br><b>10:</b> Det gick inte att ta bort alla projektledare.
     *         <br><b>11:</b> En grupp har lagts till i databasen.
     */
    public void setErrorCode(int err_code) {
        this.err_code = err_code;
    }

    /**
     * Hämtar felkoden.
     * @return <b>0:</b> Inget felmeddelande.
     *         <br><b>1:</b> Gruppnamnet finns redan i databasen.
     *         <br><b>2:</b> Inget gruppnamn har fyllts i.
     *         <br><b>3:</b> En användare har tilldelats en grupp.
     *         <br><b>4:</b> En användare tillhör redan den valda projektgruppen.
     *         <br><b>5:</b> En eller flera grupper har tagits bort.
     *         <br><b>6:</b> Fel när man skulle ta bort grupper. (Inga grupper markerade)
     *         <br><b>7:</b> En användare har tilldelats rollen projektledare.
     *         <br><b>8:</b> Användaren tilldelades inte rollen projektledare.
     *         <br><b>9:</b> En eller flera projektledare har tagits bort.
     *         <br><b>10:</b> Det gick inte att ta bort alla projektledare.
     *         <br><b>11:</b> En grupp har lagts till i databasen.
     */
    public int getErrorCode() {
        return err_code;
    }

    /**
     * Sätter de grupper som ska tas bort.
     * @param groups Grupperna som ska tas bort.
     */
    public void setDeleteGroup(String[] groups) {
        deleteGroup = groups;
    }

    /**
     * Hämtar de grupper som ska tas bort.
     * @return Grupperna som ska tas bort.
     */
    public String[] getDeleteGroup() {
        return deleteGroup;
    }

    /**
     * Sätter in användare som finns i databasen.
     * @param users En lista med användarnamn
     */
    public void setAllUsers(List<String> users) {
        this.users = users;
    }

    /**
     * Hämtar en lista med användarnamn.
     * @return En lista med användarnamn.
     */
    public List<String> getAllUsers() {
        return users;
    }

    /**
     * Markerar vilken användare som ska kopplas till en viss projektgrupp
     * @param user Användaren som ska kopplas till en viss projektgrupp.
     */
    public void setAssignUser(String user) {
        assignUser = user;
    }

    /**
     * Hämtar vilken användare som ska kollas till en viss projektgrupp.
     * @return Ett användarnamn.
     */
    public String getAssignUser() {
        return assignUser;
    }

    /**
     * Markerar vilken projektgrupp en viss användare ska kopplas till.
     * @param group Gruppen användaren ska kopplas till.
     */
    public void setAssignGroup(String group) {
        assignGroup = group;
    }

    /**
     * Hämtar vilken grupp en viss användare ska kopplas till.
     * @return Gruppen en viss användare ska kopplas till.
     */
    public String getAssignGroup() {
        return assignGroup;
    }

    /**
     * Sätter vilken flik formuläret skickats från.
     * @param tab <b>"add"</b>: Fliken där man lägger till nya grupper.
     *            <br><b>"delete"</b>: Fliken där man tar bort projektgrupper.
     *            <br><b>"assign"</b>: Fliken där man tilldelar en grupp till en användare.
     *            <br><b>"assignPl"</b>: Fliken där man tilldelar/tar bort projektledare.
     */
    public void setTab(String tab) {
        this.tab = tab;
    }

    /**
     * Hämtar vilken flik formuläret skickats från.
     * @return Fliken formuläret skickats från.
     */
    public String getTab() {
        return tab;
    }

    /**
     * Hämtar en lista med alla projektledare.
     * @return En lista med alla projektledare.
     */
    public List<String[]> getAllPl() {
        return allPl;
    }

    /**
     * Sätter en lista med alla projektledare från databasen.
     * @param allPl En lista med alla projektledare från databasen.
     */
    public void setAllPl(List<String[]> allPl) {
        this.allPl = allPl;
    }

    /**
     * Hämtar en string-vektor med projektledare som ska tas bort.
     * @return En String-vektor med alla projektledare som ska tas bort.
     * <br>(Strängarna har formen "<b>användarnamn</b> <b>grupp</b>")
     */
    public String[] getRemovePl() {
        return removePl;
    }

    /**
     * Sätter en string-vektor med projektledare som ska tas bort.
     * @return En String-vektor med alla projektledare som ska tas bort.
     * <br>(Strängarna har formen "<b>användarnamn</b> <b>grupp</b>")
     */
    public void setRemovePl(String[] removePl) {
        this.removePl = removePl;
    }

    /**
     * Hämtar gruppnamnet på den grupp som en användare ska tilldelas rollen projektledare för.
     * @return Namnet på gruppen som användaren ska tilldelas rollen projektledare för.
     */
    public String getAssignGroupPl() {
        return assignGroupPl;
    }

    /**
     * Sätter namnet på den grupp en användare som ska tilldelas rollen projektledre för.
     * @param assignGroupPl Namnet på gruppen som användaren ska tilldelas rollen projektledare för.
     */
    public void setAssignGroupPl(String assignGroupPl) {
        this.assignGroupPl = assignGroupPl;
    }

    /**
     * Returnerar namnet på en användare som ska tilldelas rollen projekledare för en grupp.
     * @return Namnet på den användare som ska tilldelas rollen projektledare för en grupp.
     */
    public String getAssignUserPl() {
        return assignUserPl;
    }

    /**
     * Sätter namnet på en användare som ska tilldelas rollen projekledare för en grupp.
     * @param assignUserPl Namnet på den användare som ska tilldelas rollen projektledare för en grupp.
     */
    public void setAssignUserPl(String assignUserPl) {
        this.assignUserPl = assignUserPl;
    }
}
