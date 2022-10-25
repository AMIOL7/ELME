package View;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ICamera;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author andru
 */
public class CustomWheelListener implements MouseWheelListener{
    
    private ICamera cam;
    private static final float MILTIPLIER = (float)0.5;

    public CustomWheelListener() {
        super();
        this.cam = Game.world().camera();
    }
    
    

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse clicks: "+e.getWheelRotation());
        cam.setZoom(MILTIPLIER*e.getWheelRotation()+cam.getZoom(), 1);
//        cam.update();
        System.out.println("Zoom: "+cam.getZoom());
    }
    
}
