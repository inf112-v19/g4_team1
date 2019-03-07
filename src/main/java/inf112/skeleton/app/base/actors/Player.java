package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.cards.Card;

import java.util.ArrayList;

public class Player {

    private Robot robot;
    private String name;
    private ArrayList<Card> cards;

    public Player(String name) {
        this.name = name;
    }
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Robot getRobot() {
        return robot;
    }

    public void clearCards() {
        cards.clear();
    }

    public ArrayList<Card> getCards(ArrayList<Card> cards) {
        return cards;
    }

    public void addRobot(Robot robot) {
        this.robot = robot;
    }

    public Card useFirstCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("List with playercards is empty!");
        }
        // retrieves and removes first card in list.
        return cards.remove(0);
    }
}
