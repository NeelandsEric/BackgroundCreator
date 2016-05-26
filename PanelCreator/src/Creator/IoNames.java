/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class IoNames implements java.io.Serializable {

    private static final long serialVersionUID = 1244L;

    private ArrayList<String> storeStr;
    private ArrayList<String> rackStr;
    private ArrayList<String> condStr;
    private ArrayList<String> sgStr;
    private ArrayList<String> compStr;
    private ArrayList<String> sysStr;
    private ArrayList<String> extraStr;
    private ArrayList<String> glycolStr;
    private ArrayList<String> fanPanelStr;

    private static final String[] HEADERS = {"io_name", "io_type", "io_value",
        "io_unit_of_measure", "io_constant", "io_offset", "io_float_digits",
        "io_alert", "io_alert_timeout", "io_alert_range_low",
        "io_alert_range_high", "io_log", "io_log_param1", "io_log_range",
        "io_data_logger"
           
    };

    public IoNames() {
        storeStr = new ArrayList<>();
        rackStr = new ArrayList<>();
        condStr = new ArrayList<>();
        sgStr = new ArrayList<>();
        compStr = new ArrayList<>();
        sysStr = new ArrayList<>();
        extraStr = new ArrayList<>();
        glycolStr = new ArrayList<>();
        fanPanelStr = new ArrayList<>();

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.storeStr);
        hash = 41 * hash + Objects.hashCode(this.condStr);
        hash = 41 * hash + Objects.hashCode(this.sgStr);
        hash = 41 * hash + Objects.hashCode(this.compStr);
        hash = 41 * hash + Objects.hashCode(this.sysStr);
        hash = 41 * hash + Objects.hashCode(this.extraStr);
        hash = 41 * hash + Objects.hashCode(this.glycolStr);
        hash = 41 * hash + Objects.hashCode(this.fanPanelStr);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IoNames other = (IoNames) obj;
        if (!Objects.equals(this.storeStr, other.storeStr)) {
            return false;
        }
        if (!Objects.equals(this.rackStr, other.rackStr)) {
            return false;
        }
        if (!Objects.equals(this.condStr, other.condStr)) {
            return false;
        }
        if (!Objects.equals(this.sgStr, other.sgStr)) {
            return false;
        }
        if (!Objects.equals(this.compStr, other.compStr)) {
            return false;
        }
        if (!Objects.equals(this.sysStr, other.sysStr)) {
            return false;
        }
        if (!Objects.equals(this.extraStr, other.extraStr)) {
            return false;
        }
        if (!Objects.equals(this.glycolStr, other.glycolStr)) {
            return false;
        }
        if (!Objects.equals(this.fanPanelStr, other.fanPanelStr)) {
            return false;
        }
        return true;
    }

    public ArrayList<String> getStoreStr() {
        return storeStr;
    }

    public void setStoreStr(ArrayList<String> storeStr) {
        this.storeStr = storeStr;
    }

    public ArrayList<String> getRackStr() {
        return rackStr;
    }

    public void setRackStr(ArrayList<String> rackStr) {
        this.rackStr = rackStr;
    }

    public ArrayList<String> getCondStr() {
        return condStr;
    }

    public void setCondStr(ArrayList<String> condStr) {
        this.condStr = condStr;
    }

    public ArrayList<String> getSgStr() {
        return sgStr;
    }

    public void setSgStr(ArrayList<String> sgStr) {
        this.sgStr = sgStr;
    }

    public ArrayList<String> getCompStr() {
        return compStr;
    }

    public void setCompStr(ArrayList<String> compStr) {
        this.compStr = compStr;
    }

    public ArrayList<String> getSysStr() {
        return sysStr;
    }

    public void setSysStr(ArrayList<String> sysStr) {
        this.sysStr = sysStr;
    }

    public ArrayList<String> getExtraStr() {
        return extraStr;
    }

    public void setExtraStr(ArrayList<String> extraStr) {
        this.extraStr = extraStr;
    }

    public ArrayList<String> getGlycolStr() {
        return glycolStr;
    }

    public void setGlycolStr(ArrayList<String> glycolStr) {
        this.glycolStr = glycolStr;
    }

    public ArrayList<String> getFanPanelStr() {
        return fanPanelStr;
    }

    public void setFanPanelStr(ArrayList<String> fanPanelStr) {
        this.fanPanelStr = fanPanelStr;
    }

    public void addString(int listIndex, String string) {

        switch (listIndex) {
            case 0:
                storeStr.add(string);
                break;
            case 1:
                rackStr.add(string);
                break;
            case 2:
                condStr.add(string);
                break;
            case 3:
                sgStr.add(string);
                break;
            case 4:
                compStr.add(string);
                break;
            case 5:
                sysStr.add(string);
                break;
            case 6:
                extraStr.add(string);
                break;
            case 7:
                glycolStr.add(string);
                break;
            case 8:
                fanPanelStr.add(string);
                break;
            default:
                System.out.println("Didnt add " + string);
                break;
        }

    }

    public void replaceString(int listIndex, int arrayIndex, String string) {
        String replaced;
        switch (listIndex) {
            case 0:
                replaced = storeStr.set(arrayIndex, string);
                break;
            case 1:
                replaced = rackStr.set(arrayIndex, string);
                break;
            case 2:
                replaced = condStr.set(arrayIndex, string);
                break;
            case 3:
                replaced = sgStr.set(arrayIndex, string);
                break;
            case 4:
                replaced = compStr.set(arrayIndex, string);
                break;
            case 5:
                replaced = sysStr.set(arrayIndex, string);
                break;
            case 6:
                replaced = extraStr.set(arrayIndex, string);
                break;
            case 7:
                replaced = glycolStr.set(arrayIndex, string);
                break;
            case 8:
                replaced = fanPanelStr.set(arrayIndex, string);
                break;
            default:
                System.out.println("Didnt add " + string);
                replaced = "None";
                break;
        }
        System.out.println("Replaced {" + replaced + "} with {" + string + "}.");
    }

    public List<String[]> formatStrings(ControlSettings cs) {

        List<String[]> vars = new ArrayList<>();
        int numfans, numsg, numcomp, numsys;
        String[] newString;
        Rack r;
        String rName, sgName, fannum = "1";
        SuctionGroup sucG;
        String compName, sysName;

        for (int i = 0; i < cs.getNumRacks(); i++) {
            // do all racks
            r = cs.getRacks().get(i);
            rName = r.getName();
            sgName = r.getSuctionGroupNameIndex(0);
            fannum = "1";
            sucG = r.getSuctionGroupIndex(0);
            compName = sucG.getCompressorNameIndex(0);
            sysName = sucG.getSystemNameIndex(0);

            // RACKS
            // do all condenser     
            for (String s : rackStr) {
                newString = s.split(",");
                newString[0] = newString[0]
                        .replace("`%rackname`", rName)
                        .replace("`%fannum`", fannum)
                        .replace("`%sgname`", sgName)
                        .replace("`%compname`", compName)
                        .replace("`%sysname`", sysName);

                //System.out.println("RACK - New string: " + newString[0] + "\tFrom old string: " + s);
                vars.add(newString);
            }

            // CONDENSERS
            numfans = r.getNumCondenserFans();
            for (int nf = 0; nf < numfans; nf++) {

                fannum = ('0' + String.valueOf((nf + 1)));
                if (nf < 99) {
                    fannum = fannum.substring(fannum.length() - 2);
                } else {
                    fannum = fannum.substring(fannum.length() - 3);
                }

                // do all condenser     
                for (String s : condStr) {
                    newString = s.split(",");
                    newString[0] = newString[0]
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("COND - New string: " + newString[0] + "\tFrom old string: " + s);
                    vars.add(newString);
                }
            }

            // SUCTION GROUPS
            numsg = r.getNumSuctionGroups();
            for (int sg = 0; sg < numsg; sg++) {
                sucG = r.getSuctionGroupIndex(sg);
                sgName = sucG.getName();
                for (String s : sgStr) {
                    newString = s.split(",");
                    newString[0] = newString[0]
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("SG - New string: " + newString[0] + "\tFrom old string: " + s);
                    vars.add(newString);
                }

                // do all suction groups
                // COMPRESSORS
                numcomp = sucG.getNumCompressors();
                for (int nc = 0; nc < numcomp; nc++) {
                    compName = sucG.getCompressorNameIndex(nc);
                    // do all compressors
                    for (String s : compStr) {
                        newString = s.split(",");
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("COMP - New string: " + newString[0] + "\tFrom old string: " + s);
                        vars.add(newString);
                    }
                }

                // SYSTEMS
                numsys = sucG.getNumSystems();
                for (int ns = 0; ns < numsys; ns++) {
                    sysName = sucG.getSystemNameIndex(ns);
                    // do all systems
                    for (String s : sysStr) {
                        newString = s.split(",");
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("SYS - New string: " + newString[0] + "\tFrom old string: " + s);
                        vars.add(newString);
                    }

                }

            }
        }

        for (String s : storeStr) {
            newString = s.split(",");

            // Only add glycol strings if it is a glycol store
            if (!cs.isGlycolStore()) {
                if (!newString[0].startsWith("Glycol")) {
                    vars.add(newString);
                }
                //System.out.println("STORE - New string: " + newString[0] + "\tFrom old string: " + s);
            } else {
                vars.add(newString);
            }
        }

        for (String s : extraStr) {
            newString = s.split(",");

            //System.out.println("EXTRA - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        if (cs.isGlycolStore()) {
            for (int ngs = 0; ngs < cs.glycolSettings.getNumGlycolSystems(); ngs++) {
                for (String s : glycolStr) {
                    newString = s.split(",");
                    newString[0] = newString[0].replace("`%glycolname`", cs.glycolSettings.getGlycolSystemNameIndex(ngs));
                    vars.add(newString);
                }
            }
        }

        for (int nfp = 1; nfp <= cs.getNumFanPanels(); nfp++) {
            for (String s : fanPanelStr) {
                newString = s.split(",");
                newString[0] = newString[0].replace("`%fanpanelnum`", String.valueOf((nfp < 10 ? "0" + nfp : nfp)));
                vars.add(newString);
            }
        }

        Collections.sort(vars, new Comparator< String[]>() {
            @Override
            public int compare(String[] x1, String[] x2) {
                return x1[0].toLowerCase().compareTo(x2[0].toLowerCase());
            }
        });

        // Add header
        vars.add(0, HEADERS);

        return vars;

    }

    public Map<String, List> mapFullStrings(ControlSettings cs) {

        Map<String, List> mappings = new TreeMap<>();

        int numfans, numsg, numcomp, numsys;
        String newString, orgString;
        Rack r;
        String rName, sgName, fannum = "1";
        SuctionGroup sucG;
        String compName, sysName;

        for (int i = 0; i < cs.getNumRacks(); i++) {
            // do all racks
            r = cs.getRacks().get(i);
            rName = r.getName();
            sgName = r.getSuctionGroupNameIndex(0);
            fannum = "1";
            sucG = r.getSuctionGroupIndex(0);
            compName = sucG.getCompressorNameIndex(0);
            sysName = sucG.getSystemNameIndex(0);

            // RACKS
            // do all condenser     
            for (String s : rackStr) {
                orgString = s.split(",")[0];
                newString = orgString
                        .replace("`%rackname`", rName)
                        .replace("`%fannum`", fannum)
                        .replace("`%sgname`", sgName)
                        .replace("`%compname`", compName)
                        .replace("`%sysname`", sysName);

                //System.out.println("RACK - New string: " + newString[0] + "\tFrom old string: " + s);
                if (!mappings.containsKey(orgString)) {
                    mappings.put(orgString, new ArrayList<>());
                }
                mappings.get(orgString).add(newString);
            }

            // CONDENSERS
            numfans = r.getNumCondenserFans();
            for (int nf = 0; nf < numfans; nf++) {

                fannum = ('0' + String.valueOf((nf + 1)));
                if (nf < 99) {
                    fannum = fannum.substring(fannum.length() - 2);
                } else {
                    fannum = fannum.substring(fannum.length() - 3);
                }

                // do all condenser     
                for (String s : condStr) {
                    orgString = s.split(",")[0];
                    newString = orgString
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("COND - New string: " + newString[0] + "\tFrom old string: " + s);
                    if (!mappings.containsKey(orgString)) {
                        mappings.put(orgString, new ArrayList<>());
                    }
                    mappings.get(orgString).add(newString);
                }
            }

            // SUCTION GROUPS
            numsg = r.getNumSuctionGroups();
            for (int sg = 0; sg < numsg; sg++) {
                sucG = r.getSuctionGroupIndex(sg);
                sgName = sucG.getName();
                for (String s : sgStr) {
                    orgString = s.split(",")[0];
                    newString = orgString
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("SG - New string: " + newString[0] + "\tFrom old string: " + s);
                    if (!mappings.containsKey(orgString)) {
                        mappings.put(orgString, new ArrayList<>());
                    }
                    mappings.get(orgString).add(newString);
                }

                // do all suction groups
                // COMPRESSORS
                numcomp = sucG.getNumCompressors();
                for (int nc = 0; nc < numcomp; nc++) {
                    compName = sucG.getCompressorNameIndex(nc);
                    // do all compressors
                    for (String s : compStr) {
                        orgString = s.split(",")[0];
                        newString = orgString
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("COMP - New string: " + newString[0] + "\tFrom old string: " + s);
                        if (!mappings.containsKey(orgString)) {
                            mappings.put(orgString, new ArrayList<>());
                        }
                        mappings.get(orgString).add(newString);
                    }
                }

                // SYSTEMS
                numsys = sucG.getNumSystems();
                for (int ns = 0; ns < numsys; ns++) {
                    sysName = sucG.getSystemNameIndex(ns);
                    // do all systems
                    for (String s : sysStr) {
                        orgString = s.split(",")[0];
                        newString = orgString
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("SYS - New string: " + newString[0] + "\tFrom old string: " + s);
                        if (!mappings.containsKey(orgString)) {
                            mappings.put(orgString, new ArrayList<>());
                        }
                        mappings.get(orgString).add(newString);
                    }

                }

            }
        }

        for (String s : storeStr) {
            newString = s.split(",")[0];

            //System.out.println("STORE - New string: " + newString[0] + "\tFrom old string: " + s);
            if (!mappings.containsKey(newString)) {
                mappings.put(newString, new ArrayList<>());
            }
            mappings.get(newString).add(newString);
        }

        for (String s : extraStr) {
            newString = s.split(",")[0];

            //System.out.println("EXTRA - New string: " + newString[0] + "\tFrom old string: " + s);
            if (!mappings.containsKey(newString)) {
                mappings.put(newString, new ArrayList<>());
            }
            mappings.get(newString).add(newString);
        }

        if (cs.isGlycolStore()) {
            for (int ngs = 0; ngs < cs.getGlycolSettings().getNumGlycolSystems(); ngs++) {
                for (String s : glycolStr) {
                    orgString = s.split(",")[0];
                    newString = orgString.replace("`%glycolname`", cs.getGlycolSysNameIndex(ngs));
                    if (!mappings.containsKey(orgString)) {
                        mappings.put(orgString, new ArrayList<>());
                    }
                    mappings.get(orgString).add(newString);
                }

            }
        }

        for (int nfp = 1; nfp <= cs.getNumFanPanels(); nfp++) {
            for (String s : fanPanelStr) {
                orgString = s.split(",")[0];
                newString = orgString.replace("`%fanpanelnum`", String.valueOf((nfp < 10 ? "0" + nfp : nfp)));
                if (!mappings.containsKey(orgString)) {
                    mappings.put(orgString, new ArrayList<>());
                }
                mappings.get(orgString).add(newString);
            }

        }

        return mappings;

    }

    public List<String[]> formatStringsNoParams(ControlSettings cs, boolean addHeader) {

        List<String[]> vars = new ArrayList<>();

        // Add header
        // Add header
        if (addHeader) {
            vars.add(HEADERS);
        }

        int numfans, numsg, numcomp, numsys;
        String[] newString;
        Rack r;
        String rName, sgName, fannum = "1";
        SuctionGroup sucG;
        String compName, sysName;

        for (int i = 0; i < cs.getNumRacks(); i++) {
            // do all racks
            r = cs.getRacks().get(i);
            rName = r.getName();
            sgName = r.getSuctionGroupNameIndex(0);
            fannum = "1";
            sucG = r.getSuctionGroupIndex(0);
            compName = sucG.getCompressorNameIndex(0);
            sysName = sucG.getSystemNameIndex(0);

            // RACKS
            // do all condenser     
            for (String s : rackStr) {
                newString = new String[]{s.split(",")[0]};
                newString[0] = newString[0]
                        .replace("`%rackname`", rName)
                        .replace("`%fannum`", fannum)
                        .replace("`%sgname`", sgName)
                        .replace("`%compname`", compName)
                        .replace("`%sysname`", sysName);

                //System.out.println("RACK - New string: " + newString[0] + "\tFrom old string: " + s);
                vars.add(newString);
            }

            // CONDENSERS
            numfans = r.getNumCondenserFans();
            for (int nf = 0; nf < numfans; nf++) {

                fannum = ('0' + String.valueOf((nf + 1)));
                if (nf < 99) {
                    fannum = fannum.substring(fannum.length() - 2);
                } else {
                    fannum = fannum.substring(fannum.length() - 3);
                }

                // do all condenser     
                for (String s : condStr) {
                    newString = new String[]{s.split(",")[0]};
                    newString[0] = newString[0]
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("COND - New string: " + newString[0] + "\tFrom old string: " + s);
                    vars.add(newString);
                }
            }

            // SUCTION GROUPS
            numsg = r.getNumSuctionGroups();
            for (int sg = 0; sg < numsg; sg++) {
                sucG = r.getSuctionGroupIndex(sg);
                sgName = sucG.getName();
                for (String s : sgStr) {
                    newString = new String[]{s.split(",")[0]};
                    newString[0] = newString[0]
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("SG - New string: " + newString[0] + "\tFrom old string: " + s);
                    vars.add(newString);
                }

                // do all suction groups
                // COMPRESSORS
                numcomp = sucG.getNumCompressors();
                for (int nc = 0; nc < numcomp; nc++) {
                    compName = sucG.getCompressorNameIndex(nc);
                    // do all compressors
                    for (String s : compStr) {
                        newString = new String[]{s.split(",")[0]};
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("COMP - New string: " + newString[0] + "\tFrom old string: " + s);
                        vars.add(newString);
                    }
                }

                // SYSTEMS
                numsys = sucG.getNumSystems();
                for (int ns = 0; ns < numsys; ns++) {
                    sysName = sucG.getSystemNameIndex(ns);
                    // do all systems
                    for (String s : sysStr) {
                        newString = new String[]{s.split(",")[0]};
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("SYS - New string: " + newString[0] + "\tFrom old string: " + s);
                        vars.add(newString);
                    }

                }

            }
        }

        for (String s : storeStr) {
            newString = new String[]{s.split(",")[0]};

            //System.out.println("STORE - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        for (String s : extraStr) {
            newString = new String[]{s.split(",")[0]};

            //System.out.println("EXTRA - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        if (cs.isGlycolStore()) {
            for (int ngs = 0; ngs < cs.glycolSettings.getNumGlycolSystems(); ngs++) {
                for (String s : glycolStr) {
                    newString = new String[]{s.split(",")[0]};
                    newString[0] = newString[0].replace("`%glycolname`", cs.glycolSettings.getGlycolSystemNameIndex(ngs));
                    vars.add(newString);
                }
            }
        }

        for (int nfp = 1; nfp <= cs.getNumFanPanels(); nfp++) {
            for (String s : fanPanelStr) {
                newString = new String[]{s.split(",")[0]};
                newString[0] = newString[0].replace("`%fanpanelnum`", String.valueOf((nfp < 10 ? "0" + nfp : nfp)));
                vars.add(newString);
            }
        }

        return vars;

    }

    public List<String[]> unformattedStrings() {

        List<String[]> vars = new ArrayList<>();

        vars.add(HEADERS);
        // Store
        vars.add(new String[]{"`Store`"});
        for (String s : storeStr) {
            vars.add(s.split(","));
        }

        // Racks
        vars.add(new String[]{"`Rack`"});
        for (String s : rackStr) {
            vars.add(s.split(","));
        }

        // Cond
        vars.add(new String[]{"`Condenser`"});
        for (String s : condStr) {
            vars.add(s.split(","));
        }

        // Suction Group
        vars.add(new String[]{"`Suction Group`"});
        for (String s : sgStr) {
            vars.add(s.split(","));
        }

        // System
        vars.add(new String[]{"`System`"});
        for (String s : sysStr) {
            vars.add(s.split(","));
        }

        // Compressor
        vars.add(new String[]{"`Compressor`"});
        for (String s : compStr) {
            vars.add(s.split(","));
        }

        // Extra
        if (!extraStr.isEmpty()) {
            vars.add(new String[]{"`Extra`"});
            for (String s : extraStr) {
                vars.add(s.split(","));
            }
        }

        // Glycol
        vars.add(new String[]{"`Glycol`"});
        for (String s : glycolStr) {
            vars.add(s.split(","));
        }

        // Fan Panel
        vars.add(new String[]{"`FanPanel`"});
        for (String s : fanPanelStr) {
            vars.add(s.split(","));
        }

        return vars;

    }

    public List<String[]> nameStrings() {

        List<String[]> vars = new ArrayList<>();

        // Store
        vars.add(new String[]{"`Store`"});
        for (String s : storeStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Racks
        vars.add(new String[]{"`Rack`"});
        for (String s : rackStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Cond
        vars.add(new String[]{"`Condenser`"});
        for (String s : condStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Suction Group
        vars.add(new String[]{"`Suction Group`"});
        for (String s : sgStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // System
        vars.add(new String[]{"`System`"});
        for (String s : sysStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Compressor
        vars.add(new String[]{"`Compressor`"});
        for (String s : compStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Extra
        if (!extraStr.isEmpty()) {
            vars.add(new String[]{"`Extra`"});
            for (String s : extraStr) {
                vars.add(new String[]{s.split(",")[0]});
            }
        }
        
        // Glycol
        vars.add(new String[]{"`Glycol`"});
        for (String s : glycolStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Fan Panel
        vars.add(new String[]{"`FanPanel`"});
        for (String s : fanPanelStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        return vars;

    }
}
