package yep;

import java.awt.*;

public class Platform implements Entity {
    //Initializes instance variables
    private int x, y, w, h;
    Game game;

    //Sets Instance Variables
    public Platform(int x, int y, int w, int h, Game game) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.game = game;
    }

    //Required for Entity interface
    public void step() {
    }

    //Displays Platform on screen
    public void paint(Graphics2D g) {
        //g.setColor(new Color(170, 0, 255));
        g.setColor(new Color(170, 0, 255));
        g.fillRect(x - game.getCamX(), y, w, h);
    }

    //Returns the dimensions of the Platform as a Rectangle
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
}