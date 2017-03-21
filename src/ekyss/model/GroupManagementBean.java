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
     * @param groups
     */
    public void setAllGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getAllGroups() {
        return groups;
    }

    /**
     * Sätt vilken form av formulär som skickas: 'add' eller 'delete'.
     * 'add'-formulär: är den formulär som användas för att skapa en grupp i databasen.
     * 'delete'-formulär: är den formulär som tart bort grupp/grupper ur databasen.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * Sätter in det projektgruppnamn som ska skapas i databasen.
     * @param name
     */
    public void setGroupName(String name) {
        groupName = name;
    }

    public String getGroupName() {
        return groupName;
    }

    /**
     * Sätter felkod, som används för att visa upp meddelanden i vy-nivån.
     * @param err_code 0 = inget felmeddelande,
     *                 1 = gruppnamn existerar redan
     *                 2 = gruppnamn är tomt
     *                 3 = lyckad tilldelning av använder till grupp
     *                 4 = ej lyckad tilldelning av användare till grupp
     */
    public void setErrorCode(int err_code) {
        this.err_code = err_code;
    }

    public int getErrorCode() {
        return err_code;
    }

    /**
     * Sätter de grupper som ska tas bort.
     * @param groups
     */
    public void setDeleteGroup(String[] groups) {
        deleteGroup = groups;
    }

    public String[] getDeleteGroup() {
        return deleteGroup;
    }

    /**
     * Sätter in användare som finns i databasen.
     * @param users
     */
    public void setAllUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getAllUsers() {
        return users;
    }

    /**
     * Markerar vilken användare som ska kopplas till en viss projektgrupp
     * @param user
     */
    public void setAssignUser(String user) {
        assignUser = user;
    }

    public String getAssignUser() {
        return assignUser;
    }

    /**
     * Markerar vilken projektgrupp en viss användare ska kopplas till.
     * @param group
     */
    public void setAssignGroup(String group) {
        assignGroup = group;
    }

    public String getAssignGroup() {
        return assignGroup;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTab() {
        return tab;
    }

    public List<String[]> getAllPl() {
        return allPl;
    }

    public void setAllPl(List<String[]> allPl) {
        this.allPl = allPl;
    }

    public String[] getRemovePl() {
        return removePl;
    }

    public void setRemovePl(String[] removePl) {
        this.removePl = removePl;
    }

    public String getAssignGroupPl() {
        return assignGroupPl;
    }

    public void setAssignGroupPl(String assignGroupPl) {
        this.assignGroupPl = assignGroupPl;
    }

    public String getAssignUserPl() {
        return assignUserPl;
    }

    public void setAssignUserPl(String assignUserPl) {
        this.assignUserPl = assignUserPl;
    }
}
