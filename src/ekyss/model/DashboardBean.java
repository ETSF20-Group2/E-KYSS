package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<String[]> userList = new ArrayList<>();
    private String role = "PL";
    private List<Integer> weeks;
    private Integer week;


    /**
     * Sätter attributet group med namnet på gruppen.
     * @param group Namnet på gruppen.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Returnerar gruppens namn
     * @return Gruppens namn.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sätter attributet user med namnet på användaren.
     * @param user Användarnamnet.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returnerar användarens namn.
     * @return Användarnamnet.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sätter attributen reportValuesSum samt sumA, sum_d, sum_i, sum_f och sum_r
     * @param reportValuesSum En map med alla värden i en tidrapport. Nyckeln ska vara namnet på kolumnen, värdet ska vara
     *                        antal minuter.
     */
    public void setReportValuesSum(Map<String, Integer> reportValuesSum) {
        this.reportValuesSum = reportValuesSum;
        setSum();
    }

    /**
     * Hämtar reportValuesSum
     * @return En Map med alla värden i en tidrapport. I mappen är nyckeln namnet på kolumnen och värdet är antal minuter.
     */
    public Map<String, Integer> getReportValuesSum() {
        return reportValuesSum;
    }

    /**
     * Sätter vilken flik användaren är inne på.
     * @param tab Fliken.
     */
    public void setTab(String tab) {
        this.tab = tab;
    }

    /**
     * Hämtar vilken flik användaren är inne på.
     * @return Fliken.
     */
    public String getTab() {
        return tab;
    }

    /**
     * Hämtar summan för en viss aktivitet eller totalsumman för alla aktiviteter.
     * @param type Vilken aktivitet man vill hämta ut. Man kan välja mellan:
     *             <br><b><i>sumA</i></b>: Summan av alla aktiviteter.
     *             <br><b><i>sum_d</i></b>: Summan av aktiviteten D.
     *             <br><b><i>sum_i</i></b>: Summan av aktiviteten I.
     *             <br><b><i>sum_f</i></b>: Summan av aktiviteten F.
     *             <br><b><i>sum_r</i></b>: Summan av aktiviteten R.
     * @return Summan av vald aktivitet/ den totala summan.
     */
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

    /**
     * Returnerar en lista på användare (userList).
     * @return En lista på användare.
     */
    public List<String[]> getUserList() {
        return userList;
    }

    /**
     * Sätter värde på userList (lista med användare).
     * @param userList en lista med användarnamn.
     */
    public void setUserList(List<String[]> userList) {
        this.userList = userList;
    }

    /**
     * Sätter attributet role (en användares roll).
     * @param role Rollen man vill sätta.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returnerar role.
     * @return En roll.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sätter attributet weeks (en lista med veckor).
     * @param weeks En lista med veckor.
     */
    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    /**
     * Returnerar weeks (en lista med veckor).
     * @return En lista med veckor.
     */
    public List<Integer> getWeeks() {
        return weeks;
    }

    /**
     * Sätten attributet week (en vecka).
     * @param week En vecka.
     */
    public void setWeek(Integer week) {
        this.week = week;
    }

    /**
     * Hämtar attributet week (en vecka).
     * @return En vecka.
     */
    public Integer getWeek() {
        return week;
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

}
