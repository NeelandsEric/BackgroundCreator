/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EricGummerson
 */
public class IoNames implements java.io.Serializable {

    private ArrayList<String> storeStr;
    private ArrayList<String> rackStr;
    private ArrayList<String> condStr;
    private ArrayList<String> sgStr;
    private ArrayList<String> compStr;
    private ArrayList<String> sysStr;
    private ArrayList<String> extraStr;

    public IoNames() {
        storeStr = new ArrayList<>();
        rackStr = new ArrayList<>();
        condStr = new ArrayList<>();
        sgStr = new ArrayList<>();
        compStr = new ArrayList<>();
        sysStr = new ArrayList<>();
        extraStr = new ArrayList<>();

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
            default:
                System.out.println("Didnt add " + string);
                replaced = "None";
                break;
        }
        System.out.println("Replaced {" + replaced + "} with {" + string + "}.");
    }

    public List<String[]> formatStrings(ControlSettings cs) {

        long start = System.currentTimeMillis(), end;
        List<String[]> vars = new ArrayList<String[]>() {
        };

        // Add header
        // Add header
        String[] headers = new String[]{"io_name", "io_type", "io_value_displayed", 
            "io_unit_of_measure", "io_constant", "io_offset", "io_float_digits",
            "io_alert", "io_alert_range_low", "io_alert_range_high"            
        };
        vars.add(headers);

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

            //System.out.println("STORE - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        for (String s : extraStr) {
            newString = s.split(",");

            //System.out.println("EXTRA - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        end = System.currentTimeMillis();

        System.out.println("Format strings took " + ((end - start)) + " ms");
        return vars;

    }
    
    public List<String[]> formatStringsNoParams(ControlSettings cs) {

        long start = System.currentTimeMillis(), end;
        List<String[]> vars = new ArrayList<String[]>() {
        };

        // Add header
        // Add header
        String[] headers = new String[]{"io_name", "io_id"};
        vars.add(headers);

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

        end = System.currentTimeMillis();

        System.out.println("Format strings took " + ((end - start)) + " ms");
        return vars;

    }

    public List<String[]> unformattedStrings() {

        List<String[]> vars = new ArrayList<String[]>() {
        };

        // Add header
         String[] headers = new String[]{"io_name", "io_type", "io_value_displayed", 
            "io_unit_of_measure", "io_constant", "io_offset", "io_float_digits",
            "io_alert", "io_alert_range_low", "io_alert_range_high"            
        };
        vars.add(headers);
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

        return vars;

    }

    public List<String[]> nameStrings() {

        List<String[]> vars = new ArrayList<String[]>() {
        };

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

        return vars;

    }
}
