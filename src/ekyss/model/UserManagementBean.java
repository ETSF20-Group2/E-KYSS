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


    /*
    private String userName;
    private String password;
    private String group;
    private String role;
    private List<String> userList = new ArrayList<String>();

    private String email; //Måste läggas till då man använder det i addUser() metoden.

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public void setUserList(String user){
        userList.add(user);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    */
}
