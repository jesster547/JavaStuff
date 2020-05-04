package Platformer;


import sun.awt.windows.WPrinterJob;

import java.awt.*;

public class Bot extends Enemy {
    private HealthBars HB;
    public Bot(int x, int y, int w, int h) {
        super(x, y, w, h);
        setStats(100, 20, 30);

    }

    public void step() {
        this.HB.step();
        super.step();
        healthPoints -= 1;
    }

    public boolean facingRight() {
        return true;
    }

    public void paint(Graphics2D g) {
        HB.paint(g);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    void remove() {
    this.HB.step();
        System.out.println("spawning health bar.");
    }

    void spawn() {
      room.hurtboxList.add(new Hurtbox(x, y, w+10, h+10, 10, this));
      this.HB = new HealthBars(this);
    }

    public String[] getImgSources() {
        return new String[]{"src/Platformer/Images/stockImage.png"};
    }

    public void setImgIndex(int[] i) {
        imgIndex = i;
    }

    public int getImgIndex() {
        return imgIndex[0];
    }

    public void setStats(int totHealth, int theWalkSpeed, int theJumpHeight) {
        this.maxHealth = totHealth;
        this.healthPoints = totHealth;
        this.walkSpeed = theWalkSpeed;
        this.jumpHeight = theJumpHeight;
    }
}
