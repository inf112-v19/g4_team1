package com.mygdx.frick.cards;

public class Card {
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
        return "Card type "+ type;
    }

    public void execute(IRobot robot){
        switch (type){
            case TURN_LEFT: robot.turnLeft();
                break;
            case TURN_RIGHT: robot.turnRight();
                break;
            case TURN_HALF: robot.turnHalf();
                break;
            case MOVE_1_TILE: robot.Move();
                break;
            case MOVE_2_TILE: robot.Move();
                break;
            case MOVE_3_TILE: robot.Move();
                break;
        }
    }
}
