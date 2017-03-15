package ekyss.model;

import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.lang.jstl.test.Bean1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.MethodSorter;

@FixMethodOrder(MethodSorters.JVM)
public class DatabaseHandlerTest {
    DatabaseHandler db = new DatabaseHandler();

    @Test
    public void testUserLoginSuccess() {
        start("testUserLoginSuccess()");
        LoginBean b = new LoginBean();
        b.setUsername("Elihn");
        b.setPassword("1234");
        b.setGroup("2");
        assertTrue(db.loginUser(b));
        end();
    }

    @Test
    public void testUserLoginFailure() {
        start("testUserLoginFailure()");
        LoginBean b = new LoginBean();
        b.setUsername("Elihn");
        b.setPassword("123");
        b.setGroup("2");
        assertFalse(db.loginUser(b));
        b.setPassword("1234");
        b.setUsername("E");
        assertFalse(db.loginUser(b));
        b.setUsername("Elihn");
        b.setGroup("1");
        assertFalse(db.loginUser(b));
        end();
    }

    @Test
    public void testAddGroup(){
        start("testAddGroup()");
        GroupManagementBean b = new GroupManagementBean();
        b.setGroup("hej");
        assertTrue(db.addGroup(b));
        assertFalse(db.addGroup(b));
        ArrayList<String> gl = new ArrayList<String>();
        gl.add("hej");
        b.setGroups(gl);
        db.deleteGroups(b);
        end();
    }

    @Test
    public void testDeleteGroups(){
        start("testDeleteGroups()");
        GroupManagementBean b = new GroupManagementBean();
        ArrayList<String> gl = new ArrayList<String>();
        for(int i = 10; i<20; i++){
            gl.add(""+i);
            b.setGroup(""+i);
            db.addGroup(b);
        }
        b.setGroups(gl);
        assertTrue(db.deleteGroups(b));
        assertFalse(db.deleteGroups(b));
        end();
    }

    @Test
    public void testAssignLeader(){
        start("testAssignLeader()");
        GroupManagementBean b = new GroupManagementBean();
        b.setGroup("1");
        b.setLeader("Jonas");
        assertTrue(db.assignLeader(b));
        end();
    }

    @Test
    public void testAddAndDeleteUser(){
        start("testAddAndDeleteUser()");
        UserManagementBean b = new UserManagementBean();
        b.setUserName("Ivar");
        b.setEmail("ivar@gmail.com");
        b.setPassword("ivarp");
        ArrayList<String> al = new ArrayList<String>();
        al.add("Ivar");
        b.setUserList(al);
        assertTrue(db.addUser(b));
        assertFalse(db.addUser(b));
        db.deleteUsers(b);
        end();
    }

    @Test
    public void testAddUserTooLongPassword(){
        start("testAddUserTooLongPassword()");
        UserManagementBean b = new UserManagementBean();
        b.setUserName("Ivar");
        b.setEmail("ivar@gmail.com");
        b.setPassword("ivarpass");
        assertFalse(db.addUser(b));
        end();
    }

    @Test
    public void testAddAndDeleteUsers(){
        start("testAddAndDeleteUsers()");
        UserManagementBean b = new UserManagementBean();
        ArrayList<String> al = new ArrayList<String>();
        for(int i = 0; i<50; i++){
            al.add(""+i);
            b.setUserName(""+i);
            b.setEmail(""+i);
            b.setPassword(""+i);
            assertTrue(db.addUser(b));
        }
        b.setUserList(al);
        assertTrue(db.deleteUsers(b));
        assertFalse(db.deleteUsers(b));
        end();
    }

    @Test
    public void testAssignGroupAndRole(){
        start("testAssignGroupAndRole()");
        UserManagementBean b = new UserManagementBean();
        b.setUserName("test");
        b.setEmail("...");
        b.setPassword("...");
        b.setGroup("2");
        b.setRole("IG");
        db.addUser(b);
        assertTrue("Error in assignGroup1", db.assignGroup(b));
        assertTrue("Error in assignRole1", db.assignRole(b));
        assertFalse("Error in assignGroup2", db.assignGroup(b));
        b.setGroup("1");
        assertTrue("Error in assignGroup3", db.assignGroup(b));
        assertTrue("Error in assignRole2", db.assignRole(b));
        assertFalse("Error in assignGroup4", db.assignGroup(b));
        ArrayList<String> al = new ArrayList<String>();
        al.add("test");
        b.setUserList(al);
        db.deleteUsers(b);
        end();
    }

