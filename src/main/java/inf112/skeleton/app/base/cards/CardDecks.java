package inf112.skeleton.app.base.cards;

import java.util.ArrayList;
import java.util.Collections;

public class CardDecks {

    ArrayList<Card> fresh = new ArrayList<>();
    ArrayList<Card> used = new ArrayList<>();

    public CardDecks() {
        //adds a certain range of cards with each priority
        fresh.addAll(cardGenerator(CardType.TURN_RIGHT, 80, 20, 420));
        fresh.addAll(cardGenerator(CardType.TURN_LEFT, 70, 20, 400));
        fresh.addAll(cardGenerator(CardType.TURN_HALF, 10, 10, 60));
        fresh.addAll(cardGenerator(CardType.MOVE_1_TILE, 490, 10, 660));
        fresh.addAll(cardGenerator(CardType.MOVE_2_TILE, 670, 10, 780));
        fresh.addAll(cardGenerator(CardType.MOVE_3_TILE, 790, 10, 840));
        fresh.addAll(cardGenerator(CardType.MOVE_BACK, 430, 10, 480));
        System.out.println(fresh);
        System.out.println(fresh.size());
    }

    private ArrayList<Card> cardGenerator(CardType type, int pstart, int pdiff, int pend) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int priority = pstart; priority <= pend; priority += pdiff) {
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
    public ArrayList<Card>  getCards(int n){
        //takes n cards from the deck
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            cards.add(getCard());
        }
        return cards;
    }
    public void addUsed(Card card){
        used.add(card);
    }

}



