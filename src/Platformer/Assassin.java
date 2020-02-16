package Platformer;

public class Assassin extends Player{
	//Attributes
	int walkSpeed = 0;
	int jumpHeight = 0;
	int totalHealth = 0;
	int totalMana = 0;
	
	//Constructor
	public Assassin(int x, int y, int w, int h){
		super(x,y,w,h);	//Calls the Player constructor
		
	}
	//methods
	@Override
	int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void setHealth(int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int getMana() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void setMana(int cost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void spawn() {
		// TODO Auto-generated method stub
		
	}
	@Override
	void setStats(int totHealth, int totMana, int theWalkSpeed, int theJumpHeight) {
		this.totalHealth = totHealth;
		this.totalMana = totMana;
		this.walkSpeed = theWalkSpeed;
		this.jumpHeight = theJumpHeight;
	}
	@Override
	int setWeaponIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
