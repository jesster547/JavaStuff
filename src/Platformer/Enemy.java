package Platformer;

import sun.font.TrueTypeFont;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public abstract class Enemy implements Entity {
    public Enemy(int x, int y, int w, int h){
        walkSpeed = 13;
        healthPoints = 100;
        manaPoints = 100;
        hSpd = 0;
        vSpd = 0;
        jumpHeight = 0;
        maxHealth = 100;
        maxMana = 100;
        grv = 1.5;
        hAcc = 2;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        setStats(10, 100, 20, 33);//Health Points, Mana Points, Walk Speed, Jump Height

    }
    public void setRoom(Room room) {
        this.room = room;
    }

    abstract void remove();

    abstract void spawn();

    void setHealth(int damage) {

    }
    int getMana() {
        return this.manaPoints;
    }

    void setMana(int cost) {

    }
    public void step() {
    vrand = math.random()*3;// 0-2
    hrand = math.random()*3;//0-2
        if(vrand == 0){
            upState = true;
            downstate = false;
        }
        else if(vrand == 1) {
            upState = false;
            downState = true;
        }
        else { //vrand == 2
            upState = false;
            downState = false;
        }
        if(hrand == 0){
            leftState = true;
            rightState = false;
        }
        else if(hrand == 1){
            leftState = false;
            rightState = true;
        }
        else{
            leftState = false;
            rightState = false;
        }



    }
    public void goplaces(upState,downState,leftState,rightState){
        int dir = 1
        if (leftState = true){

        }
        else if (rightState = true) {

        }
        else{

        }
    }
    public void paint(Graphics2D g) {
        g.setColor(new Color(74, 204, 111, 100));
        if (canJump)
            g.setColor(new Color(74, 204, 111));
        g.fillRect(x - room.getCamX(), y, w, h);
    }
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
        return canJump;
    }

}