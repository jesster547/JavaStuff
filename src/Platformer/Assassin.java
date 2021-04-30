package Platformer;


import java.awt.*;

public class Assassin extends Player {
    private int maxHealth;
    private int maxMana;
    private int healthPoints;
    private int manaPoints;
    private final int weaponIndex;
    private int mvTimer,jmpTimer;
    private HealthBars HB;

    public Assassin(int x, int y, int w, int h, int i) {
        super(x, y, w, h, i);
        this.weaponIndex = i;
        maxHealth = 0;
        maxMana = 0;
        manaPoints = 0;
        setStats(100, 100, 20, 19);//Health Points, Mana Points, Walk Speed, Jump Height

    }

    void remove() {

    }
    public void paint(Graphics2D g){
        super.paint(g);
        HB.paint(g);

    }

    void spawn() {
         this.HB = new HealthBars(this);
    }
    public int getHealth() {
        return this.healthPoints;
    }

    public int getTotalHealth() {
        return this.maxHealth;
    }

    public String[] getImgSources() {
        //List of all assassin image sources. Index in this list correlates to imgIndex.
        return new String[]{"src/Platformer/Images/Assassin/NeutralStick.PNG",
                "src/Platformer/Images/Assassin/JumpStick0.PNG","src/Platformer/Images/Assassin/JumpStick1.PNG","src/Platformer/Images/Assassin/JumpStick2.PNG","src/Platformer/Images/Assassin/JumpStick3.PNG", "src/Platformer/Images/Assassin/assassinDown.png",
                "src/Platformer/Images/Assassin/RunningAnimationStick0.PNG", "src/Platformer/Images/Assassin/RunningAnimationStick1.PNG",
                "src/Platformer/Images/Assassin/RunningAnimationStick2.PNG", "src/Platformer/Images/Assassin/RunningAnimationStick3.PNG",
                "src/Platformer/Images/Assassin/RunningAnimationStick4.PNG", "src/Platformer/Images/Assassin/RunningAnimationStick5.PNG",
                "src/Platformer/Images/Assassin/assassinRunning7.PNG", "src/Platformer/Images/Assassin/assassinRunning8.PNG",
                "src/Platformer/Images/Assassin/assassinSkid.PNG", "src/Platformer/Images/Assassin/assassinSkidBack.PNG",
                "src/Platformer/Images/Assassin/assassinCrouch.PNG"};
    }

    public void setImgIndex(int[] nums) {
        imgIndex = nums;
    }

    public int getImgIndex() {
        //Checks if player has been hit
        if(iFrames>0){
            mvTimer = 0;
            return (imgIndex[13]);
        }
        //Checks if player is in the air
        if (vSpd >= 0 && !canJump) {
            return (imgIndex[4]);
        }
        if (vSpd < 0) {
            int jmpMod = jmpTimer % 20;
            jmpTimer++;
            if (jmpMod == 0)
                return imgIndex[1];
            if (jmpMod <= 5)
                return imgIndex[2];
            if (jmpMod == 10)
                return imgIndex[3];
            if (jmpMod <= 15)
                return imgIndex[4];
            return imgIndex[4];
        }

        //Checks if player is crouching and resets running animation
        if (downState) {
            mvTimer = 0;
            return imgIndex[13];
        }
        //Checks if player staying still
        if (Math.abs(hSpd) < 1 || mvTimer == 0) {
            return imgIndex[0];
        }
        //Player is on ground moving. Animation of 6 frames running at 12 frames per second- saved extra 2 spots for an 8 frame animation code will need adjusting a bit tho
        else {
            //Skidding Animation & Skidding backwards animation. Resets running animation
            if (!leftState && !rightState) {
                mvTimer = 0;
                if ((hSpd > 0 && facingRight) || (hSpd < 0 && !facingRight))
                    return imgIndex[11];
                return imgIndex[12];
            }
            //Iterates through the running animation
            int mvMod = mvTimer % 25;
            if (mvMod == 0)
                return imgIndex[11]; // Last index for running
            if (mvMod <= 5)
                return imgIndex[6]; // First index for running
            if (mvMod <= 10)
                return imgIndex[7];
            if (mvMod <= 15)
                return imgIndex[8];
            if (mvMod <= 20)
                return imgIndex[9];
            if (mvMod <= 25)
                return imgIndex[10];
            return imgIndex[11];     //Last index for running
        }
    }

    public void step() {
        super.step();                //Calls Player.step()
        this.healthPoints = this.maxHealth+super.healthPoints;
        if (hSpd != 0 && canJump)    //Checks if player is on the ground running
            mvTimer++;                //Iterates through running frames
        else
            mvTimer = 0;            //Resets animation once player stops
        this.HB.step();

    }

    public void doAttack(){
        if(this.facingRight) {
            room.hurtboxList.add(new Hurtbox(x + w, y, 70, 155, 5, 7, 4, 21, true, this));
            room.hurtboxList.add(new Hurtbox(x + w, y + 10, 110, 75, 5, 5, 2, 19, true, this));
        }
        else{
            room.hurtboxList.add(new Hurtbox(x -70, y, 70, 155, 5, 7, 4, 21, true, this));
            room.hurtboxList.add(new Hurtbox(x -110, y + 10, 110, 75, 5, 5, 2, 19, true, this));
        }
    }

    public void setStats(int totHealth, int totMana, int theWalkSpeed, int theJumpHeight) {
        this.maxHealth = totHealth;
        this.maxMana = totMana;
        this.healthPoints = totHealth;
        this.manaPoints = totMana;
        this.walkSpeed = theWalkSpeed;
        this.jumpHeight = theJumpHeight;
    }

    int getWeaponIndex() {
        return this.weaponIndex;
    }


}
