package test.ekyss.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ekyss.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoginBeanTest {

    private LoginBean bean = new LoginBean();

    @Test
    public void setLogin() throws Exception {
        assertFalse("Inkorrekt standardvärde av isLogin()", bean.isLogin());
        bean.setLogin(true);
        assertTrue("Inkorrekt respons av isLogin()", bean.isLogin());
        bean.setLogin(false);
        assertFalse("Inkorrekt respons av isLogin()", bean.isLogin());
    }

    @Test
    public void setErrorCode() throws Exception {
        assertEquals("Inkorrekt standardvärde av getErrorCode()", 0, bean.getErrorCode());
        bean.setErrorCode(4);
        assertEquals("Inkorrekt returnering av getErrorCode()", 0, bean.getErrorCode());
        bean.setErrorCode(-4);
        assertEquals("Inkorrekt returnering av getErrorCode()", 0, bean.getErrorCode());
        for (int i=0; i<=3; i++) {
            bean.setErrorCode(i);
            assertEquals("Inkorrekt returnering av getErrorCode()", i, bean.getErrorCode());
        }
        bean.setErrorCode(4);
        assertEquals("Inkorrekt returnering av getErrorCode()", 3, bean.getErrorCode());
    }

    @Test
    public void setAllGroups_String() throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("qwertasdf");
        arrayList.add("trdsqhjyt");
        arrayList.add("einflsiw3");

        bean.setAllGroups("qwertasdf");
        bean.setAllGroups("trdsqhjyt");
        bean.setAllGroups("einflsiw3");
        assertEquals("Inkorrekt returnering av getAllGroup()", arrayList, bean.getAllGroups());
    }

    @Test
    public void setAllGroups_ArrayList() throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("qwertasdf");
        arrayList.add("trdsqhjyt");
        arrayList.add("einflsiw3");

        bean.setAllGroups(arrayList);
        assertEquals("Inkorrekt returnering av getAllGroups()", arrayList, bean.getAllGroups());
    }

    @Test
    public void setUsername() throws Exception {
        bean.setUsername("kalle");
        assertEquals("Inkorrekt returnering av getUsername()", "kalle", bean.getUsername());
        bean.setUsername("pelle");
        assertEquals("Inkorrekt returnering av getUsername()", "pelle", bean.getUsername());
    }

    @Test
    public void setPassword() throws Exception {
        bean.setPassword("qweerty");
        assertEquals("Inkorrekt returnering av getPassword()", "qweerty", bean.getPassword());
        bean.setPassword("gjsoq91");
        assertEquals("Inkorrekt returnering av getPassword()", "gjsoq91", bean.getPassword());
    }

    @Test
    public void getSelectedGroup() throws Exception {
        bean.setSelectedGroup("abc1");
        assertEquals("Inkorrekt returnering av getSelectedGroup()", "abc1", bean.getSelectedGroup());
        bean.setSelectedGroup("1cba");
        assertEquals("Inkorrekt returnering av getSelectedGroup()", "1cba", bean.getSelectedGroup());
    }

}