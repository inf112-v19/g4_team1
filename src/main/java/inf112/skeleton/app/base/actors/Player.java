package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.cards.Card;

import java.util.ArrayList;

public class Player {

    private Robot robot;
    private String name;
    private ArrayList<Card> cards;

    public Player(String name, Robot robot) {
        this.name = name;
        this.robot = robot;
    }
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void clearCards() {
        cards.clear();
    }

    public ArrayList<Card> getCards(ArrayList<Card> cards) {
        return cards;
    }

    public Card getFirstCard() {
        for(int i = 0; i < cards.size(); i++) {
            if(cards.get(i) instanceof Card) {
                Card firstCard = cards.get(i);
                cards.remove(i);
                return firstCard;
            }
        }
        throw new IllegalStateException("List with playercards is empty!");
    }
}
