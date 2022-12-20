package ELME.View;

import ELME.Model.Node;
import ELME.Model.Nodes.*;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * @author pszi
 */
public class SideMenu extends ExtraMenu {

    public static final Color SIDE_FOREGROUND = new Color(50,70,255, 225);
    public static final Color SIDE_BACKGROUND = new Color(170,170,200, 175);
    private ImageComponent currentComp;
    private boolean hasItBeenDragged;
    public SideMenu(double x, double y, double width, double height, int rows, int columns, String... items) {
        super(x, y, width, height, rows, columns, items);

        Input.mouse().onClicked(e -> {
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
                        case "Constant": typeIndicator = new ConstantNode(); break;
                        case "Light": typeIndicator = new LightNode(); break;
                        case "NOT": typeIndicator = new NOTNode(); break;
                        case "AND": typeIndicator = new ANDNode(); break;
                        case "OR": typeIndicator = new ORNode(); break;
                        case "XOR": typeIndicator = new XORNode(); break;
                        case "ODD": typeIndicator = new ODDNode(); break;
                        default: return;
                    }
                    temp.graphVisuals.insertNode(typeIndicator,
                            new Rectangle2D.Double(Input.mouse().getMapLocation().getX(), Input.mouse().getMapLocation().getY(), 50, 50));
                    hasItBeenDragged = true;
                } else
                    temp.graphVisuals.moveNode(0, Input.mouse().getMapLocation().getX(), Input.mouse().getMapLocation().getY());
            }
        });

        Input.mouse().onReleased(e -> {
            if (e.getButton() == MouseEvent.BUTTON1 && currentComp != null) {
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
