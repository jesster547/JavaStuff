package Platformer;

import java.awt.*;

public class Background implements Entity {
    private int[] imgIndex;
    private int x;
    private int y;
    private final int w;
    private final int h;
    private final String imgSrc;
    private Room room;
    /* The first sprite to be added to any room (unless object is meant to hide behind the background)
    * Add the source path of the image in the constructor and set width and height of the room to set background image
    * Horizontal/vertical repetition to be added    */

    public Background(int w, int h, String imgSrc){
        this.w = w;
        this.h = h;
        this.imgSrc = imgSrc;
        System.out.println(this.w+" "+ this.h);
    }

    public void step() {
    }

    public boolean facingRight() {
        return true;
    }

    public void paint(Graphics2D g) {
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, w, h);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String[] getImgSources() {
        return new String[]{this.imgSrc};
    }

    public void setImgIndex(int[] nums) {
        imgIndex = nums;
    }

    public int getImgIndex() {
        return imgIndex[0];
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }
}
