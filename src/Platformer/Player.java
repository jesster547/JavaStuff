package Platformer;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Player implements Entity {
    /* Variables
     * x & y - Position of Player
     * w & h - Dimensions of Player (Hit box)
     * walkSpeed - Horizontal Distance Player Travels each step
     * weapIndex - Which weapon player uses 0, 1, or 2
     * grv & hAcc - Gravity (Vertical Acceleration) and horizontal acceleration
     * vSpd & hSpd - Speed player is travelling (Vertical and Horizontal)
     * upState, downState, rightState, & leftState - Detects of keys that are being pressed
     * room - The room the player is in
     * imgIndex - The indexes the room uses to display images
     * canJump - True if player can jump. If player is in the air, it is false */
    protected int x, y, w, h, walkSpeed, weapIndex, healthPoints, manaPoints, maxHealth, maxMana;
    protected double grv, vSpd, hSpd, jumpHeight, hAcc;
    protected boolean upState = false, rightState = false, leftState = false, downState = false, facingRight = true,
            canJump = false;
    protected Room room;
    protected int[] imgIndex;

    //Sets Variables
    public Player(int x, int y, int w, int h, int i) {
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
        this.weapIndex = i;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    abstract void remove();

    abstract void spawn();

    public int getWeapIndex() {
        return weapIndex;
    }

    public void setWeapIndex(int weap) {
        weapIndex = weap;
    }

    int getHealth() {
        return this.healthPoints;
    }

    public boolean facingRight() {
        return facingRight;
    }

    void setHealth(int damage) {
        this.healthPoints -= damage;    //subtracts damage make damage negative for heals
        if (this.healthPoints > this.maxHealth) {
            this.healthPoints = this.maxHealth;
        }
    }

    int getMana() {
        return this.manaPoints;
    }

    void setMana(int cost) {
        this.manaPoints -= cost;            //subtracts mana used, make negative for regened mana
        if (this.manaPoints > this.maxMana) {
            this.manaPoints = this.maxMana;
        }
    }

    //Handles Collision and Movement of Player
    public void step() {
        int rightInt = 0;    //Used to determine Direction player is moving
        int leftInt = 0;     //Used to determine Direction player is moving
        if (rightState)
            rightInt = 1;
        if (leftState)
            leftInt = 1;
        int move = rightInt - leftInt; //-1 = left, 0 = still, 1 = right

        if (move == -1) {
            facingRight = false;
        }
        if (move == 1) {
            facingRight = true;
        }

        //Implements player's acceleration on the ground. Air acceleration is 2 times ground acceleration
        //Max speed is equal to walk speed
        if (canJump)
            hAcc = move * 2;
        else
            hAcc = move;

        //Implements faster deceleration when player is crouched
        if (downState && canJump) {
            hAcc = 0;
            hSpd *= 0.85;
            if (Math.abs(hSpd) < 1)
                hSpd = 0;
        }

        //The deceleration when the player is not crouched
        else if (move == 0) {
            hSpd = 0.9 * hSpd;
            if (Math.abs(hSpd) < 1)
                hSpd = 0;
        }

        //Increases/Decreases hSpd by hAcc
        hSpd += hAcc;

        //Limits speed of player
        if (walkSpeed < Math.abs(hSpd))
            hSpd = sign(hSpd) * walkSpeed;

        //Changes vSpd for gravity
        vSpd += grv;

        //Used to determine if player can jump or is in the air
        canJump = false;
        //Checks all entities for Platforms
        for (Entity i : room.entityList) {
            if (i instanceof Platform) {
                //Checks if Platform is directly below. If so, player can jump. Stays true once it becomes true
                if (!canJump) {
                    canJump = i.getBounds().intersects(new Rectangle(x, y + 1, w, h));
                }
                if (canJump && upState) {
                    vSpd = jumpHeight * -1; //Sends player upward (Jump)
                    downState = false;
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

        if (downState && h == 200 && canJump) {
            h = 100;
            y += 100;
        } else if (!downState && h == 100) {
            h = 200;
            y -= 100;
        }


        //Manages Camera
        //Moves Camera with player if possible
        if (x - room.getCamX() > 1120 && room.getCamX() <= room.width && hSpd > 0) {
            room.addCamX(hSpd);

        }
        if (x - room.getCamX() < 400 && room.getCamX() >= 0 && hSpd < 0) {
            room.addCamX((hSpd));
        }

        //Changes player's position
        x += hSpd;
        y += vSpd;

    }

    //Displays player (Placeholder until we add a real sprite)
    public void paint(Graphics2D g) {
        g.setColor(new Color(74, 204, 111, 100));
        if (canJump)
            g.setColor(new Color(74, 204, 111));
        g.fillRect(x - room.getCamX(), y, w, h);
    }

    //Returns the Player's hit box
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    //Checks if keys are down.
    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_UP) {
            upState = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightState = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_LEFT) {
            leftState = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_DOWN) {
            downState = true;
        }
    }

    //Checks if keys get released
    public void keyReleased(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_UP) {
            upState = false;
        }
        if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightState = false;
        }
        if (k.getKeyCode() == KeyEvent.VK_LEFT) {
            leftState = false;
        }
        if (k.getKeyCode() == KeyEvent.VK_DOWN) {
            downState = false;
        }
    }

    //Returns 1, -1, or 0 depending on the sign of the input
    private double sign(double num) {
        if (num != 0)
            return (num / Math.abs(num));
        return (0);
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