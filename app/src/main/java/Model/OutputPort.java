package Model;

/**
 * A kind of {@link Model.Port Port} which is used as outputs for
 * {@link Model.Node Nodes}. This kind of port can be connected to many other
 * ports ({@link Model.InputPort InputPort})
 *
 * Notice that OutputPorts are not able to initiate a connection nor store which
 * other ports they are connected to. Therefore they act passive in most
 * circumstances.
 *
 * @author Máté Visnyár
 */
public class OutputPort extends Port {

    private Boolean value;

    /**
     * Construct an OutputPort with a tag
     *
     * @param tag Name of port
     */
    public OutputPort(String tag) {
        super(tag);
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
