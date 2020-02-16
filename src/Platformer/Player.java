package Platformer;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Player implements Entity {
    /* Variables
     * x & y - Position of Player
     * w & h - Dimensions of Player (Hit box)
     * walkSpeed - Horizontal Distance Player Travels each step
     * weapIndex - Which weapon player uses 0, 1, or 2
     * grv - Gravity
     * vSpd & hSpd - Speed player is travelling (Vertical and Horizontal)
     * upState, rightState, & leftState - Detects of keys that are being pressed
     * room - The room the player is in
     * imgIndex - The indexes the room uses to display images */
    protected int x, y, w, h, walkSpeed, weapIndex = 0;
    protected double grv, vSpd, hSpd;
    private boolean upState = false, rightState = false, leftState = false;
    private Room room;
    int[] imgIndex;

    //Sets Variables
    public Player(int x, int y, int w, int h) {
        //walkSpeed = 13;
        hSpd = 0;
        vSpd = 0;
        grv = 1.5;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    abstract int getHealth();

    abstract void setHealth(int damage);

    abstract int getMana();

    abstract void setMana(int cost);

    abstract void remove();
    
    abstract void spawn();

    //Handles Collision and Movement of Player
    public void step() {
        int rightInt;    //Used to determine Direction player is moving
        int leftInt;    //Used to determine Direction player is moving
        if (rightState)
            rightInt = 1;
        else
            rightInt = 0;
        if (leftState)
            leftInt = 1;
        else
            leftInt = 0;
        int move = rightInt - leftInt; //-1 = left, 0 = still, 1 = right

        hSpd = move * walkSpeed; //Determines direction and speed of movement
        vSpd += grv;             //Changes vSpd for gravity

        //Checks all entities for Platforms
        for (Entity i : room.entityList) {
            if (i instanceof Platform) {
                //Checks if Platform is directly below. If so, player can jump.
                if ((i.getBounds().intersects(new Rectangle(x, y + 1, w, h))) && upState) {
                    vSpd = -27; //Sends player upward (Jump)
                }
                /* Checks if player will collide with a platform in the next step. If so, it
                 * will move the player as close to the platform as possible without intersecting it.
                 * Then, it sets vSpd/hSpd to 0, so it will not move in the direction. */
                if (i.getBounds().intersects(new Rectangle(x + (int) hSpd, y, w, h))) {
                    while (!i.getBounds().intersects(new Rectangle(x + sign((int) hSpd), y, w, h))) {
                        x += sign((int) hSpd);
                    }
                    hSpd = 0;
                }
                if (i.getBounds().intersects(new Rectangle(x, y + (int) vSpd, w, h))) {
                    while (!i.getBounds().intersects(new Rectangle(x, y + sign((int) vSpd), w, h))) {
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

        //Manages Camera
        //Moves Camera with player if possible
        if (x - room.getCamX() > 1120 && room.getCamX() <= room.width && hSpd > 0) {
            room.addCamX((int) hSpd);

        }
        if (x - room.getCamX() < 400 && room.getCamX() >= 0 && hSpd < 0) {
            room.addCamX((int) hSpd);
        }

        //Changes player's position
        x += hSpd;
        y += vSpd;

    }

    //Displays player (Placeholder until we add a real sprite)
    public void paint(Graphics2D g) {
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
    }

    //Returns 1, -1, or 0 depending on the sign of the input
    private int sign(int num) {
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
}