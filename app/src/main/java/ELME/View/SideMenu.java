package ELME.View;

import ELME.Model.Node;
import ELME.Model.Nodes.*;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author pszi
 */
public class SideMenu extends ExtraMenu {

    public static final Color SIDE_FOREGROUND = new Color(100,180,120, 175);
    public static final Color SIDE_BACKGROUND = new Color(100,100,120, 175);
    private ImageComponent currentComp;
    private boolean hasItBeenDragged;
    private String lastClickedText = "";
    private int currentNodeIndex;
    public SideMenu(double x, double y, double width, double height, int rows, int columns, String... items) {
        super(x, y, width, height, rows, columns, items);

        Input.mouse().onClicked(e -> {
            currentNodeIndex = -1;
            if (e.getButton() == MouseEvent.BUTTON1)
                for (ImageComponent comp : getCellComponents()) {
                    if (comp.getBoundingBox().contains(e.getPoint()))
                        System.out.println(comp.getText() + " clicked");
            }
        });

        Input.mouse().onPressed(e -> {
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (ImageComponent comp : getCellComponents()) {
                    if (comp.getBoundingBox().contains(e.getPoint())) {
                        currentComp = comp;
                        hasItBeenDragged = false;
                        System.out.println(comp.getText() + " pressed");
                    }
                }
            }
        });

        Input.mouse().onDragged(e -> {
            if (Input.mouse().isLeftButtonPressed() && currentComp != null) {
                MainScreen temp = (MainScreen) Game.screens().current();
                Node typeIndicator = null;
                if (!hasItBeenDragged)
                {
                    switch (currentComp.getText()) {
                        case "NOT": typeIndicator = new NOTNode(); break;
                        case "AND": typeIndicator = new ANDNode(); break;
                        case "OR": typeIndicator = new ORNode(); break;
                        case "XOR": typeIndicator = new XORNode(); break;
                        case "ODD": typeIndicator = new ODDNode(); break;
                        default: return;
                    }
                    currentNodeIndex = temp.graphVisuals.graph.getNodes().size();
                    temp.graphVisuals.InsertNode(typeIndicator,
                            new Rectangle2D.Double(Input.mouse().getMapLocation().getX(), Input.mouse().getMapLocation().getY(), 50, 50));
                    hasItBeenDragged = true;
                } else
                    temp.graphVisuals.MoveNode(currentNodeIndex,
                            new Point2D.Double(Input.mouse().getMapLocation().getX(), Input.mouse().getMapLocation().getY()));
            }
        });

        Input.mouse().onReleased(e -> {
            if (e.getButton() == MouseEvent.BUTTON1 && currentComp != null) {
                System.out.println(currentComp.getText() + " released");
                currentComp = null;
            }
        });
    }

    @Override
    public void prepare()
    {
        super.prepare();
        this.getCellComponents().forEach(comp -> {
            comp.setFontSize(16);
            comp.getAppearance().setForeColor(SIDE_FOREGROUND);
            comp.getAppearance().setBackgroundColor1(SIDE_BACKGROUND);
            comp.getAppearance().setTransparentBackground(false);
            comp.getAppearanceHovered().setTransparentBackground(false);
        });
    }
}
