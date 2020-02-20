package Platformer;


public class Assassin extends Player {
	private int maxHealth, maxMana, healthPoints, manaPoints, weaponIndex, mvTimer;

	public Assassin(int x, int y, int w, int h, int i) {
		super(x, y, w, h, i);
		this.weaponIndex = i;
		maxHealth = 0;
		maxMana = 0;
		healthPoints = 0;
		manaPoints = 0;
		setStats(10, 100, 20, 33);//Health Points, Mana Points, Walk Speed, Jump Height
	}

	void remove() {

	}

	void spawn() {

	}

	public String[] getImgSources() {
		//List of all assassin image sources. Index in this list correlates to imgIndex.
		return new String[]{"src/Platformer/Images/Assassin/assassinNeutral.PNG",
				"src/Platformer/Images/Assassin/assassinUp.PNG", "src/Platformer/Images/Assassin/assassinDown.png",
				"src/Platformer/Images/Assassin/assassinRunning1.PNG", "src/Platformer/Images/Assassin/assassinRunning2.PNG",
				"src/Platformer/Images/Assassin/assassinRunning3.PNG", "src/Platformer/Images/Assassin/assassinRunning4.PNG",
				"src/Platformer/Images/Assassin/assassinRunning5.PNG", "src/Platformer/Images/Assassin/assassinRunning6.PNG",
				"src/Platformer/Images/Assassin/assassinRunning7.PNG", "src/Platformer/Images/Assassin/assassinRunning8.PNG",
				"src/Platformer/Images/Assassin/assassinSkid.PNG", "src/Platformer/Images/Assassin/assassinSkidBack.PNG",
				"src/Platformer/Images/Assassin/assassinCrouch.PNG"};
	}

	public void setImgIndex(int[] nums) {
		imgIndex = nums;
	}

	public int getImgIndex() {
		//Checks if player is in the air
		if (vSpd >= 0 && !canJump) {
			return (imgIndex[2]);
		}
		if (vSpd < 0) {
			return (imgIndex[1]);
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
		//Player is on ground moving. Animation of 8 frames running at 12 frames per second
		else {
			//Skidding Animation & Skidding backwards animation. Resets running animation
			if (!leftState && !rightState) {
				mvTimer=0;
				if ((hSpd > 0 && facingRight) || (hSpd < 0 && !facingRight))
					return imgIndex[11];
				return imgIndex[12];
			}
			//Iterates through the running animation
			int mvMod = mvTimer % 40;
			if (mvMod == 0)
				return imgIndex[10];
			if (mvMod <= 5)
				return imgIndex[3];
			if (mvMod <= 10)
				return imgIndex[4];
			if (mvMod <= 15)
				return imgIndex[5];
			if (mvMod <= 20)
				return imgIndex[6];
			if (mvMod <= 25)
				return imgIndex[7];
			if (mvMod <= 30)
				return imgIndex[8];
			if (mvMod <= 35)
				return imgIndex[9];
			return imgIndex[10];
		}
	}

	public void step() {
		super.step();                //Calls Player.step()
		if (hSpd != 0 && canJump)    //Checks if player is on the ground running
			mvTimer++;                //Iterates through running frames
		else
			mvTimer = 0;            //Resets animation once player stops
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
