package inf112.skeleton.app.base.cards;

import inf112.skeleton.app.base.actors.IRobot;

/**
 * Generic interface for a card
 */
public interface ICard {
    /**
     * get the type of the program card, eg. TURN_RIGHT
     *
     * @return Enum CardType with the direction
     */
    CardType getType();

    /**
     * get the priority number of the card, higher means it will be used before other cards
     *
     * @return priority number
     */
    int getPriorityNumber();

    /**
     * does what the program on the card says with the robot, e.g. moves the robot forward
     * @param robot robot to tryToMove
     */
    void execute(IRobot robot);

}
