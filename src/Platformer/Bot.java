package Platformer;


import java.awt.*;

public class Bot extends Enemy {
    public Bot(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    public void step() {
        super.step();
    }

    public boolean facingRight() {
        return rightState;
    }

    public void paint(Graphics2D g) {

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    void remove() {

    }

    void spawn() {

    }

    public String[] getImgSources() {
        return new String[]{"src/Platformer/Images/stockImage.png"};
    }

    public void setImgIndex(int[] i) {
        imgIndex = i;
    }

    public int getImgIndex() {
        return imgIndex[0];
    }
}
