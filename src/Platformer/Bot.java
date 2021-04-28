package Platformer;



import java.awt.*;

public class Bot extends Enemy {
    boolean targeting;
    boolean notTargeting;
    int hAcc;

    public Bot(int x, int y, int w, int h) {
        super(x, y, w, h);
        hAcc = 2;
        setStats(100, 10, 30);

    }

    public Bot(int x, int y, int w, int h, Room room) {
        super(x, y, w, h);
        this.room = room;
        hAcc = 2;
        setStats(100, 10, 30);

    }

    public void step() {
        super.step();
        if (this.healthPoints > 0) {
            this.healthPoints--;
        }
        int playerX = 0;
        int playerY = 0;
        for (Player player : room.playerList) {
            if (Math.abs(player.getX()+(player.getBounds().getWidth()/2) - (x+((double)w/2))) < Math.abs(playerX - (x+((double)w/2)) )|| playerX == 0) {
                playerX = (int)(player.getX()+(player.getBounds().getWidth()/2));
            }
        }
        if(playerX-(x+((double)w/2))>0){
            this.hSpd+= hAcc;
            if (this.hSpd > walkSpeed){
                this.hSpd = walkSpeed;
            }
        }
        else if(playerX-(x+((double)w/2))<0){
            this.hSpd -= hAcc;
            if (this.hSpd < walkSpeed){
                this.hSpd = walkSpeed*-1;
            }
        }
        else{
            this.hSpd = 0;
        }
        this.vSpd  += grv;
        // Basically checks boundaries.
        for (Entity i : room.entityList) {
            if (i instanceof Platform){
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
        this.x +=this.hSpd;
        this.y+=this.vSpd;
    }

    private double sign(double num) {
        if (num != 0)
            return (num / Math.abs(num));
        return (0);
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
    }

    void spawn() {
        room.hurtboxList.add(new Hurtbox(x, y, w + 10, h + 10, 10, 30, 15, 1,false, this));
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
