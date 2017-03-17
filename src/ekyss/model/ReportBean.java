package ekyss.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportBean implements Serializable {
    private String user;
    private String group;
    private int week;
    private String type = null;
    private Map<String, Integer> reportValues = new HashMap<String, Integer>();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Map<String, Integer> getReportValues() {
        return reportValues;
    }

    public void setReportValues(Map<String, Integer> reportValues) {
        this.reportValues = reportValues;
    }

    public void setReportValues(String column, int value){
        reportValues.put(column, value);
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
