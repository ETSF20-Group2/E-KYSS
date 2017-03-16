package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupManagementBean implements Serializable {

    private List<String> groups = new ArrayList<>();

    public void setAllGroups(List<String> groups) {
        this.groups.addAll(groups);
    }

    public void setAllGroups(String group) {
        groups.add(group);
    }

    public List<String> getAllGroups() {
        return groups;
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
