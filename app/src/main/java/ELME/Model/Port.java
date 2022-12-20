package ELME.Model;

import java.util.Optional;

/**
 * Represents the abstract concept of a port
 *
 * @author Máté Visnyár
 */
public abstract class Port {

    private String tag;
    protected Node owner;

    /**
     * Construct a Port with a tag and owner
     *
     * @param tag Name of port
     * @param owner Owner of port
     */
    public Port(String tag, Node owner) {
        this.tag = tag;
        this.owner = owner;
    }

    /**
     * Returns value associated with port
     *
     * @return Optional Boolean value associated with port
     */
    public abstract Optional<Boolean> getValue();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Node getOwner() {
        return owner;
    }

    public void setOwner(Node owner) {
        this.owner = owner;
    }

}
