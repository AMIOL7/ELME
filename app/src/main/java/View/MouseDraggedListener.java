/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.input.IMouse;
import de.gurkenlabs.litiengine.input.Input;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
public class MouseDraggedListener implements IMouse.MouseDraggedListener {

    private Point2D camPos;
    private Point2D oldPos;
    private Camera cam;
    private boolean dragging;

    public MouseDraggedListener(Camera cam) {
        super();
        oldPos = Input.mouse().getLocation();
        this.cam = cam;
        camPos = new Point2D.Double(0, 0);
        dragging = false;

        Input.mouse().addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Input.mouse().isRightButton(e)) {
                    dragging = true;
                    oldPos = Input.mouse().getLocation();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Input.mouse().isRightButton(e)) {
                    dragging = false;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (!dragging) {
            return;
        }

        Point2D newPos = Input.mouse().getLocation();
        Point2D deltaPos = new Point2D.Double(oldPos.getX() - newPos.getX(), oldPos.getY() - newPos.getY());
        
        double scale = 1.0 / cam.getRenderScale();
        camPos = new Point2D.Double(camPos.getX() + deltaPos.getX() * scale, camPos.getY() + deltaPos.getY() * scale);
        cam.pan(camPos, 1);
        
        oldPos = newPos;
    }
}
