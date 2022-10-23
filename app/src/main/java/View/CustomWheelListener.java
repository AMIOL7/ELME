/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import de.gurkenlabs.litiengine.graphics.Camera;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author andru
 */
public class CustomWheelListener implements MouseWheelListener{
    
    private Camera cam;
    private static final float MILTIPLIER = (float)0.5;

    public CustomWheelListener(Camera cam) {
        super();
        this.cam = cam;
    }
    
    

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse clicks: "+e.getWheelRotation());
        cam.setZoom(MILTIPLIER*e.getWheelRotation()+cam.getZoom(), 1);
//        cam.update();
        System.out.println("Zoom: "+cam.getZoom());
    }
    
}
