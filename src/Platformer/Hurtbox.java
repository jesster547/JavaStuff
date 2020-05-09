package Platformer;

import java.awt.*;

public class Hurtbox implements Entity{
    protected int x, y, w, h, damage, hKnockback, vKnockback, xOffset, yOffset;
    protected Room room;
    public Entity parent;
    private final Boolean temp;
    public Hurtbox(int x, int y, int w, int h, int damage, int hKnockback, int vKnockback, boolean temp, Entity parent){
        this.x = x;
        this.y= y;
        this.w = w;
        this.h = h;
        this.xOffset = x-parent.getX();
        this.yOffset = y-parent.getY();
        this.damage = damage;
        this.hKnockback = hKnockback;
        this.vKnockback = vKnockback;
        this.temp = temp;
        this.parent = parent;
        if(parent instanceof Enemy)
            this.room = ((Enemy)parent).room;
        if(parent instanceof Player)
            this.room = ((Player)parent).room;
    }

    public void step() {
        this.x = parent.getX()+xOffset;
        this.y = parent.getY()+yOffset;
    }

    public boolean facingRight() {
        return false;
    }

    public void paint(Graphics2D g) {
        g.setColor(new Color(90, 33, 227, 100));
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

    public boolean isTemp(){
        return temp;
    }
}
