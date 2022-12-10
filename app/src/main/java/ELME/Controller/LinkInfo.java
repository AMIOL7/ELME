package ELME.Controller;

/**
 * record that stores info on the link, stored in the entity that gets the input
 * @param entity is the entity which has the node the connection goes OUT OF
 * @param number is the output number
 *
 * @author Pap Szabolcs István
 */


public record LinkInfo(LogicEntity entity, int number) {}
