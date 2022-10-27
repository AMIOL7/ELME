package ELME.Model;

import java.util.Optional;

/**
 * Represents the abstract concept of a port
 *
 * @author Máté Visnyár
 */
public abstract class Port {

    private String tag;

    /**
     * Construct a Port with a tag
     *
     * @param tag Name of port
     */
    public Port(String tag) {
        this.tag = tag;
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

}
