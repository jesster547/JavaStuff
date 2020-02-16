package Platformer;


public class Assassin extends Player {

	public Assassin(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.walkSpeed = 20;
		System.out.println(this.walkSpeed);
	}
}
