package ekyss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *  Denna klass är bönan för för webbsidan "Veckorapporteringshanterare".
 */
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

    /**
     * Hämtar vilken typ av formulär informationen kommer ifrån.
     * @return <b>"sign"</b>: En eller flera rapporter ska signeras.
     * <br><b>"unsign"</b>: En eller flera rapporter ska annulleras.
     */
    public String getType() { return type; }

    /**
     * Sätter vilken typ av formulär informationen kommer ifrån.
     * @param type <b>"sign"</b>: En eller flera rapporter ska signeras.
     * <br><b>"unsign"</b>: En eller flera rapporter ska annulleras.
     */
    public void setType(String type){ this.type = type; }

    /**
     * Hämtar vilken grupp rapporterna ska signeras/annulleras för.
     * @return Gruppen rapporterna ska signeras/annulleras för.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sätter vilken grupp rapporterna ska signeras/annulleras för.
     * @return Gruppen rapporterna ska signeras/annulleras för.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Hämtar vilken flik användaren är inne på.
     * @return <b>"sign"</b>: Fliken där signering av rapporter sker.
     * <br><b>"unsign"</b>: Fliken där annullering av rapporter sker.
     */
    public String getTab() {
        return tab;
    }

    /**
     * Sätter vilken flik användaren är inne på.
     * @param tab <b>"sign"</b>: Fliken där signering av rapporter sker.
     * <br><b>"unsign"</b>: Fliken där annullering av rapporter sker.
     */
    public void setTab(String tab) {
        this.tab = tab;
    }

    /**
     * Hämtar en lista av strängvektorer som visar alla osignerade rapporter för en specifik grupp.
     * @return En lista av strängvektorer som är uppbygda enligt följande:
     * <b>[0]</b>: veckan<br>
     * <b>[1]</b>: användaren<br>
     * <b>[2]</b>: tid rapporterad för t_d<br>
     * <b>[3]</b>: tid rapporterad för t_i<br>
     * <b>[4]</b>: tid rapporterad för t_f<br>
     * <b>[5]</b>: tid rapporterad för t_r<br>
     * <b>[6]</b>: tid rapporterad för övrigt (21-100)<br>
     * <b>[7]</b>: total tid rapporterad.
     */
    public List<String[]> getAllUnsignedReports() {
        return allUnsignedReports;
    }

    /**
     * Sätter en lista av strängvektorer som visar alla osignerade rapporter för en specifik grupp.
     * @param allUnsignedReports En lista av strängvektorer som är uppbygda enligt följande:
     * <b>[0]</b>: veckan<br>
     * <b>[1]</b>: användaren<br>
     * <b>[2]</b>: tid rapporterad för t_d<br>
     * <b>[3]</b>: tid rapporterad för t_i<br>
     * <b>[4]</b>: tid rapporterad för t_f<br>
     * <b>[5]</b>: tid rapporterad för t_r<br>
     * <b>[6]</b>: tid rapporterad för övrigt (21-100)<br>
     * <b>[7]</b>: total tid rapporterad.
     */
    public void setAllUnsignedReports(List<String[]> allUnsignedReports) {
        this.allUnsignedReports = allUnsignedReports;
    }

    /**
     * Hämtar en lista av strängvektorer som visar alla signerade rapporter för en specifik grupp.
     * @return En lista av strängvektorer som är uppbygda enligt följande:
     * <b>[0]</b>: veckan<br>
     * <b>[1]</b>: användaren<br>
     * <b>[2]</b>: tid rapporterad för t_d<br>
     * <b>[3]</b>: tid rapporterad för t_i<br>
     * <b>[4]</b>: tid rapporterad för t_f<br>
     * <b>[5]</b>: tid rapporterad för t_r<br>
     * <b>[6]</b>: tid rapporterad för övrigt (21-100)<br>
     * <b>[7]</b>: total tid rapporterad.
     */
    public List<String[]> getAllSignedReports() {
        return allSignedReports;
    }

    /**
     * Sätter en lista av strängvektorer som visar alla signerade rapporter för en specifik grupp.
     * @param allSignedReports En lista av strängvektorer som är uppbygda enligt följande:
     * <b>[0]</b>: veckan<br>
     * <b>[1]</b>: användaren<br>
     * <b>[2]</b>: tid rapporterad för t_d<br>
     * <b>[3]</b>: tid rapporterad för t_i<br>
     * <b>[4]</b>: tid rapporterad för t_f<br>
     * <b>[5]</b>: tid rapporterad för t_r<br>
     * <b>[6]</b>: tid rapporterad för övrigt (21-100)<br>
     * <b>[7]</b>: total tid rapporterad.
     */
    public void setAllSignedReports(List<String[]> allSignedReports) {
        this.allSignedReports = allSignedReports;
    }

    /**
     * Hämtar en strängvektor som visar alla rapporter som ska signeras.
     * @return En strängvektor där varje sträng är i formatet "<b><u>vecka</u></b> <b><u>användarnamn</u></b>"
     */
    public String[] getSignReport() {
        return signReport;
    }

    /**
     * Sätter en strängvektor som visar alla rapporter som ska signeras.
     * @param signReport En strängvektor där varje sträng är i formatet "<b><u>vecka</u></b> <b><u>användarnamn</u></b>"
     */
    public void setSignReport(String[] signReport) {
        this.signReport = signReport;
    }

    /**
     * Hämtar en strängvektor som visar alla rapporter som ska annulleras.
     * @return En strängvektor där varje sträng är i formatet "<b><u>vecka</u></b> <b><u>användarnamn</u></b>"
     */
    public String[] getUnsignReport() {
        return unsignReport;
    }

    /**
     * Sätter en strängvektor som visar alla rapporter som ska annulleras.
     * @param unsignReport En strängvektor där varje sträng är i formatet "<b><u>vecka</u></b> <b><u>användarnamn</u></b>"
     */
    public void setUnsignReport(String[] unsignReport) {
        this.unsignReport = unsignReport;
    }

    /**
     * Hämtar en felkod som används i ReportManagement.jsp
     * @return <b>1</b>: Alla valda rapporter har signerats.
     *     <br><b>2</b>: Alla valda rapporter har annullerats.
     */
    public int getErr_code() {
        return err_code;
    }

    /**
     * Sätter en felkod som används i ReportManagement.jsp
     * @param err_code <b>1</b>: Alla valda rapporter har signerats.
     *     <br><b>2</b>: Alla valda rapporter har annullerats.
     */
    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }
}
