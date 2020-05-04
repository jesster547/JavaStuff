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

    public Enemy(int x, int y, int w, int h) {
        walkSpeed = 13;
        healthPoints = 100;
        hSpd = 0;
        vSpd = 0;
        jumpHeight = 0;
        maxHealth = 100;
        grv = 1.5;
        hAcc = 2;
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

    public void setRoom(Room room) {
        this.room = room;
    }

    abstract void remove();

    abstract void spawn();

    void setHealth(int damage) {

    }

    public void step() {
        upState = false;
        int vrand = (int) (Math.random() * 250);// 0-2
        int hrand = (int) (Math.random() * 500);//0-999

        //horizontal decisions
        if (leftState) {
            if (hrand == 0) {
                leftState = false;
                rightState = false;
            } else if (hrand == 1) {
                leftState = false;
                rightState = true;
            }
        } else if (rightState) {

            if (hrand == 0) {
                rightState = false;
            } else if (hrand == 1) {
                leftState = true;
                rightState = false;
            }
        } else {
            if (hrand == 0) {
                leftState = true;
                rightState = false;
            } else if (hrand == 1) {
                rightState = true;
            }
        }
        //vertical decisions
        if (vrand == 0) {
            upState = true;
        }
        direction(leftState, rightState);
        vSpd += grv;
        canJump = false;
        for (Entity i : room.entityList) {
            if (i instanceof Platform) {
                //Checks if Platform is directly below. If so, player can jump. Stays true once it becomes
                if (!canJump) {
                    canJump = i.getBounds().intersects(new Rectangle(x, y + 1, w, h));
                }
                if (canJump && upState) {
                    vSpd = jumpHeight * -1; //Sends player upward (Jump)
                    upState = false;

                }
                /* Checks if player will collide with a platform in the next step. If so, it
                 * will move the player as close to the platform as possible without intersecting it.
                 * Then, it sets vSpd/hSpd to 0, so it will not move in the direction. */
                if (i.getBounds().intersects(new Rectangle(x + (int) hSpd, y, w, h))) {
                    while (!i.getBounds().intersects(new Rectangle(x + (int) sign(hSpd), y, w, h))) {
                        x += sign((int) hSpd);
                    }
                    hSpd = 0;
                }
                if (i.getBounds().intersects(new Rectangle(x, y + (int) vSpd, w, h))) {
                    while (!i.getBounds().intersects(new Rectangle(x, y + (int) sign(vSpd), w, h))) {
                        y += sign((int) vSpd);
                    }
                    vSpd = 0;
                }
                //Tests if player is inside a platform, and pushes player horizontally out of the shortest side
                if (i.getBounds().intersects(new Rectangle(x, y, w, h))) {
                    int distRight = Math.abs((int) (i.getBounds().getX() - (x + w)));
                    int distLeft = Math.abs((int) (x - (i.getBounds().getX() + i.getBounds().getWidth())));
                    if (distRight < distLeft) {
                        x = (int) (i.getBounds().getX() - w);
                    } else
                        x = (int) (i.getBounds().getX() + i.getBounds().getWidth());
                }
            }
        }
        this.x += hSpd;
        this.y += vSpd;
    }

    private double sign(double num) {
        if (num != 0)
            return (num / Math.abs(num));
        return (0);
    }

    public double direction(boolean leftState, boolean rightState) {
        int dir = 0;
        if (leftState) {
            dir += -1;
        } else if (rightState) {
            dir += 1;
        }
        return sideMovement(dir);
    }

    public double sideMovement(int dir) {
        if (Math.abs(hSpd) < walkSpeed) {
            hSpd += hAcc * dir;
        }
        return hSpd;
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
        return false;
    }
    int getHealth(){
        return this.healthPoints;
    }
    int getTotalHealth(){
        return this.maxHealth;
    }
}