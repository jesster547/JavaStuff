package yep;
//Imports Necessary Libraries

import java.awt.*;
import java.awt.event.KeyEvent;

public interface Entity {
    //Requires all entities to have an event that runs every frame
    void step();

    //Requires all entities to be able to be displayed on screen
    void paint(Graphics2D g);

    //Requires all entities to have a hit box
    Rectangle getBounds();

}
