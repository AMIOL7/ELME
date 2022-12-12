package ELME.Model;

import java.util.Optional;

/**
 * A kind of {@link ELME.Model.Port Port} which is used as inputs for
 * {@link ELME.Model.Node Nodes}. This kind of port can only be connected to one
 * other port ({@link ELME.Model.OutputPort OutputPort}). Notice that InputPort
 * itself does not store the input value, therefore it must get it from the port
 * it is connected to.
 *
 * Usually it is the InputPort's job to initiate a connection, or to disconnect
 * itself from an other port. {@link ELME.Model.OutputPort OutputPorts} act
 * passive in this regard.
 *
 * @author Máté Visnyár
 */
public class InputPort extends Port {

    private OutputPort connectedPort;

    /**
     * Construct an InputPort with a tag and owner
     *
     * @param tag Name of port
     * @param owner Owner of port
     */
    public InputPort(String tag, Node owner) {
        super(tag, owner);
    }

    /**
     * In case there is a valid value available returns it, otherwise returns
     * empty {@link Optional Optional}.
     *
     * @return Optional Boolean value associated with port
     */
    @Override
    public Optional<Boolean> getValue() {

        if (!isConnected()) {
            return Optional.empty();
        }

        return connectedPort.getValue();
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
     * Connects to supplied {@link ELME.Model.OutputPort OutputPort} if not
     * already connected.
     *
     * @param port {@link ELME.Model.OutputPort OutputPort} to connect to.
     */
    public void connect(OutputPort port) {
        if (!isConnected()) {
            this.connectedPort = port;
            port.getOwner().addDependent(this.getOwner());
        }
    }

    /**
     * Disconnects from port
     */
    public void disconnect() {
        connectedPort.getOwner().removeDependent(this.getOwner());
        connectedPort = null;
    }

    public OutputPort getConnectedPort() {
        return connectedPort;
    }

}
