/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rosh
 */
public class DrawObject {

    public DrawObject(int startX, int startY, int endX, int endY) {
        this.sX = startX;
        this.sY = startY;
        this.eX = endX;
        this.eY = endY;
    }
    int sX,sY,eX,eY;

    public int getStartX() {
        return sX;
    }

    public void setStartX(int startX) {
        this.sX = startX;
    }

    public int getStartY() {
        return sY;
    }

    @Override
    public String toString() {
        return "DrawObject{" + "sX=" + sX + ", sY=" + sY + ", eX=" + eX + ", eY=" + eY + '}';
    }

    public void setStartY(int startY) {
        this.sY = startY;
    }

    public int geteX() {
        return eX;
    }

    public void seteX(int eX) {
        this.eX = eX;
    }

    public int getEndY() {
        return eY;
    }

    public void setEndY(int endY) {
        this.eY = endY;
    }

    
}
