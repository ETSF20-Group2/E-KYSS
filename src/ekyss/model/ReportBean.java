package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Denna klass är bönan för för webbsidan "Veckorapportering".
 */
public class ReportBean implements Serializable {
    private String user;
    private String group;
    private int week = 0;
    private String type = "";
    private Map<String, Integer> reportValues = new HashMap<String, Integer>();
    private List<String> allWeeks = new ArrayList<String>();
    private int err_code = 0;
    private String tab = null;

    private int d_11 = 0, i_11 = 0, f_11 = 0, r_11 = 0;
    private int d_12 = 0, i_12 = 0, f_12 = 0, r_12 = 0;
    private int d_13 = 0, i_13 = 0, f_13 = 0, r_13 = 0;
    private int d_14 = 0, i_14 = 0, f_14 = 0, r_14 = 0;
    private int d_15 = 0, i_15 = 0, f_15 = 0, r_15 = 0;
    private int d_16 = 0, i_16 = 0, f_16 = 0, r_16 = 0;
    private int d_17 = 0, i_17 = 0, f_17 = 0, r_17 = 0;
    private int d_18 = 0, i_18 = 0, f_18 = 0, r_18 = 0;
    private int d_19 = 0, i_19 = 0, f_19 = 0, r_19 = 0;
    private int t_21 = 0, t_22 = 0, t_23 = 0;
    private int t_30 = 0;
    private int t_41 = 0, t_42 = 0, t_43 = 0, t_44 = 0;
    private int t_100 = 0;

    
    /**
     * Sätter en felkod som används i report.jps
     * @param err_code <b>1</b>: Rapporten har skapats.
     *             <br><b>2</b>: Rapporten har uppdaterats.
     *             <br><b>3</b>: Rapporten har tagits bort.
     *             <br><b>4</b>: Det fanns redan en rapport från den användaren och gruppen för den veckan.
     *             <br><b>5</b>: Användaren har ingen roll.
     *             <br><b>6</b>: Okänt fel.
     */
    public void setErr_code(int err_code){
        this.err_code=err_code;
    }

    /**
     * Returnerar felkoden.
     * @return <b>1</b>: Rapporten har skapats.
     *         <br><b>2</b>: Rapporten har uppdaterats.
     *         <br><b>3</b>: Rapporten har tagits bort.
     *         <br><b>4</b>: Det fanns redan en rapport från den användaren och gruppen för den veckan.
     *         <br><b>5</b>: Användaren har ingen roll.
     *         <br><b>6</b>: Okänt fel.
     */
    public int getErr_code(){
        return err_code;
    }

    /**
     * Sätter en lista med alla veckor en användare har en osignerad rapport för (inom en specifik grupp).
     * @param allWeeks En lista med alla veckor en användare har en osignerad rapport för (inom en specifik grupp).
     */
    public void setAllWeeks(List<String> allWeeks){
        this.allWeeks = allWeeks;
    }

    /**
     * Returnerar en lista med alla veckor en användare har en osignerad rapport för (inom en specifik grupp).
     * @return En lista med alla veckor en användare har en osignerad rapport för (inom en specifik grupp).
     */
    public List<String> getAllWeeks(){
        return allWeeks;
    }

    /** 
     * Hämtar kolumn d_11 i tidrapporten.
     * @return kolumn d_11
     */
    public int getD_11() {
        return d_11;
    }


    /**
     * Sätter kolumn d_11 i tidrapporten (lägger även till i reportValues).
     * @param d_11 kolumn d_11
     */
    public void setD_11(int d_11) {
        this.d_11 = d_11;
        reportValues.put("d_11", d_11);
    }


    /**
     * Hämtar kolumn i_11 i tidrapporten.
     * @return kolumn i_11
     */
    public int getI_11() {
        return i_11;
    }
    
    /**
     * Sätter kolumn i_11 i tidrapporten (lägger även till i reportValues).
     * @param i_11 kolumn i_11
     */
    public void setI_11(int i_11) {
        this.i_11 = i_11;
        reportValues.put("i_11", i_11);
    }
    
    /**
     * Hämtar kolumn f_11 i tidrapporten.
     * @return kolumn f_11
     */
    public int getF_11() {
        return f_11;
    }

