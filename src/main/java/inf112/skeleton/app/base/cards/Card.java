package inf112.skeleton.app.base.cards;

import inf112.skeleton.app.base.actors.IRobot;

public class Card implements ICard {
    private CardType type;
    private int priorityNumber;

    public Card(CardType type, int priorityNumber) {
        this.type = type;
        this.priorityNumber = priorityNumber;
    }

    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Card type " + type + ", priority " + priorityNumber;
    }

    @Override
    public void execute(IRobot robot) {
        switch (type) {
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

    @Override
    public int getPriorityNumber() {
        return priorityNumber;
    }


    public String imageFileName () {
        String file = "";
        switch (type) {
            case TURN_LEFT: file+="RL";
                break;
            case TURN_RIGHT: file+="RR";
                break;
            case TURN_HALF: file+="UT";
                break;
            case MOVE_1_TILE: file+="MV1";
                break;
            case MOVE_2_TILE: file+="MV2";
                break;
            case MOVE_3_TILE: file+="MV3";
                break;
            case MOVE_BACK: file+="BCK";
                break;
        }
        file+=Integer.toString(priorityNumber);
        file+=".png";
        return file;

    }



}
