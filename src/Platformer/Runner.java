package Platformer;

import javax.swing.JFrame;
import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Platformer");
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Platform(300, 300, 150, 200));
        entities.add(new Assassin(200, 200, 150, 200,1));
        Room testingRoom = new Room(entities, 4021, 766);
        frame.add(testingRoom);
        frame.setSize(1600, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        while (true) {
            testingRoom.step();
            testingRoom.repaint();
            Thread.sleep(16);
        }
    }
}