package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.cards.Card;

import java.util.ArrayList;

public interface IPlayer {

    /**
     * Gives the a list of cards
     * @param cards A list of cards
     */
    void setCards(ArrayList<Card> cards);

    /**
     * Removes all of the elements from the cards list. The list will be empty after this call.
     */
    void clearCards();

    /**
     *
     * @return A list with all of the players cards
     */
    ArrayList<Card> getCards();

    /**
     * Finds and removes the first card in the players card-list.
     * @return first card in the players card-list
     */
    Card getFirstCard();




}
