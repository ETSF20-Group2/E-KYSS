package ekyss.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DashboardBean implements Serializable {


    private Integer summary = null;
    private Map<String, Integer> reportValuesSum = new HashMap<>();


    public void setReportValuesSum(Map<String, Integer> reportValuesSum) {
        this.reportValuesSum = reportValuesSum;
    }

    public Map<String, Integer> getReportValuesSum() {
        return reportValuesSum;
    }

    /*
    private String user;
    private String group;
    private int week;
    private int documentSummary;
    private int activitySummary;
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
    public int getDocumentSummary() {
        return documentSummary;
    }
    public void setDocumentSummary(int documentSummary) {
        this.documentSummary = documentSummary;
    }
    public int getActivitySummary() {
        return activitySummary;
    }
    public void setActivitySummary(int activitySummary) {
        this.activitySummary = activitySummary;
    }
    public Map<String, Integer> getReportValues() {
        return reportValues;
    }
    public void setReportValues(Map<String, Integer> reportValues) {
        this.reportValues = reportValues;
    }
    public void setReportValues(String col, int value){
        reportValues.put(col, value);
    }
    public int getReportValues(String col){
        return reportValues.get(col);
    }
    */

}
