package com.mygdx.frick.cards;

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

}
