package View;

import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.ImageComponentList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntConsumer;

/**
 * Unfortunately, due to LitiEngine's implementation of Menu having limited
 * customization regarding the layout of the menus, the class has to be
 * entirely re-defined in order to create a menu with a vertical layout.
 * Thankfully, the default implementation is rather short, so copy-pasting
 * a few functions aren't a big issue.
 */

public class VerticalMenu extends ImageComponentList {

    private int currentSelection;
    private final String[] items;

    private final List<IntConsumer> selectionChangeConsumers;

    public VerticalMenu(
            final double x,
            final double y,
            final double width,
            final double height,
            final String... items) {
        this(x, y, width, height, null, items);
    }

    public VerticalMenu(
            final double x,
            final double y,
            final double width,
            final double height,
            final Spritesheet background,
            final String... items) {
        super(x, y, width, height, 1, items.length, null, background);
        this.items = items;
        this.selectionChangeConsumers = new CopyOnWriteArrayList<>();
    }

    public int getCurrentSelection() {
        return this.currentSelection;
    }

    public void onChange(final IntConsumer cons) {
        this.selectionChangeConsumers.add(cons);
    }

    @Override
    public void prepare() {

        super.prepare();
        for (int i = 0; i < this.items.length; i++) {
            final ImageComponent menuButton = this.getCellComponents().get(i);
            menuButton.setText(this.items[i]);
            menuButton.onClicked(
                    c -> this.setCurrentSelection(this.getCellComponents().indexOf(menuButton)));
        }
    }

    public void setCurrentSelection(final int currentSelection) {
        this.currentSelection = currentSelection;

        for (int i = 0; i < this.getCellComponents().size(); i++) {
            this.getCellComponents().get(this.currentSelection).setSelected(i == this.currentSelection);
        }

        this.selectionChangeConsumers.forEach(c -> c.accept(this.getCurrentSelection()));
    }
}

