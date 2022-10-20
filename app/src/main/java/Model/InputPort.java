package Model;

/**
 * A kind of {@link Model.Port Port} which is used as inputs for
 * {@link Model.Node Nodes}. This kind of port can only be connected to one
 * other port ({@link Model.OutputPort OutputPort}). Notice that InputPort
 * itself does not store the input value, therefore it must get it from the port
 * it is connected to.
 *
 * Usually it is the InputPort's job to initiate a connection, or to disconnect
 * itself from an other port. {@link Model.OutputPort OutputPorts} act passive
 * in this regard.
 *
 * @author Máté Visnyár
 */
public class InputPort extends Port {

    private OutputPort connectedPort;

    /**
     * Construct an InputPort with a tag
     *
     * @param tag Name of port
     */
    public InputPort(String tag) {
        super(tag);
    }

    /**
     * If it is connected to a port it fetches the value thereof, otherwise it
     * returns false.
     *
     * @return boolean value associated with port
     */
    @Override
    public boolean getValue() {
        return isConnected() && connectedPort.getValue();
    }

    /**
     * Returns true if the port is connected to an other.
     *
     * @return true if the port is connected to an other.
     */
    public boolean isConnected() {
        return connectedPort != null;
    }

    /**
     * Connects to supplied {@link Model.OutputPort OutputPort}.
     *
     * Note that since an InputPort can only be connected to one
     * {@link Model.OutputPort OutputPort} at a time, calling this method
     * disconnects from the previously connected port.
     *
     * @param port {@link Model.OutputPort OutputPort} to connect to.
     */
    public void connect(OutputPort port) {
        this.connectedPort = port;
    }

    /**
     * Disconnects from port
     */
    public void disconnect() {
        connectedPort = null;
    }

    public OutputPort getConnectedPort() {
        return connectedPort;
    }

}
