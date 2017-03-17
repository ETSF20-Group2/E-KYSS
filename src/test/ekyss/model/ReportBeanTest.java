package ekyss.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ReportBeanTest {
	
	private ReportBean bean = new ReportBean();
	
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
	public void setReportValues() throws Exception {
		Map<String, Integer> testMap = new HashMap<String, Integer>();
		testMap.put("a", 10);
		testMap.put("b", 11);
		bean.setReportValues(testMap);
		assertEquals("Map har inte satts till rätt map/värden", bean.getReportValues(), testMap);
		assertFalse("Map är null", bean.getReportValues() == null);
	}
}
