package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ReportManagementBean implements Serializable {
    private String group;
    private Map<String, List<Integer>> signMap = new HashMap<String, List<Integer>>();

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String, List<Integer>> getSignMap() {
        return signMap;
    }

    public void setSignMap(Map<String, List<Integer>> signMap) {
        this.signMap = signMap;
    }

    public void setSignMap(String userName, Integer week){
        if(!signMap.containsKey(userName))
            signMap.put(userName, new ArrayList<Integer>());
        signMap.get(userName).add(week);
    }
}
