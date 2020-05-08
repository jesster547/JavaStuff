package Platformer;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Room extends JPanel {
    ArrayList<Entity> entityList;       //List of all entities in the room
    ArrayList<Hurtbox> hurtboxList;     //List of all hurt-boxes in the room (Hurt-boxes do not exist in entityList)
    ArrayList<Player> playerList;       //List of all players in the room (Players are also in entityList)
    private int camX = 0;               //Position of Camera
    public int width;                   //Width of the room
    public int floorY;                  //Height of the floor platform
    private final ArrayList<Image> imgList;   //List of all entity images
    boolean  newKey;


    public Room(ArrayList<Entity> entities, int width, int floorY, boolean newKey) {
        this.setBackground(Color.BLUE);
        entityList = entities;
        this.width = width;
        this.floorY = floorY;
        imgList = new ArrayList<>();
        hurtboxList = new ArrayList<>();
        playerList = new ArrayList<>();
        this.newKey = newKey;

        entityList.add(new Platform(0, floorY, width, 900 - floorY,false)); //Adds ground
        entityList.add(new Platform(-1, 0, 1, 900,false));           //Adds Left Wall
        entityList.add(new Platform(width, 0, 1, 900,false));           // Adds Right Wall
        entityList.add(new Platform(2400, 500, 400, 100,false));
        entityList.add(new Platform(1200, 444, 400, 50,true));
        /*
        entityList.add(new Platform(1200, 388, 400, 50,true));
        entityList.add(new Platform(1200, 332, 400, 50,true));
        entityList.add(new Platform(1200, 276, 400, 50,true));
        entityList.add(new Platform(1200, 220, 400, 50,true));
        entityList.add(new Platform(1200, 164, 400, 50,true));
         */
        //hurtboxList.add(new Hurtbox(0, 0, width, 10, 10, 0, -100, new Bot(0, 0, 1, 1, this)));

        //Binds all entities to this room and generates list of images. Also runs 'spawn' event when loading the room
        for (Entity i : entityList) {
            i.setRoom(this);
            if (i instanceof Player) {
                ((Player) i).spawn();
                playerList.add((Player) i);
            }
            if (i instanceof Enemy)
                ((Enemy) i).spawn();
            //Temp Var to get image sources from entities
            String[] tmpImgs;
            tmpImgs = i.getImgSources();
            //Loops through the sources given and puts them in the list
            for (String o : tmpImgs) {
                //Local Vars to handle images
                ImageIcon imgtmp;
                Image tmp;
                //Sets the image to the given source, and scales it to the entity
                imgtmp = new ImageIcon(o);
                tmp = imgtmp.getImage().getScaledInstance((int) i.getBounds().getWidth(), (int) i.getBounds().getHeight(), Image.SCALE_SMOOTH);
                imgtmp = new ImageIcon(tmp, imgtmp.getDescription());
                tmp = imgtmp.getImage();
                //Adds the scaled image to imgList
                imgList.add(tmp);
            }
            //Sets temp variables
            int[] indexes = new int[tmpImgs.length];
            int total = imgList.size() - indexes.length;
            //Gives entities indexes to display images
            for (int p = total; p < imgList.size(); p++) {
                indexes[p - total] = p;
            }
            i.setImgIndex(indexes);
        }
    }

    //Steps all entities in room
    public void step() {
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).step();                                    //Steps Every entity in room
            if (entityList.get(i) instanceof Player) {                   //Checks if entity is a player
                if (((Player) entityList.get(i)).getHealth() <= 0) {     //If player health is < 0, player object is
                    entityList.remove(i);                                //deleted
                    i--;
                }
            }
        }
        for(Hurtbox e: hurtboxList){
            e.step();
        }
    }

    //Displays all entities in room
    public void paint(Graphics g) {
        super.paint(g); //Clears Screen
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Smoother edges
        //Displays entities and images
        for (Entity i : entityList) {
            i.paint(g2d);
            //Checks if entity wants to be flipped, if so it is flipped
            if (i.facingRight()) {
                g.drawImage(imgList.get(i.getImgIndex()), i.getX() - camX, i.getY(), (int) i.getBounds().getWidth(), (int) i.getBounds().getHeight(), null);
            } else {
                    g.drawImage(imgList.get(i.getImgIndex()), (int) (i.getX() - camX + i.getBounds().getWidth()), i.getY(), -(int) i.getBounds().getWidth(), (int) i.getBounds().getHeight(), null);
            }
        }
        for (Hurtbox e: hurtboxList){
            e.paint(g2d);
        }
    }

    //Adds mv to Camera position. Camera will not extend past the room borders unless the room is smaller than camera.
    public void addCamX(double mv) {
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