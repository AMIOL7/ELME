package ELME.View;

import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * @author pszi
 */
public class Toolbar extends ExtraMenu {

    public static final Color TOOL_FOREGROUND = new Color(235,160,160, 195);
    public static final Color TOOL_BACKGROUND = new Color(160,160,170, 195);

    public Toolbar(double x, double y, double width, double height, int rows, int columns, String... items) {
        super(x, y, width, height, rows, columns, items);

        Input.mouse().onClicked(e -> {
            if (e.getButton() == MouseEvent.BUTTON1)
                for (ImageComponent comp : getCellComponents()) {
                    if (comp.getBoundingBox().contains(e.getPoint()))
                        System.out.println(comp.getText() + " pressed");
                }
        });
    }

    @Override
    public void prepare()
    {
        super.prepare();
        this.getCellComponents().forEach(comp -> {
            comp.getAppearance().setForeColor(TOOL_FOREGROUND);
            comp.getAppearance().setBackgroundColor1(TOOL_BACKGROUND);
            comp.getAppearance().setTransparentBackground(false);
            comp.getAppearanceHovered().setTransparentBackground(false);
        });
    }
}
