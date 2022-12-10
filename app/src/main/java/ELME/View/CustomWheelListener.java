package ELME.View;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ICamera;
import de.gurkenlabs.litiengine.input.IMouse;
import de.gurkenlabs.litiengine.input.Input;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

/**
 * This listeners listens for MouseWheelEvent to allow zooming feature
 *
 * @author andru
 */
public class CustomWheelListener implements MouseWheelListener {

    private static final float MILTIPLIER = (float) 0.1;
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 10f;

    private Point2D oldMouseScreenPos;
    private Point2D oldMouseMapPos;

    public CustomWheelListener() {
        super();
        this.oldMouseMapPos = new Point2D.Double(0, 0);
        this.oldMouseScreenPos = new Point2D.Double(0, 0);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        ICamera camera = Game.world().camera();
        IMouse mouse = Input.mouse();

        Point2D mouseScreenPos = mouse.getLocation();
        if (mouseScreenPos.distanceSq(oldMouseScreenPos) == 0) {

            float zoom = Math.max(Math.min(MILTIPLIER * e.getWheelRotation() + camera.getZoom(), MAX_ZOOM), MIN_ZOOM);
            camera.setZoom(zoom, 1);

            Point2D mouseMapPos = mouse.getMapLocation();
            Point2D diff = new Point2D.Double(this.oldMouseMapPos.getX() - mouseMapPos.getX(), this.oldMouseMapPos.getY() - mouseMapPos.getY());
            Point2D focus = camera.getFocus();
            camera.setFocus(focus.getX() + diff.getX(), focus.getY() + diff.getY());

        } else {
            this.oldMouseMapPos = mouse.getMapLocation();
        }
        this.oldMouseScreenPos = mouseScreenPos;

    }

}
