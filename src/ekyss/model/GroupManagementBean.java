package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupManagementBean implements Serializable {

    private List<String> groups = new ArrayList<>();
    private String type = null;
    private String groupName = null;
    private int err_code = 0;

    /**
     * Sätter en lista innehållandes samtliga existerande grupper i databasen.
     * @param groups
     */
    public void setAllGroups(List<String> groups) {
        this.groups.addAll(groups);
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
     */
    public void setErrorCode(int err_code) {
        if ( (err_code-0)*(err_code-2) <= 0 ) {
            this.err_code = err_code;
        }
    }

    public int getErrorCode() {
        return err_code;
    }


    /*
    private List<String> groups = new ArrayList<>();
    private String group = null;
    private String leader = null;
    private List<String> users = new ArrayList<>();

    public void setUsers(List<String> users){
        this.users = users;
    }

    public void setUsers(String user){
        users.add(user);
    }

    public List<String> getUsers(){
        return users;
    }

    public void setGroups(List<String> groups){
        this.groups = groups;
    }

    public void setGroups(String group){
        groups.add(group);
    }

    public List<String> getGroups(){
        return groups;
    }

    public void setGroup(String group){
        this.group = group;
    }

    public String getGroup(){
        return group;
    }

    public void setLeader(String leader){
        this.leader = leader;
    }

    public String getLeader(){
        return leader;
    }
    */
}
