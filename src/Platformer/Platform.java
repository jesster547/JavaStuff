package Platformer;

import java.awt.*;

public class Platform implements Entity {
    //Initializes instance variables
    private int x, y, w, h;
    Room room;
    int[] imgIndex;

    //Sets Instance Variables
    public Platform(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    //Required for Entity interface
    public void step() {
    }

    //Displays Platform on screen
    public void paint(Graphics2D g) {
        //g.setColor(new Color(170, 0, 255));
        g.setColor(new Color(74, 131, 36));
        g.fillRect(x - room.getCamX(), y, w, h);
    }

    //Returns the dimensions of the Platform as a Rectangle
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    //Binds platform to room
    public void setRoom(Room room) {
        this.room = room;
    }

    //Provides X Position
    public int getX() {
        return x;
    }

    //Provides Y Position
    public int getY() {
        return y;
    }

    //Will always face right (No need to flip image)
    public boolean facingRight() {
        return true;
    }

    //Provides file name for images used (Provide empty string if no image is added)
    public String[] getImgSources() {
        return new String[]{"src/Platformer/Images/notARealStockImage.png"};
    }

    //Sets the indexes for the scaled images
    public void setImgIndex(int[] i) {
        imgIndex = i;
    }

    //Uses index given by room to choose the image to be displayed
    public int getImgIndex() {
        return (imgIndex[0]);
    }

}