    /**
     * Sätter kolumn f_11 i tidrapporten (lägger även till i reportValues).
     * @param f_11 kolumn f_11
     */
    public void setF_11(int f_11) {
        this.f_11 = f_11;
        reportValues.put("f_11", f_11);
    }
    
    /**
     * Hämtar kolumn r_11 i tidrapporten.
     * @return kolumn r_11
     */
    public int getR_11() {
        return r_11;
    }

    /**
     * Sätter kolumn r_11 i tidrapporten (lägger även till i reportValues).
     * @param r_11 kolumn r_11
     */
    public void setR_11(int r_11) {
        this.r_11 = r_11;
        reportValues.put("r_11", r_11);
    }
    
    /**
     * Hämtar kolumn d_12 i tidrapporten.
     * @return kolumn d_12
     */
    public int getD_12() {
        return d_12;
    }

    /**
     * Sätter kolumn d_12 i tidrapporten (lägger även till i reportValues).
     * @param d_12 kolumn d_12
     */
    public void setD_12(int d_12) {
        this.d_12 = d_12;
        reportValues.put("d_12", d_12);
    }

    /**
     * Hämtar kolumn i_12 i tidrapporten.
     * @return kolumn i_12
     */
    public int getI_12() {
        return i_12;
    }

    /**
     * Sätter kolumn i_12 i tidrapporten (lägger även till i reportValues).
     * @param i_12 kolumn i_12
     */
    public void setI_12(int i_12) {
        this.i_12 = i_12;
        reportValues.put("i_12", i_12);
    }

    /**
     * Hämtar kolumn f_12 i tidrapporten.
     * @return kolumn f_12
     */
    public int getF_12() {
        return f_12;
    }

    /**
     * Sätter kolumn f_12 i tidrapporten (lägger även till i reportValues).
     * @param f_12 kolumn f_12
     */
    public void setF_12(int f_12) {
        this.f_12 = f_12;
        reportValues.put("f_12", f_12);
    }

    /**
     * Hämtar kolumn r_12 i tidrapporten.
     * @return kolumn r_12
     */
    public int getR_12() {
        return r_12;
    }

    /**
     * Sätter kolumn r_12 i tidrapporten (lägger även till i reportValues).
     * @param r_12 kolumn r_12
     */
    public void setR_12(int r_12) {
        this.r_12 = r_12;
        reportValues.put("r_12", r_12);
    }

    /**
     * Hämtar kolumn d_13 i tidrapporten.
     * @return kolumn d_13
     */
    public int getD_13() {
        return d_13;
    }

    /**
     * Sätter kolumn d_13 i tidrapporten (lägger även till i reportValues).
     * @param d_13 kolumn d_13
     */
    public void setD_13(int d_13) {
        this.d_13 = d_13;
        reportValues.put("d_13", d_13);
    }

    /**
     * Hämtar kolumn i_13 i tidrapporten.
     * @return kolumn i_13
     */
    public int getI_13() {
        return i_13;
    }

    /**
     * Sätter kolumn i_13 i tidrapporten (lägger även till i reportValues).
     * @param i_13 kolumn i_13
     */
    public void setI_13(int i_13) {
        this.i_13 = i_13;
        reportValues.put("i_13", i_13);
    }

    /**
     * Hämtar kolumn f_13 i tidrapporten.
     * @return kolumn f_13
     */
    public int getF_13() {
        return f_13;
    }

    /**
     * Sätter kolumn f_13 i tidrapporten (lägger även till i reportValues).
     * @param f_13 kolumn f_13
     */
    public void setF_13(int f_13) {
        this.f_13 = f_13;
        reportValues.put("f_13", f_13);
    }

    /**
     * Hämtar kolumn r_13 i tidrapporten.
     * @return kolumn r_13
     */
    public int getR_13() {
        return r_13;
    }

    /**
     * Sätter kolumn r_13 i tidrapporten (lägger även till i reportValues).
     * @param r_13 kolumn r_13
     */
    public void setR_13(int r_13) {
        this.r_13 = r_13;
        reportValues.put("r_13", r_13);
    }

    /**
     * Hämtar kolumn d_14 i tidrapporten.
     * @return kolumn d_14
     */
    public int getD_14() {
        return d_14;
    }

