package Platformer;
//Imports Necessary Libraries
import java.awt.*;

public interface Entity {
    //Requires all entities to have an event that runs every frame
    void step();

    //Requires all entities to be able to be displayed on screen
    void paint(Graphics2D g);

    //Requires all entities to have a hit box
    Rectangle getBounds();

    //Requires all entities to be able to be in a room
    void setRoom(Room room);

    //Requires all entities to provide image sources
    String[] getImgSources();

    //Requires all entities to be able to obtain indexes for images
    void setImgIndex(int[] i);

    //Requires all entities to provide remapped indexes for pictures
    int getImgIndex();

    //Requires all entities to be able to report their position
    int getX();

    int getY();
}