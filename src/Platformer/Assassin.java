package Platformer;


public class Assassin extends Player {
	private int  totalHealth, totalMana, healthPoints, manaPoints, weaponIndex;

	public Assassin(int x, int y, int w, int h, int i) {
		super(x, y, w, h, i);
		this.weaponIndex = i;
		totalHealth = 0;
		totalMana = 0;
		healthPoints = 0;
		manaPoints = 0;
		setStats(10, 100, 20, 33);//Health Points, Mana Points, Walk Speed, Jump Height
	}

	int getHealth() {
		return this.healthPoints;
	}

	void setHealth(int damage) {
		this.healthPoints -= damage;	//subtracts damage make damage negative for heals
	}

	int getMana() {
		return this.manaPoints;
	}

	void setMana(int cost) {
		this.manaPoints-= cost;			//subtracts mana used, make negative for regened mana
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

	int getWeaponIndex() {
		return this.weaponIndex;
	}


}
