package inf112.skeleton.app.base.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class for managing the deck of cards
 */
public class CardDecks {
    private ArrayList<Card> fresh = new ArrayList<>();
    private ArrayList<Card> used = new ArrayList<>();

    // adds a certain range of cards with related priority
    public CardDecks() {
        fresh.addAll(cardGenerator(CardType.TURN_RIGHT, 80, 20, 420));
        fresh.addAll(cardGenerator(CardType.TURN_LEFT, 70, 20, 400));
        fresh.addAll(cardGenerator(CardType.TURN_HALF, 10, 10, 60));
        fresh.addAll(cardGenerator(CardType.MOVE_1_TILE, 490, 10, 660));
        fresh.addAll(cardGenerator(CardType.MOVE_2_TILE, 670, 10, 780));
        fresh.addAll(cardGenerator(CardType.MOVE_3_TILE, 790, 10, 840));
        fresh.addAll(cardGenerator(CardType.MOVE_BACK, 430, 10, 480));
        Collections.shuffle(fresh);
    }

    /**
     * generate a deck of cards
     *
     * @param type type of the card
     * @param pstart lower bound of priority range
     * @param pdiff priority step
     * @param pend upper bound of priority range
     * @return list of cards
     */
    private ArrayList<Card> cardGenerator(CardType type, int pstart, int pdiff, int pend) {
        ArrayList<Card> cards = new ArrayList<>();

        for (int priority = pstart; priority <= pend; priority += pdiff)
            cards.add(new Card(type, priority));

        return cards;
    }

    /**
     * get the card from the top of the deck
     *
     * @return a card
     */
    public Card getCard() {
        if (fresh.isEmpty() && used.isEmpty())
            throw new IllegalStateException("no more cards");

        if (fresh.isEmpty()) {
            Collections.shuffle(used);
            fresh = used;
            used.clear();
        }

        Card card = fresh.get(0);
        fresh.remove(0);
        return card;
    }

    /**
     * take n cards from the deck
     */
    public ArrayList<Card> getCards(int n) {
        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i < n; i++)
            cards.add(getCard());

        return cards;
    }

    /**
     * add a card that was played
     *
     * @param card used card
     */
    public void addUsed(Card card) {
        used.add(card);
    }

}
