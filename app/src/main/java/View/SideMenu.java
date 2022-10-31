package View;

import de.gurkenlabs.litiengine.gui.Menu;

import java.awt.Color;

/**
 * @author pszi
 */
public class SideMenu extends Menu {

    public static final Color SIDE_FOREGROUND = new Color(100,180,120, 175);
    public static final Color SIDE_BACKGROUND = new Color(100,100,120, 175);

    public SideMenu(double x, double y, double width, double height, String... items) {
        super(x, y, width, height, items);
    }

    @Override
    public void prepare()
    {
        super.prepare();
        this.getCellComponents().forEach(comp -> {
            comp.getAppearance().setForeColor(SIDE_FOREGROUND);
            comp.getAppearance().setBackgroundColor1(SIDE_BACKGROUND);
            comp.getAppearance().setTransparentBackground(false);
            comp.getAppearanceHovered().setTransparentBackground(false);
        });
    }
}