    @Test
    public void testAssignGroupAndDelete(){
        start("testAssignGroupAndDelete()");
        UserManagementBean b = new UserManagementBean();
        b.setUserName("test");
        b.setEmail("...");
        b.setPassword("...");
        b.setGroup("1");
        db.addUser(b);
        assertTrue(db.assignGroup(b));
        assertTrue(db.deleteFromGroup(b));
        assertFalse(db.deleteFromGroup(b));
        ArrayList<String> a = new ArrayList<String>();
        a.add("Test");
        b.setUserList(a);
        db.deleteUsers(b);
        end();
    }

    @Test
    public void testSignReports(){
        ReportManagementBean b = new ReportManagementBean();
        b.setGroup("2");
        Map<String, List<Integer>> hm = new HashMap<String, List<Integer>>();
        hm.put("Elihn", new ArrayList<Integer>());
        hm.put("Pelle", new ArrayList<Integer>());
        hm.get("Elihn").add(1);
        hm.get("Elihn").add(2);
        hm.get("Pelle").add(2);
        b.setSignMap(hm);
        assertTrue(db.signReports(b));
    }


    @Test
    public void testInsertAndRemoveTimeReport(){
        start("testInsertTimeReport()");
        ReportBean b = new ReportBean();
        String[] TIMEREPORTCOLUMNS = {"11_d", "11_i", "11_f", "11_r",
                "12_d", "12_i", "12_f", "12_r",
                "13_d", "13_i", "13_f", "13_r",
                "14_d", "14_i", "14_f", "14_r",
                "15_d", "15_i", "15_f", "15_r",
                "16_d", "16_i", "16_f", "16_r",
                "17_d", "17_i", "17_f", "17_r",
                "18_d", "18_i", "18_f", "18_r",
                "19_d", "19_i", "19_f", "19_r",
                "21_t", "22_t", "23_t", "30_t", "41_t",
                "42_t", "43_t", "44_t", "100_t"};
        Map<String, Integer> tr = new HashMap<String, Integer>();
        for(String s:TIMEREPORTCOLUMNS){
            tr.put(s, 1);
        }
        b.setGroup("1");
        b.setWeek(10);
        b.setUser("Johannes");
        b.setReportValues(tr);
        assertTrue(db.createTimeReport(b));
        assertFalse(db.createTimeReport(b));
        assertTrue(db.removeTimeReport(b));
        end();
    }

    @Test
    public void testUpdateTimeReport(){
        start("testInsertTimeReport()");
        ReportBean b = new ReportBean();
        String[] TIMEREPORTCOLUMNS = {"11_d", "11_i", "11_f", "11_r",
                "12_d", "12_i", "12_f", "12_r",
                "13_d", "13_i", "13_f", "13_r",
                "14_d", "14_i", "14_f", "14_r",
                "15_d", "15_i", "15_f", "15_r",
                "16_d", "16_i", "16_f", "16_r",
                "17_d", "17_i", "17_f", "17_r",
                "18_d", "18_i", "18_f", "18_r",
                "19_d", "19_i", "19_f", "19_r",
                "21_t", "22_t", "23_t", "30_t", "41_t",
                "42_t", "43_t", "44_t", "100_t"};
        Map<String, Integer> tr = new HashMap<String, Integer>();
        for(String s:TIMEREPORTCOLUMNS){
            tr.put(s, 1);
        }
        b.setGroup("1");
        b.setWeek(11);
        b.setUser("Johannes");
        b.setReportValues(tr);
        db.createTimeReport(b);
        for(String s: tr.keySet()){
            tr.put(s, 2);
        }
        assertTrue(db.updateTimeReports(b));
        db.removeTimeReport(b);
    }

