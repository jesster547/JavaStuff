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
        if (this.healthPoints > 0) {
            this.healthPoints--;
        }
        int playerX = 0;
        int playerY = 0;
        for(Player player : room.playerList){
            if(Math.abs(player.getX()-this.getX()) < Math.abs(playerX -this.getX()) || playerX == 0){
                playerX = player.getX();
            }
        }

            // doesn't matter
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
            // Basically checks boundaries.
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
        room.hurtboxList.add(new Hurtbox(x, y, w + 10, h + 10, 10, this));
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
