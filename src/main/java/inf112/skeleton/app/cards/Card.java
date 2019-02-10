package inf112.skeleton.app.cards;

import inf112.skeleton.app.actors.IRobot;

public class Card implements  ICard{
    private CardType type;
    private int priorityNumber;

    public Card(CardType type, int priorityNumber) {
        this.type = type;
        this.priorityNumber = priorityNumber;
    }

    public CardType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Card type "+ type+", priority "+priorityNumber;
    }

    public void execute(IRobot robot){
        switch (type){
            case TURN_LEFT: robot.turnLeft();
                break;
            case TURN_RIGHT: robot.turnRight();
                break;
            case TURN_HALF: robot.turnHalf();
                break;
            case MOVE_1_TILE: robot.moveForward(1);
                break;
            case MOVE_2_TILE: robot.moveForward(2);
                break;
            case MOVE_3_TILE: robot.moveForward(3);
                break;
            case MOVE_BACK: robot.moveBackwards();
                break;
        }
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }
}
