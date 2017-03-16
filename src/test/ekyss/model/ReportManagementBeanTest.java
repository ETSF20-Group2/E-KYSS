package ekyss.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import beans.ReportManagementBean;

public class ReportManagementBeanTest {
	
	private ReportManagementBean bean = new ReportManagementBean();
	
	@Test
	public void setGroup() throws Exception {
		bean.setGroup("a");
		assertEquals("Inkorrekt värde av user", "a", bean.getGroup());
	}
	
	@Test
	public void setSignMap() throws Exception {
		Map<String, List<Integer>> testMap = new HashMap<String, List<Integer>>();
		List<Integer> testList = new ArrayList<Integer>();
		for(int n = 0; n < 5; n++){
			testList.add(n);
		}
		testMap.put("a", testList);
		bean.setSignMap(testMap);
		assertEquals("Fel värde av signMap", testMap, bean.getSignMap());
	}
	
	//Behövs fler tester, men fann det svårt att göra rätt test här. Tyckte att koden var lite fel, men nog bara jag som är förvirrad /Calle

}
