/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

public class LinkInfo {

    public int panelID;
    public String panelName;
    public int xPos;
    public int yPos;

    public LinkInfo() {
    }

    public LinkInfo(int panelID, String panelName, int xPos, int yPos) {
        this.panelID = panelID;
        this.panelName = panelName;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getPanelID() {
        return panelID;
    }

    public void setPanelID(int panelID) {
        this.panelID = panelID;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        System.out.println(this.panelName + " => " + panelName);
        this.panelName = panelName;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

}
