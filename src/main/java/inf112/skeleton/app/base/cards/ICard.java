package inf112.skeleton.app.base.cards;

import inf112.skeleton.app.base.actors.IRobot;

public interface ICard {
    /**
     * get the type of the program card, eg. TURN_RIGHT
     *
     * @return Enum CardType with the direction
     */
    public CardType getType();

    /**
     * get the priority number of the card, higher means it will be used before other cards
     *
     * @return priority number
     */
    public int getPriorityNumber();

    /**
     * does what the program on the card says with the robot, e.g. moves the robot forward
     * @param robot robot to move
     */
    public void execute(IRobot robot);
}
