package Platformer;

import com.sun.media.jfxmediaimpl.HostUtils;

import java.awt.*;
import java.util.function.DoubleToIntFunction;

public class Slime extends Enemy{
    private int jumpTimer, jumpPos, currentPos;
    private double hSpd, hAcc, vSpd;
    private boolean facingRight;
    private HealthBars healthbar;

    public Slime(int x, int y, int w, int h){
        super(x, y, w, h);
        jumpTimer = (int)(Math.random()*255);
    }

    public void step(){
        facingRight = false;
        // Gets closest player's x position
        int playerX = 0;
        //Sets playerX to the middle of the player's hitbox
        for (Player player : room.playerList) {
            if (Math.abs(player.getX()+(player.getBounds().getWidth()/2) - (x+((double)w/2))) < Math.abs(playerX - (x+((double)w/2)) )|| playerX == 0) {
                playerX = (int)(player.getX()+(player.getBounds().getWidth()/2));
            }
        }
        //Adds temporary landing hitboxes onto slime
        if(jumpTimer == 179) {
            room.hurtboxList.add(new Hurtbox(x-50, y+h-50, 50, 50, 5, 7, 2,19, true,this));
            room.hurtboxList.add(new Hurtbox(x+w, y+h-50, 50, 50, 5, 7, 2,19,true, this));
        }
        //Removes temporary landing hitbixes
        if(jumpTimer == 160){
            for(int i = 0; i < room.hurtboxList.size(); i++){
                if(room.hurtboxList.get(i).parent == this && room.hurtboxList.get(i).isTemp()){
                    room.hurtboxList.remove(i);
                    i--;
                }
            }
        }
        //Checks if Slime can jump and is on cooldown to jump. If so, it decreases the cooldown by one frame and
        // accelerates the slime toward the player. Also handles facingRight.
        if(jumpTimer > 0 && canJump) {
            jumpTimer--;
            int dir = 0;
            if(playerX - (x+(w/2)) != 0){
                dir = Math.abs(playerX - (x+(w/2)))/(playerX - (x+(w/2)));
            }
            else
                hSpd = 0;
            hAcc = dir*.1;

            if(dir > 0)
                facingRight=true;
        }

        //Checks if slime is no longer on cooldown to jump, is on the ground, and is within range of the player. If so,
        //it resets the cooldown and sets the target position it is jumping to and the position it is jumping from. Also
        //sets vSpd to -40 to launch slime into the air
        else if(jumpTimer == 0 && canJump && Math.abs(playerX - x) < 800){
            jumpTimer = 181;
            jumpPos = playerX;
            currentPos = x;
            vSpd = -40;
        }

        //Slime is in the middle of a jump. Sets hSpd to 1/52 of total distance traveled. 1/52 as that is how many frames
        //it takes for the slime to land after setting vSpd to -40. Also handles facingRight.
        else if(jumpTimer > 0){
            int dir = 0;
            if(jumpPos-x != 0) {
                dir = Math.abs(jumpPos - x) / (jumpPos - x);
            }
            hSpd = dir*((double)Math.abs(jumpPos-currentPos))/52;
            if(dir > 0)
                facingRight=true;
        }

        //Changes speed based on acceleration
        this.vSpd  += 2.5;
        hSpd += hAcc;

        //Caps ground speed at 3 pixels/frame
        if(Math.abs(hSpd) > 1 && canJump)
            hSpd = sign(hSpd);


        // Checks boundaries & whether or not slime can jump.
        canJump = false;
        for (Entity i : room.entityList) {
            if (i instanceof Platform){
                //Checks if Platform is directly below. If so, Slime can jump. Stays true once it becomes true
                if (!canJump) {
                    canJump = i.getBounds().intersects(new Rectangle(x, y + 1, w, h));
                }
                /* Checks if slime will collide with a platform in the next step. If so, it
                 * will move the slime as close to the platform as possible without intersecting it.
                 * Then, it sets vSpd/hSpd to 0, so it will not move in the direction. */
                if (i.getBounds().intersects(new Rectangle(x + (int) hSpd, y, w, h))) {
                    while (!i.getBounds().intersects(new Rectangle(x + (int) sign(hSpd), y, w, h))) {
                        x += sign((int) hSpd);
                    }
                    x+= -sign(hSpd);
                    hSpd = 0;
                }
                if (i.getBounds().intersects(new Rectangle(x, y + (int) vSpd, w, h))) {
                    while (!i.getBounds().intersects(new Rectangle(x, y + (int) sign(vSpd), w, h))) {
                        y += sign((int) vSpd);
                    }
                    vSpd = 0;
                }
                //Tests if slime is inside a platform, and pushes slime horizontally out of the shortest side
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

        //Adds velocity to position
        this.x += this.hSpd;
        this.y += this.vSpd;

        healthbar.step();
    }
    public void remove() {

    }

    private double sign(double num) {
        if (num != 0)
            return (num / Math.abs(num));
        return (0);
    }

    public void spawn() {
        healthbar = new HealthBars(this);
        room.hurtboxList.add(new Hurtbox(x, y, w, h, 7, 10, 5,1,false, this));
    }

    public boolean facingRight() {
        return facingRight;
    }

    public void paint(Graphics2D g){
        g.setColor(new Color(255, 182, 0, 255-jumpTimer));
        g.fillRect(x-room.getCamX()-10, y-10, w+20, h+20);
        healthbar.paint(g);
    }
    public String[] getImgSources() {
        return new String[]{"src/Platformer/Images/Slime/Slime_neutral.png"};
    }
}
