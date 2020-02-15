package yep;

import java.awt.*;

public class Platform implements Entity {
    //Initializes instance variables
    private int x, y, w, h;
    Room room;

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
        g.setColor(new Color(170, 0, 255));
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
}