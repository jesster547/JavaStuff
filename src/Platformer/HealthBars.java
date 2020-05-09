package Platformer;

import java.awt.*;

public class HealthBars implements Entity {
    private int x;          // x loc of health bar.
    private int y;          // y loc of health bar.
    private int width;      // width of health bar.
    private int height;     // height of health bar.
    private double curHealth;  // actual health (in pixels)
    private float color;
    private int red;
    private Room room;
    Entity parent;

    public HealthBars(Entity en) {
        parent = en;
        if (parent instanceof Enemy) {
            room = ((Enemy)parent).room;
            height = 20;
            width = (int)parent.getBounds().getWidth();
        } else if (parent instanceof Assassin) {
            room = ((Player)en).room;
            x = room.getCamX() + 20;
            y = 20;
            width = 400;
            height = 100;
        }

    }

    public void step() {
        // pixels per health point
        double hp;
        if (parent instanceof Player) {
            //Calculates pixels/hp
            hp = (double)width / ((Player) parent).getTotalHealth();
            //Calculates the number of pixels to accurately display health
            curHealth = hp * ((Player) parent).getHealth();
            //Sets color hue based on the percentage of hp the player has
            color = (float) ((1.0/3)*((Player)parent).getHealth()/((Player)parent).getTotalHealth());
        } else if (parent instanceof Enemy) {
            //Sets coordinates to display above the enemy
            x = parent.getX() - room.getCamX();
            y = parent.getY() - 100;
            //Calculates pixels/hp
            hp = (double)width / ((Enemy) parent).getTotalHealth();
            //Calculates the number of pixels to accurately display health
            curHealth = (int) hp * ((Enemy) parent).getHealth();
            //Sets color hue based on the percentage of hp the player has
            color = (float) ((1.0/3)*((Enemy)parent).getHealth()/((Enemy)parent).getTotalHealth());
        }
    }

    @Override
    public boolean facingRight() {
        return false;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.getHSBColor(color, 1, 1));
        g.fillRect(x, y, (int)curHealth, height);    // draws the remaining HP.
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);        // draws the outline of the health bar.
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String[] getImgSources() {
        return new String[0];
    }

    @Override
    public void setImgIndex(int[] i) {

    }

    @Override
    public int getImgIndex() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
