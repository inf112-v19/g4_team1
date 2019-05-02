package inf112.skeleton.app.base.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class for managing the deck of cards
 */
public class CardDecks {
    private ArrayList<Card> freshDeck = new ArrayList<>();
    private ArrayList<Card> usedDeck = new ArrayList<>();

    // adds a certain range of cards with related priority
    public CardDecks() {
        freshDeck.addAll(cardGenerator(CardType.TURN_RIGHT,  80,  20, 420));
        freshDeck.addAll(cardGenerator(CardType.TURN_LEFT,   70,  20, 400));
        freshDeck.addAll(cardGenerator(CardType.TURN_HALF,   10,  10, 60));
        freshDeck.addAll(cardGenerator(CardType.MOVE_1_TILE, 490, 10, 660));
        freshDeck.addAll(cardGenerator(CardType.MOVE_2_TILE, 670, 10, 780));
        freshDeck.addAll(cardGenerator(CardType.MOVE_3_TILE, 790, 10, 840));
        freshDeck.addAll(cardGenerator(CardType.MOVE_BACK,   430, 10, 480));
        Collections.shuffle(freshDeck);
    }

    /**
     * generate a deck of cards
     *
     * @param type type of the card
     * @param pStart lower bound of priority range
     * @param pDiff priority step
     * @param pEnd upper bound of priority range
     * @return list of cards
     */
    private ArrayList<Card> cardGenerator(CardType type, int pStart, int pDiff, int pEnd) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int priority = pStart; priority <= pEnd; priority += pDiff)
            cards.add(new Card(type, priority));

        return cards;
    }

    /**
     * get the card from the top of the deck
     *
     * @return a card
     */
    public Card getCard() {
        if (freshDeck.isEmpty() && usedDeck.isEmpty())
            throw new IllegalStateException("no more cards");

        if (freshDeck.isEmpty()) {
            Collections.shuffle(usedDeck);
            freshDeck.addAll(usedDeck);
            usedDeck.clear();
        }
        return freshDeck.remove(0);
    }

    /**
     * take n cards from the deck
     */
    public ArrayList<Card> getCards(int n) {
        //System.out.println("gets cards "+n);
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < n; i++)
            cards.add(getCard());

        return cards;
    }

    /**
     * add a card that was played
     *
     * @param card usedDeck card
     */
    public void addUsed(Card card) {
        if(card.getType() == CardType.POWERDOWN){
            throw new IllegalArgumentException();
        }
        //System.out.println("adds a used card");
        usedDeck.add(card);
    }

}
