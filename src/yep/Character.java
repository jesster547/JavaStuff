package yep;

public interface Character {
	// add a weapon index
	int getHealth();
	void setHealth(int damage);
	int getMana();
	void setMana(int cost);
	void remove();
	void spawn();
	void step();

}