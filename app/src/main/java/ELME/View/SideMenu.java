package View;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author pszi
 */
public class SideMenu extends ExtraMenu {

    public static final Color SIDE_FOREGROUND = new Color(100,180,120, 175);
    public static final Color SIDE_BACKGROUND = new Color(100,100,120, 175);
    //private final List<Consumer<Integer>> confirmConsumer;
    private ImageComponent currentComp;
    private boolean hasItBeenDragged;
    public SideMenu(double x, double y, double width, double height, int rows, int columns, String... items) {
        super(x, y, width, height, rows, columns, items);
        //confirmConsumer = new CopyOnWriteArrayList<>();

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
                        System.out.println(comp.getText() + " pressed");
                    }
                    //currentComp = new ImageComponent(e.getPoint().getX(), e.getPoint().getY(), 100, 100, "Look at this");
                    //getCellComponents().add(currentComp);
                }
            }
        });

        Input.mouse().onDragged(e -> {
            if (Input.mouse().isLeftButtonPressed() && currentComp != null) {
                if (!hasItBeenDragged)
                {
                    ImageComponent newComp = new ImageComponent(currentComp.getX(), currentComp.getY(), currentComp.getWidth(), currentComp.getHeight(), currentComp.getText());
                    newComp.getAppearance().setForeColor(new Color(255, 0, 0));
                    newComp.getAppearance().setBackgroundColor1(new Color(100, 175, 250));
                    newComp.getAppearance().setTransparentBackground(false);
                    newComp.getAppearanceHovered().setTransparentBackground(false);
                    currentComp = newComp;
                    Game.screens().current().getComponents().add(currentComp);
                    currentComp.setEnabled(true);
                    currentComp.setVisible(true);
                    System.out.println(currentComp.getText() + " dragged");
                    hasItBeenDragged = true;
                }
                currentComp.setLocation(Input.mouse().getLocation());
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
