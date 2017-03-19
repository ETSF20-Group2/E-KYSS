package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ReportManagementBean implements Serializable {
    private String group;
    private List<String[]> allUnsignedReports = new ArrayList<String[]>();
    private List<String[]> allSignedReports = new ArrayList<String[]>();
    private Map<String, List<Integer>> signMap = new HashMap<String, List<Integer>>();
    private String type = null;
    private String tab = "";
    private String[] signReport = null;
    private String[] unsignReport = null;
    private int err_code = 0;

    public String getType() { return type; }

    public void setType(String type){ this.type = type; }

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

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public List<String[]> getAllUnsignedReports() {
        return allUnsignedReports;
    }

    public void setAllUnsignedReports(List<String[]> allUnsignedReports) {
        this.allUnsignedReports = allUnsignedReports;
    }

    public List<String[]> getAllSignedReports() {
        return allSignedReports;
    }

    public void setAllSignedReports(List<String[]> allSignedReports) {
        this.allSignedReports = allSignedReports;
    }

    public String[] getSignReport() {
        return signReport;
    }

    public void setSignReport(String[] signReport) {
        this.signReport = signReport;
    }

    public String[] getUnsignReport() {
        return unsignReport;
    }

    public void setUnsignReport(String[] unsignReport) {
        this.unsignReport = unsignReport;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }
}
