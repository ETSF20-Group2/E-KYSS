package test.ekyss.model;

import ekyss.model.*;
import static org.junit.Assert.fail;

import static org.junit.Assert.*;

import org.junit.Test;

public class DashBoardBeanTest {
	
	private DashboardBean bean = new DashboardBean();
	
	@Test
	public void setUser() throws Exception {
		 bean.setUser("a");
		 assertEquals("Inkorrekt värde av user", "a", bean.getUser()); 
	}
	
	@Test
	public void setGroup() throws Exception {
		bean.setGroup("a");
		assertEquals("Inkorrekt värde av group", "a", bean.getGroup());
	}
	
	@Test
	public void setWeek() throws Exception {
		bean.setWeek(1);
		assertEquals("Inkorrekt värde av week", 1, bean.getWeek());
		bean.setWeek(53);
		assertFalse("Värdet är större än 52 eller mindre än 1", bean.getWeek() > 0 && bean.getWeek() < 53);	
	}
	
	@Test
	public void setDocumentSummary() throws Exception {
		bean.setDocumentSummary(1);
		assertEquals("Inkorrekt värde av documentSummary", 1, bean.getDocumentSummary());
	}
	
	@Test
	public void setActivitySummary() throws Exception {
		bean.setActivitySummary(1);
		assertEquals("Inkorrekt värde av activitySummary", 1, bean.getActivitySummary());
	}
	
	public void setReportValues() throws Exception {
		bean.setReportValues("a", 10);
		bean.setReportValues("b", 11);
		assertFalse("Map är null", bean.getReportValues() != null);
		assertEquals("Fel värden hämtas", 10, bean.getReportValues("a"));
		assertEquals("Fel värden hämtas", 11, bean.getReportValues("b"));
	}
}
