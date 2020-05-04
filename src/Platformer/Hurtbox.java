package Platformer;

import java.awt.*;

public class Hurtbox implements Entity{
    protected int x, y, w, h, damage;
    protected Room room;
    public Entity parent;
    public Hurtbox(int x, int y, int w, int h, int damage, Entity parent){
        this.x = x;
        this.y= y;
        this.w = w;
        this.h = h;
        this.damage = damage;
        this.parent = parent;
        if(parent instanceof Enemy)
            this.room = ((Enemy)parent).room;
        if(parent instanceof Player)
            this.room = ((Player)parent).room;
    }

    public void step() {
        this.x = parent.getX()-5;
        this.y = parent.getY()-5;
    }

    public boolean facingRight() {
        return false;
    }

    public void paint(Graphics2D g) {
        g.setColor(new Color(94, 58, 177, 100));
        g.fillRect(x - room.getCamX(), y, w, h);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String[] getImgSources() {
        return new String[0];
    }

    public void setImgIndex(int[] i) {

    }

    public int getImgIndex() {
        return 0;
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }
}
