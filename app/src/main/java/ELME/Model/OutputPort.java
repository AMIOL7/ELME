package ELME.Model;

import java.util.Optional;

/**
 * A kind of {@link ELME.Model.Port Port} which is used as outputs for
 * {@link ELME.Model.Node Nodes}. This kind of port can be connected to many other
 * ports ({@link ELME.Model.InputPort InputPort})
 *
 * Notice that OutputPorts are not able to initiate a connection nor store which
 * other ports they are connected to. Therefore they act passive in most
 * circumstances.
 *
 * @author Máté Visnyár
 */
public class OutputPort extends Port {

    private Optional<Boolean> value;

    /**
     * Construct an OutputPort with a tag and owner
     *
     * @param tag Name of port
     * @param owner Owner of port
     */
    public OutputPort(String tag, Node owner) {
        super(tag, owner);
        this.value = Optional.empty();
    }

    @Override
    public Optional<Boolean> getValue() {
        return value;
    }

    public void setValue(Optional<Boolean> value) {
        this.value = value;
    }

}
