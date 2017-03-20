package ekyss.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DashboardBean implements Serializable {

    private String user = null;
    private String group = null;
    private Map<String, Integer> reportValuesSum = new HashMap<>();
    private String tab = null;
    private Integer sumA = 0;
    private Integer sum_d = 0;
    private Integer sum_i = 0;
    private Integer sum_f = 0;
    private Integer sum_r = 0;

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setReportValuesSum(Map<String, Integer> reportValuesSum) {
        this.reportValuesSum = reportValuesSum;
        setSum();
    }

    public Map<String, Integer> getReportValuesSum() {
        return reportValuesSum;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTab() {
        return tab;
    }

    public Integer getSum(String type) {
        switch (type) {
            case "sumA": return sumA;
            case "sum_d": return sum_d;
            case "sum_i": return sum_i;
            case "sum_f": return sum_f;
            case "sum_r": return sum_r;
            default: return null;
        }
    }

    private void setSum() {
        for (int i = 11; i<=19; i++) {
            sum_d += reportValuesSum.get("d_" + i);
            sum_i += reportValuesSum.get("i_" + i);
            sum_f += reportValuesSum.get("f_" + i);
            sum_r += reportValuesSum.get("r_" + i);
            sumA += reportValuesSum.get("t_" + i);
        }
        sumA += reportValuesSum.get("t_21");
        sumA += reportValuesSum.get("t_22");
        sumA += reportValuesSum.get("t_23");
        sumA += reportValuesSum.get("t_30");
        sumA += reportValuesSum.get("t_41");
        sumA += reportValuesSum.get("t_42");
        sumA += reportValuesSum.get("t_43");
        sumA += reportValuesSum.get("t_44");
        sumA += reportValuesSum.get("t_100");
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
