package ELME.View;

import de.gurkenlabs.litiengine.Game;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * This listeners listens for MouseWheelEvent to allow zooming feature
 *
 * @author andru
 */
public class CustomWheelListener implements MouseWheelListener{
    
    private static final float MILTIPLIER = (float)0.5;

    public CustomWheelListener() {
        super();
    }
    
    

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Game.world().camera().setZoom(MILTIPLIER*e.getWheelRotation()+Game.world().camera().getZoom(), 1);
    }
    
}
