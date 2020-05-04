package Platformer;

import java.awt.*;

public class HealthBars implements Entity {
    private int x;          // x loc of health bar.
    private int y;          // y loc of health bar.
    private int width;      // width of health bar.
    private int height;     // height of health bar.
    private double hp;      // pixels per health point
    private int curHealth;  // actual health (in pixels)
    private int color;
    private int red;
    private Room room;
    Entity e;

    public HealthBars(Entity en) {
        this.e = en;

        if (en instanceof Enemy) {
            this.room = ((Enemy)en).room;
            this.height = 20;
            this.width = 80;
        } else if (en instanceof Assassin) {
            this.room = ((Player)en).room;
            this.y = 20;
            this.width = 400;
            this.height = 100;
        }

    }

    @Override
    public void step() {
        if (this.e instanceof Player) { //width/totalHealth = pixels per hp
            this.x = room.getCamX() + 20;
            this.hp = this.width / ((Player) this.e).getTotalHealth();//supposed to get health from the two classes and find pixels per hp.
            this.curHealth = (int) this.hp * ((Player) this.e).getHealth();  //shows how much health the player actually has
            this.color = (((Player)this.e).getHealth()/((Player)this.e).getTotalHealth())*255;
            if (this.color < 150){
                this.red = (((Player)this.e).getTotalHealth()/((Player)this.e).getHealth())*10;
            }
            else{
                this.red = 0;
            }
        } else if (this.e instanceof Enemy) {
            this.x = this.e.getX();
            this.y = this.e.getY() - 100;
            this.hp = this.width / ((Enemy) e).getTotalHealth();//supposed to get health from the two classes and find pixels per hp.
            this.curHealth = (int) this.hp * ((Enemy) this.e).getHealth();   //shows how much health the enemy actually has
            this.color = (((Enemy)this.e).getHealth()/((Enemy)this.e).getTotalHealth())*255; //used to calculate amount of green in health bar. then it is the amount of red.
            if (this.color < 150){
                this.red = (((Enemy)this.e).getTotalHealth()/((Enemy)this.e).getHealth())*10;
            }
            else{
                this.red = 0;
            }
        }
    }

    @Override
    public boolean facingRight() {
        return false;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(new Color(this.red, this.color, 0));
        g.fillRect(this.x, this.y, this.curHealth, this.height);    // draws the health part of the health bar.
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
