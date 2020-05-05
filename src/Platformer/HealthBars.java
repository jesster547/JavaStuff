package Platformer;

import java.awt.*;

public class HealthBars implements Entity {
    private int x;          // x loc of health bar.
    private int y;          // y loc of health bar.
    private int width;      // width of health bar.
    private int height;     // height of health bar.
    private double hp;      // pixels per health point
    private double curHealth;  // actual health (in pixels)
    private int color;
    private int red;
    private Room room;
    Entity parent;

    public HealthBars(Entity en) {
        this.parent = en;
        if (parent instanceof Enemy) {
            this.room = ((Enemy)parent).room;
            this.height = 20;
            this.width = (int)this.parent.getBounds().getWidth();
        } else if (parent instanceof Assassin) {
            this.room = ((Player)en).room;
            this.x = room.getCamX() + 20;
            this.y = 20;
            this.width = 400;
            this.height = 100;
        }

    }

    public void step() {
        if (this.parent instanceof Player) { //width/totalHealth = pixels per hp
            this.hp = (double)this.width / ((Player) this.parent).getTotalHealth();//supposed to get health from the two classes and find pixels per hp.
            this.curHealth = this.hp * ((Player) this.parent).getHealth();  //shows how much health the player actually has
            this.color = (int)(((double)(((Player)this.parent).getHealth())/((Player)this.parent).getTotalHealth())*255);
            this.red = Math.abs(this.color - 255);

        } else if (this.parent instanceof Enemy) {
            this.x = this.parent.getX() - room.getCamX();
            this.y = this.parent.getY() - 100;
            this.curHealth = (int) this.hp * ((Enemy) this.parent).getHealth();   //shows how much health the enemy actually has
            this.color = (int)(((double)(((Enemy)this.parent).getHealth())/((Enemy)this.parent).getTotalHealth())*255); //used to calculate amount of green in health bar. then it is the amount of red.
            this.red = Math.abs(this.color - 255);
        }
    }

    @Override
    public boolean facingRight() {
        return false;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(new Color(0, 255, 0));
        g.fillRect(this.x, this.y, (int)this.curHealth, this.height);    // draws the health part of the health bar.
        g.setColor(Color.BLACK);
        g.drawRect(this.x, this.y, this.width, this.height);        // draws the outline of the health bar.
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
