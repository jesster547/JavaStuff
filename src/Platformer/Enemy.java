package Platformer;

import sun.font.TrueTypeFont;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public abstract class Enemy implements Entity { //Enemy-- does not have mana
    protected int x, y, w, h, walkSpeed, healthPoints, maxHealth;
    protected double grv, vSpd, hSpd, jumpHeight, hAcc;
    protected boolean leftState, rightState, upState, canJump;
    public Room room;
    protected int[] imgIndex;
    protected HealthBars HB;

    public Enemy(int x, int y, int w, int h) {
        walkSpeed = 13;
        healthPoints = 100;
        hSpd = 0;
        vSpd = 0;
        jumpHeight = 0;
        maxHealth = 100;
        grv = 1.5;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        boolean leftState = false;
        boolean rightState = false;
        boolean canJump = false;
        boolean upState = false;
        boolean facingRight = false;
    }

    abstract void remove();

    abstract void spawn();

    public void step() {
        this.HB.step();
        if (this.healthPoints <= 0) {
            this.remove();
        }
        for (Hurtbox i : room.hurtboxList){
            if(i.parent instanceof Player){
                if(i.getBounds().intersects(new Rectangle(x, y, w, h))){
                    healthPoints--;
                }
            }
        }
    }

    public void paint(Graphics2D g) {
        g.setColor(new Color(74, 204, 111, 100));
        if (canJump)
            g.setColor(new Color(74, 204, 111));
        g.fillRect(x - room.getCamX(), y, w, h);
    }

    //---Getters and Setters---

    //Returns the enemy's hit box
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    //Returns X position
    public int getX() {
        return x;
    }

    //Returns Y Position
    public int getY() {
        return y;
    }
    //Sets the room
    public void setRoom(Room room) {
        this.room = room;
    }

    //Returns All Image Sources in Order
    public String[] getImgSources() {
        return new String[]{"src/Platformer/Images/stockImage.png"};
    }

    //Sets the list of indexes to display images
    public void setImgIndex(int[] nums) {
        imgIndex = nums;
    }

    //Contains logic for which image is to be displayed at a point in time
    public int getImgIndex() {
        return (imgIndex[0]);
    }

    //Returns whether or not a player can jump
    public boolean canJump() {
        return false;
    }
    int getHealth(){
        return this.healthPoints;
    }
    int getTotalHealth(){
        return this.maxHealth;
    }
    void setHealth(int damage) { this.healthPoints -= damage;}
}