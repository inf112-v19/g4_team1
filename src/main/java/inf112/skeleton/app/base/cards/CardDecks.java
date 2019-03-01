package inf112.skeleton.app.base.cards;

import java.util.ArrayList;
import java.util.Collections;

public class CardDecks {

    ArrayList<Card> fresh = new ArrayList<>();
    ArrayList<Card> used = new ArrayList<>();

    public CardDecks() {
        //adds a certain range of cards with each priority
        fresh.addAll(cardGenerator(CardType.MOVE_1_TILE, 50, 10, 100));
    }

    private ArrayList<Card> cardGenerator(CardType type, int pstart, int pdiff, int pend) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int priority = pstart; start < pend; priority += pend) {
            cards.add(new Card(type, priority));
        }
        return cards;
    }

    public Card getCard() {
        if (fresh.isEmpty() && used.isEmpty()) {
            throw new IllegalStateException("no more cards");
        }
        if (fresh.isEmpty()) {
            Collections.shuffle(used);
            fresh = used;
            used.clear();
        }
        Card card = fresh.get(0);
        fresh.remove(0);
        return card;
    }
    public void addUsed(Card card){
        used.add(card);
    }

}



