package Platformer;


public class Assassin extends Player {

	public Assassin(int x, int y, int w, int h,int i) {
		super(x, y, w, h);
		int walkSpeed = 0;
		int jumpHeight = 0;
		int totalHealth = 0;
		int totalMana = 0;
		int healthPoints = 0;
		int manaPoints = 0;
		setStats(10,10,10,10)
	}

	int getHealth() {
		return 0;
	}

	void setHealth(int damage) {

	}

	int getMana() {
		return 0;
	}

	void setMana(int cost) {

	}

	void remove() {

	}

	void spawn() {

	}

	public String[] getImgSources() {
		return new String[]{"src/Platformer/Images/ASSASSINNEUTRAL.PNG", "src/Platformer/Images/ASSASSINUP.PNG", "src/Platformer/Images/ASSSASSINDOWN.png"};
	}

	public void setImgIndex(int[] nums) {
		imgIndex = nums;
	}

	public int getImgIndex() {
		if (vSpd > 0) {
			return (imgIndex[2]);
		}
		if (vSpd < 0) {
			return (imgIndex[1]);
		}
		return imgIndex[0];
	}

	public void setStats(int totHealth, int totMana, int theWalkSpeed, int theJumpHeight) {
		this.totalHealth = totHealth;
		this.totalMana = totMana;
		this.healthPoints = totHealth;
		this.manaPoints = totMana;
		this.walkSpeed = theWalkSpeed;
		this.jumpHeight = theJumpHeight;
	}

	int setWeaponIndex() {
		return 0;
	}


}