    /**
     * Sätter kolumn d_14 i tidrapporten (lägger även till i reportValues).
     * @param d_14 kolumn d_14
     */
    public void setD_14(int d_14) {
        this.d_14 = d_14;
        reportValues.put("d_14", d_14);
    }

    /**
     * Hämtar kolumn i_14 i tidrapporten.
     * @return kolumn i_14
     */
    public int getI_14() {
        return i_14;
    }

    /**
     * Sätter kolumn i_14 i tidrapporten (lägger även till i reportValues).
     * @param i_14 kolumn i_14
     */
    public void setI_14(int i_14) {
        this.i_14 = i_14;
        reportValues.put("i_14", i_14);
    }

    /**
     * Hämtar kolumn f_14 i tidrapporten.
     * @return kolumn f_14
     */
    public int getF_14() {
        return f_14;
    }

    /**
     * Sätter kolumn f_14 i tidrapporten (lägger även till i reportValues).
     * @param f_14 kolumn f_14
     */
    public void setF_14(int f_14) {
        this.f_14 = f_14;
        reportValues.put("f_14", f_14);
    }

    /**
     * Hämtar kolumn r_14 i tidrapporten.
     * @return kolumn r_14
     */
    public int getR_14() {
        return r_14;
    }

    /**
     * Sätter kolumn r_14 i tidrapporten (lägger även till i reportValues).
     * @param r_14 kolumn r_14
     */
    public void setR_14(int r_14) {
        this.r_14 = r_14;
        reportValues.put("r_14", r_14);
    }

    /**
     * Hämtar kolumn d_15 i tidrapporten.
     * @return kolumn d_15
     */
    public int getD_15() {
        return d_15;
    }

    /**
     * Sätter kolumn d_15 i tidrapporten (lägger även till i reportValues).
     * @param d_15 kolumn d_15
     */
    public void setD_15(int d_15) {
        this.d_15 = d_15;
        reportValues.put("d_15", d_15);
    }

    /**
     * Hämtar kolumn i_15 i tidrapporten.
     * @return kolumn i_15
     */
    public int getI_15() {
        return i_15;
    }

    /**
     * Sätter kolumn i_15 i tidrapporten (lägger även till i reportValues).
     * @param i_15 kolumn i_15
     */
    public void setI_15(int i_15) {
        this.i_15 = i_15;
        reportValues.put("i_15", i_15);
    }

    /**
     * Hämtar kolumn f_15 i tidrapporten.
     * @return kolumn f_15
     */
    public int getF_15() {
        return f_15;
    }

    /**
     * Sätter kolumn f_15 i tidrapporten (lägger även till i reportValues).
     * @param f_15 kolumn f_15
     */
    public void setF_15(int f_15) {
        this.f_15 = f_15;
        reportValues.put("f_15", f_15);
    }

    /**
     * Hämtar kolumn r_15 i tidrapporten.
     * @return kolumn r_15
     */
    public int getR_15() {
        return r_15;
    }

    /**
     * Sätter kolumn r_15 i tidrapporten (lägger även till i reportValues).
     * @param r_15 kolumn r_15
     */
    public void setR_15(int r_15) {
        this.r_15 = r_15;
        reportValues.put("r_15", r_15);
    }

    /**
     * Hämtar kolumn d_16 i tidrapporten.
     * @return kolumn d_16
     */
    public int getD_16() {
        return d_16;
    }

    /**
     * Sätter kolumn d_16 i tidrapporten (lägger även till i reportValues).
     * @param d_16 kolumn d_16
     */
    public void setD_16(int d_16) {
        this.d_16 = d_16;
        reportValues.put("d_16", d_16);
    }

    /**
     * Hämtar kolumn i_16 i tidrapporten.
     * @return kolumn i_16
     */
    public int getI_16() {
        return i_16;
    }

    /**
     * Sätter kolumn i_16 i tidrapporten (lägger även till i reportValues).
     * @param i_16 kolumn i_16
     */
    public void setI_16(int i_16) {
        this.i_16 = i_16;
        reportValues.put("i_16", i_16);
    }

    /**
     * Hämtar kolumn f_16 i tidrapporten.
     * @return kolumn f_16
     */
    public int getF_16() {
        return f_16;
    }

