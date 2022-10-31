package View;

import java.awt.Color;

/**
 * @author pszi
 */
public class Toolbar extends VerticalMenu {

    public static final Color TOOL_FOREGROUND = new Color(235,160,160, 195);
    public static final Color TOOL_BACKGROUND = new Color(160,160,170, 195);

    public Toolbar(double x, double y, double width, double height, String... items) {
        super(x, y, width, height, items);
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
