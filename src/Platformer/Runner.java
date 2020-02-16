package Platformer;

import javax.swing.JFrame;
import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        //Creates Window
        JFrame frame = new JFrame("Platformer");
        //Creates list of entities to be placed into room
        ArrayList<Entity> entities = new ArrayList<>();
        //Adds new entities to list (Floor and boundaries are automatically added to room
        entities.add(new Platform(300, 400, 150, 100));
        entities.add(new Assassin(200, 200, 150, 200, 1));
        //Creates new room, sets width of room and heigth of floor. Also passes list of entities into the room
        Room testingRoom = new Room(entities, 4021, 766);
        //Puts the room onto the screen
        frame.add(testingRoom);
        //Sets attributes to the window (Size, visibility, close operation, resizability)
        frame.setSize(1600, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //Core game loop; Steps game and refreshes the look. Then waits 16 ms to achieve 60 fps.
        while (true) {
            testingRoom.step();
            testingRoom.repaint();
            Thread.sleep(16);
        }
    }
}