    /**
     * Sätter kolumn f_16 i tidrapporten (lägger även till i reportValues).
     * @param f_16 kolumn f_16
     */
    public void setF_16(int f_16) {
        this.f_16 = f_16;
        reportValues.put("f_16", f_16);
    }

    /**
     * Hämtar kolumn r_16 i tidrapporten.
     * @return kolumn r_16
     */
    public int getR_16() {
        return r_16;
    }

    /**
     * Sätter kolumn r_16 i tidrapporten (lägger även till i reportValues).
     * @param r_16 kolumn r_16
     */
    public void setR_16(int r_16) {
        this.r_16 = r_16;
        reportValues.put("r_16", r_16);
    }

    /**
     * Hämtar kolumn d_17 i tidrapporten.
     * @return kolumn d_17
     */
    public int getD_17() {
        return d_17;
    }

    /**
     * Sätter kolumn d_17 i tidrapporten (lägger även till i reportValues).
     * @param d_17 kolumn d_17
     */
    public void setD_17(int d_17) {
        this.d_17 = d_17;
        reportValues.put("d_17", d_17);
    }

    /**
     * Hämtar kolumn i_17 i tidrapporten.
     * @return kolumn i_17
     */
    public int getI_17() {
        return i_17;
    }

    /**
     * Sätter kolumn i_17 i tidrapporten (lägger även till i reportValues).
     * @param i_17 kolumn i_17
     */
    public void setI_17(int i_17) {
        this.i_17 = i_17;
        reportValues.put("i_17", i_17);
    }

    /**
     * Hämtar kolumn f_17 i tidrapporten.
     * @return kolumn f_17
     */
    public int getF_17() {
        return f_17;
    }

    /**
     * Sätter kolumn f_17 i tidrapporten (lägger även till i reportValues).
     * @param f_17 kolumn f_17
     */
    public void setF_17(int f_17) {
        this.f_17 = f_17;
        reportValues.put("f_17", f_17);
    }

    /**
     * Hämtar kolumn r_17 i tidrapporten.
     * @return kolumn r_17
     */
    public int getR_17() {
        return r_17;
    }

    /**
     * Sätter kolumn r_17 i tidrapporten (lägger även till i reportValues).
     * @param r_17 kolumn r_17
     */
    public void setR_17(int r_17) {
        this.r_17 = r_17;
        reportValues.put("r_17", r_17);
    }

    /**
     * Hämtar kolumn d_18 i tidrapporten.
     * @return kolumn d_18
     */
    public int getD_18() {
        return d_18;
    }

    /**
     * Sätter kolumn d_18 i tidrapporten (lägger även till i reportValues).
     * @param d_18 kolumn d_18
     */
    public void setD_18(int d_18) {
        this.d_18 = d_18;
        reportValues.put("d_18", d_18);
    }

    /**
     * Hämtar kolumn i_18 i tidrapporten.
     * @return kolumn i_18
     */
    public int getI_18() {
        return i_18;
    }

    /**
     * Sätter kolumn i_18 i tidrapporten (lägger även till i reportValues).
     * @param i_18 kolumn i_18
     */
    public void setI_18(int i_18) {
        this.i_18 = i_18;
        reportValues.put("i_18", i_18);
    }

    /**
     * Hämtar kolumn f_18 i tidrapporten.
     * @return kolumn f_18
     */
    public int getF_18() {
        return f_18;
    }

    /**
     * Sätter kolumn f_18 i tidrapporten (lägger även till i reportValues).
     * @param f_18 kolumn f_18
     */
    public void setF_18(int f_18) {
        this.f_18 = f_18;
        reportValues.put("f_18", f_18);
    }

    /**
     * Hämtar kolumn r_18 i tidrapporten.
     * @return kolumn r_18
     */
    public int getR_18() {
        return r_18;
    }

    /**
     * Sätter kolumn r_18 i tidrapporten (lägger även till i reportValues).
     * @param r_18 kolumn r_18
     */
    public void setR_18(int r_18) {
        this.r_18 = r_18;
        reportValues.put("r_18", r_18);
    }

    /**
     * Hämtar kolumn d_19 i tidrapporten.
     * @return kolumn d_19
     */
    public int getD_19() {
        return d_19;
    }

