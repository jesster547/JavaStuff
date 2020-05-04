package Platformer;

import java.awt.*;

public class HealthBars implements Entity{
    private int x;
    private int y;
    private int width;
    private int height;
    private double hp;
    private int curHealth;
    Room room;
    Entity e;
    public HealthBars(Entity en){
        this.e = en;
        if(en instanceof Enemy){
            this.x = en.getX();
            this.y = en.getY() -100;
            this.height = 20;
            this.width = 80;
        }
        else if (en instanceof Assassin){
            this.x = room.getCamX() + 20;
            this.y = 20;
            this.width = 400;
            this.height = 100;
        }

    }
    @Override
    public void step() {
    if (e instanceof Player){ //width/totalHealth = pixels per hp
        this.hp = this.width/ ((Player)e).getTotalHealth();//supposed to get health from the two classes and find pixels per hp.
        this.curHealth =(int) this.hp*((Player)e).getHealth();
    }
    else if(e instanceof Enemy){
        this.hp = this.width/ ((Enemy)e).getTotalHealth();//supposed to get health from the two classes and find pixels per hp.
        this.curHealth =(int) this.hp*((Enemy)e).getHealth();
    }
    }

    @Override
    public boolean facingRight() {
        return false;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(new Color(0, 224, 0));
        g.fillRect(this.x, this.y, this.curHealth, this.height);
        g.setColor(Color.BLACK);
        g.drawRect(this.x,this.y,this.width,this.height);
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
