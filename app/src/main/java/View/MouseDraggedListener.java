/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.input.IMouse;
import de.gurkenlabs.litiengine.input.Input;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 *This event listener listens for when the user drags the mouse of the the game 
 * screen while holding right mouse button pressed. When such event occurs,
 * it pans the camera in the opposite direction, since the element on the screen
 * should move in the same direction as the mouse
 * 
 * 
 * @author andru
 */
public class MouseDraggedListener implements IMouse.MouseDraggedListener{
    private Point2D camPos;
    private Point2D prevPosition;
    private Camera cam;
    private final double speed = 2.8;
    
    public MouseDraggedListener(Camera cam) {    
        super();
        prevPosition = Input.mouse().getLocation();
        this.cam = cam;
        camPos = new Point2D.Double(0, 0);
        
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        Point2D currentPos =  Input.mouse().getLocation();
        System.out.println(prevPosition + " - " + currentPos);
        
        double dx=0;
        double dy=0;
        if (Input.mouse().isRightButtonPressed()) {
            
            if (currentPos.getX()>prevPosition.getX()) {
                dx=-1;
            } else if (currentPos.getX()<prevPosition.getX()){
                dx=1;
            } else {
                dx=0;
            }
            if (currentPos.getY()>prevPosition.getY()) {
                dy=-1;
            } else if (currentPos.getY()<prevPosition.getY()){
                dy=1;
            } else {
                dy=0;
            }
        }
        System.out.println(camPos);
        camPos = new Point2D.Double(camPos.getX() + dx*speed, camPos.getY() + dy*speed);
        System.out.println(camPos);
        cam.pan(camPos, 1);

        prevPosition = currentPos;
        
        
    }
}