    /**
     * Sätter kolumn d_19 i tidrapporten (lägger även till i reportValues).
     * @param d_19 kolumn d_19
     */
    public void setD_19(int d_19) {
        this.d_19 = d_19;
        reportValues.put("d_19", d_19);
    }

    /**
     * Hämtar kolumn i_19 i tidrapporten.
     * @return kolumn i_19
     */
    public int getI_19() {
        return i_19;
    }

    /**
     * Sätter kolumn i_19 i tidrapporten (lägger även till i reportValues).
     * @param i_19 kolumn i_19
     */
    public void setI_19(int i_19) {
        this.i_19 = i_19;
        reportValues.put("i_19", i_19);
    }

    /**
     * Hämtar kolumn f_19 i tidrapporten.
     * @return kolumn f_19
     */
    public int getF_19() {
        return f_19;
    }

    /**
     * Sätter kolumn f_19 i tidrapporten (lägger även till i reportValues).
     * @param f_19 kolumn f_19
     */
    public void setF_19(int f_19) {
        this.f_19 = f_19;
        reportValues.put("f_19", f_19);
    }

    /**
     * Hämtar kolumn r_19 i tidrapporten.
     * @return kolumn r_19
     */
    public int getR_19() {
        return r_19;
    }

    /**
     * Sätter kolumn r_19 i tidrapporten (lägger även till i reportValues).
     * @param r_19 kolumn r_19
     */
    public void setR_19(int r_19) {
        this.r_19 = r_19;
        reportValues.put("r_19", r_19);
    }

    /**
     * Hämtar kolumn t_21 i tidrapporten.
     * @return kolumn t_21
     */
    public int getT_21() {
        return t_21;
    }

    /**
     * Sätter kolumn t_21 i tidrapporten (lägger även till i reportValues).
     * @param t_21 kolumn t_21
     */
    public void setT_21(int t_21) {
        this.t_21 = t_21;
        reportValues.put("t_21", t_21);
    }

    /**
     * Hämtar kolumn t_22 i tidrapporten.
     * @return kolumn t_22
     */
    public int getT_22() {
        return t_22;
    }

    /**
     * Sätter kolumn t_22 i tidrapporten (lägger även till i reportValues).
     * @param t_22 kolumn t_22
     */
    public void setT_22(int t_22) {
        this.t_22 = t_22;
        reportValues.put("t_22", t_22);
    }

    /**
     * Hämtar kolumn t_23 i tidrapporten.
     * @return kolumn t_23
     */
    public int getT_23() {
        return t_23;
    }

    /**
     * Sätter kolumn t_23 i tidrapporten (lägger även till i reportValues).
     * @param t_23 kolumn t_23
     */
    public void setT_23(int t_23) {
        this.t_23 = t_23;
        reportValues.put("t_23", t_23);
    }

    /**
     * Hämtar kolumn t_30 i tidrapporten.
     * @return kolumn t_30
     */
    public int getT_30() {
        return t_30;
    }

    /**
     * Sätter kolumn t_30 i tidrapporten (lägger även till i reportValues).
     * @param t_30 kolumn t_30
     */
    public void setT_30(int t_30) {
        this.t_30 = t_30;
        reportValues.put("t_30", t_30);
    }

    /**
     * Hämtar kolumn t_41 i tidrapporten.
     * @return kolumn t_41
     */
    public int getT_41() {
        return t_41;
    }

    /**
     * Sätter kolumn t_41 i tidrapporten (lägger även till i reportValues).
     * @param t_41 kolumn t_41
     */
    public void setT_41(int t_41) {
        this.t_41 = t_41;
        reportValues.put("t_41", t_41);
    }

    /**
     * Hämtar kolumn t_42 i tidrapporten.
     * @return kolumn t_42
     */
    public int getT_42() {
        return t_42;
    }

    /**
     * Sätter kolumn t_42 i tidrapporten (lägger även till i reportValues).
     * @param t_42 kolumn t_42
     */
    public void setT_42(int t_42) {
        this.t_42 = t_42;
        reportValues.put("t_42", t_42);
    }

    /**
     * Hämtar kolumn t_43 i tidrapporten.
     * @return kolumn t_43
     */
    public int getT_43() {
        return t_43;
    }

    /**
     * Sätter kolumn t_43 i tidrapporten (lägger även till i reportValues).
     * @param t_43 kolumn t_43
     */
    public void setT_43(int t_43) {
        this.t_43 = t_43;
        reportValues.put("t_43", t_43);
    }

