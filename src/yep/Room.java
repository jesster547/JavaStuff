package yep;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Room extends JPanel {
    ArrayList<Entity> entityList; //List of all entities in the room
    private int camX = 0;         //Position of Camera
    public int width;             //Width of the room
    public int floorY;            //Height of the floor platform

    public Room(ArrayList<Entity> entities, int width, int floorY) {
        entityList = entities;
        this.width = width;
        this.floorY = floorY;

        entityList.add(new Platform(0, floorY, width, 900 - floorY)); //Adds ground
        entityList.add(new Platform(-1, 0, 1, 900));           //Adds Left Wall
        entityList.add(new Platform(width, 0, 1, 900));           // Adds Right Wall

        //Binds all entities to this room
        for (Entity i : entityList) {
            i.setRoom(this);
        }

        //Adds an event listener
        addKeyListener(new KeyListener() {

            @Override //Used to use Key Listener (Not actually needed)
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override //Allows the player to listen for events
            public void keyPressed(KeyEvent keyEvent) {
                for (Entity i : entityList) {
                    if (i instanceof Player)
                        ((Player) i).keyPressed(keyEvent);
                }
            }

            @Override //Allows the player to listen for events
            public void keyReleased(KeyEvent keyEvent) {
                for (Entity i : entityList) {
                    if (i instanceof Player)
                        ((Player) i).keyReleased(keyEvent);
                }
            }
        });
        setFocusable(true); //Allows the player to listen for events
    }

    //Steps all entities in room
    public void step() {
        for (Entity i : entityList) {
            i.step();
        }
    }

    //Displays all entities in room
    public void paint(Graphics g) {
        super.paint(g); //Clears Screen
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Smoother edges
        //Displays entities
        for (Entity i : entityList) {
            i.paint(g2d);
        }
    }

    //Adds mv to Camera position. Camera will not extend past the room borders unless the room is smaller than camera.
    public void addCamX(int mv) {
        if (camX + mv >= 0 && camX + mv <= width - 1600) {
            camX += mv;
        } else if (camX + mv > width - 1600) {
            while (camX + 1 <= width - 1600) {
                camX++;
            }
        } else {
            while (camX - 1 >= 0) {
                camX--;
            }
        }
    }

    //Allows other objects to get camera position
    public int getCamX() {
        return camX;
    }
}
