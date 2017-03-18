package test.ekyss.model;

import ekyss.model.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;


public class UserBeanTest {
 private UserBean bean = new UserBean();
 
 @Test
	public final void testSetandGetUserName(){
	 bean.setUserName("Rich");
	 assertEquals("Inkorrekt returnering av getUserName()", "Rich", bean.getUserName());
	 bean.setUserName("Paul");
	 assertEquals("Inkorrekt returnering av getUserName()", "Paul", bean.getUserName());
	}
 
 @Test
 public final void testSetandGetEmail(){
	 bean.setEmail("bla.hotmail.com");
	 assertEquals("Inkorrekt returnering av getEmail()", "bla.hotmail.com", bean.getEmail());
	 bean.setEmail("blaa.hotmail.com");
	 assertEquals("Inkorrekt returnering av getEmail()", "blaa.hotmail.com", bean.getEmail());
	}
 
 @Test
 public final void testSetandGetGroup(){
	 bean.setGroup("Grupp1");
	 assertEquals("Inkorrekt returnering av getGroup()", "Grupp1", bean.getGroup());
	 bean.setGroup("Grupp2");
	 assertEquals("Inkorrekt returnering av getGroup()", "Grupp2", bean.getGroup());
	}
 
 @Test
 public final void testSetandGetRole(){
	 bean.setRole("Utvecklare");
	 assertEquals("Inkorrekt returnering av getRole()", "Utvecklare", bean.getRole());
	 bean.setRole("Projektledare");
	 assertEquals("Inkorrekt returnering av getRole()", "Projektledare", bean.getRole());
	}
 
 @Test
 public final void testSetandGetNewPassword(){
	 bean.setNewPassword1("1234");
	 assertEquals("Inkorrekt returnering av getNewPassword()", "1234", bean.getNewPassword1());
	}
 
 /*@Test
 public final void testSetandGetGroupList(){
	 ArrayList<String> groups = new ArrayList<String>();
	 groups.add("Grupp1");
	 groups.add("Grupp2");
	 bean.setGroupList(groups);
	 assertEquals("Inkorrekt returnering av getGroupList()", groups, bean.getGroupList());
	}
 
*/
}