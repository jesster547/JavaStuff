package Platformer;

import com.sun.org.apache.xerces.internal.xs.XSTerm;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.temporal.Temporal;
import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        //Creates Window
        JFrame frame = new JFrame("Platformer");
        //Creates list of entities to be placed into room
        ArrayList<Entity> entities = new ArrayList<>();
        ArrayList<Entity> entitiesTwo = new ArrayList<>();
        //Adds new entities to list (Floor and boundaries are automatically added to room
        entities.add(new Background(4021, 770, "src/Platformer/Images/backGround_1.png"));
        entities.add(new Platform(300, 400, 150, 100, false));
        entities.add(new Assassin(200, 200, 150, 200, 1));
        //entities.add(new Bot(1000,200,100,299));
        entitiesTwo.add(new Background(4021, 770, "src/Platformer/Images/windowBackground.png"));
        entitiesTwo.add(new Platform(600, 400, 150, 90, false));
        entitiesTwo.add(new Assassin(200, 200, 150, 200, 1));
        //Creates new room, sets width of room and height of floor. Also passes list of entities into the room
        Room testingRoom = new Room(entities, 4021, 766, true);
        Room testTwo = new Room(entitiesTwo, 4021, 766, true);
        //Puts the room onto the screen
        frame.add(testingRoom);
        //Sets attributes to the window (Size, visibility, close operation, resizability)
        frame.setSize(1600, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        boolean roomTwo = false;

        //Adds an event listener
        frame.addKeyListener(new KeyListener() {

            @Override //Used to use Key Listener (Not actually needed)
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override //Allows the player to listen for events
            public void keyPressed(KeyEvent keyEvent) {
                for (Entity i : testingRoom.entityList) {
                    if (i instanceof Player)
                        ((Player) i).keyPressed(keyEvent);
                }
                for (Entity i : testTwo.entityList) {
                    if (i instanceof Player)
                        ((Player) i).keyPressed(keyEvent);
                }
            }

            @Override //Allows the player to listen for events
            public void keyReleased(KeyEvent keyEvent) {
                for (Entity i : testingRoom.entityList) {
                    if (i instanceof Player)
                        ((Player) i).keyReleased(keyEvent);
                }
                for (Entity i : testTwo.entityList) {
                    if (i instanceof Player)
                        ((Player) i).keyReleased(keyEvent);
                }
            }
        });
        frame.setFocusable(true); //Allows the player to listen for events

        //Core game loop; Steps game and refreshes the look. Then waits 16 ms to achieve 60 fps.
        //Added roomTwo and testTwo in order to test multiple rooms
        while (true) {
            if (testingRoom.entityList.size() >= 8) {
                testingRoom.step();
                testingRoom.repaint();
            } else {
                if (!roomTwo) {
                    frame.getContentPane().remove(0);
                    frame.getContentPane().add(testTwo);
                    roomTwo = true;
                    frame.revalidate();
                }
                testTwo.step();
                testTwo.repaint();
            }
            Thread.sleep(16);
        }
    }
}