    @Test
    public void testChangePasswordValid(){
        start("testChangePasswordValid()");
        LoginBean login = new LoginBean();
        login.setUsername("test");
        login.setPassword("test");
        login.setGroup("1");
        UserManagementBean create = new UserManagementBean();
        create.setUserName("test");
        create.setPassword("test");
        create.setEmail("");
        create.setGroup("1");
        ArrayList<String> al = new ArrayList<String>();
        al.add("test");
        create.setUserList(al);
        db.addUser(create);
        db.assignGroup(create);
        assertTrue(db.loginUser(login));
        UserBean b = new UserBean();
        b.setUserName("test");
        b.setNewPassword("tset");
        assertTrue(db.changePassword(b));
        assertFalse(db.loginUser(login));
        db.deleteUsers(create);
        end();
    }

    @Test
    public void testGetUserListU(){
        start("testGetUserListU()");
        UserManagementBean b = db.getUserListU();
        List<String> l = b.getUserList();
        String[] users = {"Elihn", "Jesper", "Joe", "Johannes", "Jonas", "Jonathan", "Kalle", "Kasper", "Martin", "Pelle"};
        for(String s : users){
            assertTrue("User " + s + " not in list", l.contains(s));
        }
        end();
    }

    @Test
    public void testGetMemberOf(){
        start("testGetMemberOf()");
        UserBean b = db.getMemberOf("Jesper");
        String[] groupNames = {"3", "2"};
        for(String s : groupNames){
            assertTrue("Group " + s + " not in list.", b.getGroupList().contains(s));
        }
        b = db.getMemberOf("Joe");
        assertTrue(b.getGroupList().isEmpty());

        end();
    }

    @Test
    public void testGetTimeReport(){
        start("testGetTimeReport()");
        // All tid rapporterad till projektgrupp 1.
        Map<String, Integer> l1 = db.getTimeReport("1", "", "", 0).getReportValues();
        // All tid rapportad för Johannes till projektgrupp 1
        Map<String, Integer> l2 = db.getTimeReport("1", "Johannes", "", 0).getReportValues();
        // All tid rapporterad för personer med rollen PG till projektgrupp 1
        Map<String, Integer> l3 = db.getTimeReport("1", "", "PG", 0).getReportValues();
        // All tid rapporterad för projektgrupp 1 under vecka 1
        Map<String, Integer> l4 = db.getTimeReport("1", "", "", 1).getReportValues();

        int sum1 = 0;//180;
        int sum2 = 0;//90;
        int sum3 = 0;//135;
        int sum4 = 0;//90;
        for(String s : l1.keySet()){
            if(s.endsWith("_t") &&  !s.startsWith("100") &&s.startsWith("1"))
                continue;
            sum1 += l1.get(s);
            sum2 += l2.get(s);
            sum3 += l3.get(s);
            sum4 += l4.get(s);
        }
        assertEquals("Wrong values in l1", 180, sum1);
        assertEquals("Wrong values in l2", 90, sum2);
        assertEquals("Wrong values in l3", 135, sum3);
        assertEquals("Wrong values in l4", 90, sum4);
        end();
    }

    @Test
    public void testGetDocumentSummary(){
        start("testGetDocumentSummary()");
        DashboardBean b = db.getDocumentSummary("1", 11);
        assertEquals(16, b.getDocumentSummary());
        end();
    }

    @Test
    public void testGetActivitySummary(){
        start("testGetActivitySummary()");
        DashboardBean b = db.getActivitySummary("1", "d");
        DashboardBean b2 = db.getActivitySummary("1", "f");
        DashboardBean b3 = db.getActivitySummary("2", "i");

        assertEquals(36, b.getActivitySummary());
        assertEquals(36, b2.getActivitySummary());
        assertEquals(47, b3.getActivitySummary());
        end();
    }


    private void start(String s){
        System.out.println("--------------- " + s + " ---------------");
    }

    private void end(){
        System.out.println("---------------------------------------------\n");
    }

}