    /**
     * Hämtar kolumn t_44 i tidrapporten.
     * @return kolumn t_44
     */
    public int getT_44() {
        return t_44;
    }

    /**
     * Sätter kolumn t_44 i tidrapporten (lägger även till i reportValues).
     * @param t_44 kolumn t_44
     */
    public void setT_44(int t_44) {
        this.t_44 = t_44;
        reportValues.put("t_44", t_44);
    }

    /**
     * Hämtar kolumn t_100 i tidrapporten.
     * @return kolumn t_100
     */
    public int getT_100() {
        return t_100;
    }

    /**
     * Sätter kolumn t_100 i tidrapporten (lägger även till i reportValues).
     * @param t_100 kolumn t_100
     */
    public void setT_100(int t_100) {
        this.t_100 = t_100;
        reportValues.put("t_100", t_100);
    }

    /**
     * Hämtar användarnamnet som rapporten ska skapas för.
     * @return Användarnamnet
     */
    public String getUser() {
        return user;
    }

    /**
     * Sätter användarnamnet som rapporten ska skapas för.
     * @param user Användarnamnet
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sätter gruppen som rapporten ska skapas för.
     * @return Namnet på gruppen.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Hämtar gruppen som rapporten ska skapas för.
     * @param group Namnet på gruppen.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Hämtar veckan som rapporten ska skapas för.
     * @return Veckan.
     */
    public int getWeek() {
        return week;
    }

    /**
     * Sätter veckan som rapporten ska skapas för.
     * @param week Veckan.
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * Hämtar en map med rapportvärden. I mappen är nyckeln kolumnnamnet och värdet antalet minuter som rapporterats.<br>
     *     <i>Denna fylls på när man sätter värdet på en kolumn i bönan.</i>
     * @return En map med rapportvärden.
     */
    public Map<String, Integer> getReportValues() {
        return reportValues;
    }

    /**
     * Sätter en map med rapportvärden. I mappen ska nyckeln vara kolumnnamnet och värdet antalet minuter som rapporterats.<br>
     *     <i>Denna fylls på när man sätter värdet på en kolumn i bönan.</i>
     * @param reportValues En map med rapportvärden.
     */
    public void setReportValues(Map<String, Integer> reportValues) {
        this.reportValues = reportValues;
    }

    /**
     * Sätter värdet på en kolumn i reportValues mappen.
     * @param column Kolumnen som ska sättas.
     * @param value Värdet på kolumnen.
     */
    public void setReportValues(String column, int value){
        reportValues.put(column, value);
    }

    /**
     * Sätter vilken typ av formulär informationen kommer ifrån.
     * @param type <b>"create"</b>: En ny rapport ska skapas.
     * <br><b>"update"</b>: En gammal rapport ska uppdateras.
     * <br<b>"remove"</b>: En rapport ska raderas.
     * <br><b>"weekSelect"</b>: En rapport med specificerad vecka ska hämtas från databasen.
     */
    public void setType(String type){
        this.type = type;
    }


    /**
     * Hämtar vilken typ av formulär informationen kommer ifrån.
     * @return <b>"create"</b>: En ny rapport ska skapas.
     * <br><b>"update"</b>: En gammal rapport ska uppdateras.
     * <br<b>"remove"</b>: En rapport ska raderas.
     * <br><b>"weekSelect"</b>: En rapport med specificerad vecka ska hämtas från databasen.
     */
    public String getType(){
        return type;
    }

    /**
     * Hämtar vilken flik på sidan användaren är på.
     * @return <b>"create"</b>: Fliken där man skapar nya rapporter.
     * <br><b>"update"</b>: Fliken där man uppdaterar rapporter.
     * <br><b>"remove"</b>: Fliken där man raderar rapporter.
     */
    public String getTab() {
        return tab;
    }

    /**
     * Sätter vilken flik på sidan användaren är på.
     * @return <b>"create"</b>: Fliken där man skapar nya rapporter.
     * <br><b>"update"</b>: Fliken där man uppdaterar rapporter.
     * <br><b>"remove"</b>: Fliken där man raderar rapporter.
     */
    public void setTab(String tab) {
        this.tab = tab;
    }
}
