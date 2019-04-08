package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.cards.Card;

import java.util.ArrayList;

public interface IPlayer {

    /**
     * Gives the player a list of cards
     *
     * @param cards A list of cards
     */
    void setCards(ArrayList<Card> cards);

    /**
     * Removes all of the elements from the cards list. The list will be empty after this call
     */
    void clearCards();

    /**
     * Get a list with all of the players cards
     *
     * @return a list of cards
     */
    ArrayList<Card> getCards();

    /**
     * Finds and removes the first card in the players card-list
     *
     * @return first card in the players card-list
     */
    Card useFirstCard();

    /**
     * get the robot associated with the player
     *
     * @return player robot
     */
    Robot getRobot();

    /**
     * assign a robot to the player
     *
     * @param robot a robot
     */
    void addRobot(Robot robot);

    /**
     *
     * @return player name
     */
    String getName();

}
