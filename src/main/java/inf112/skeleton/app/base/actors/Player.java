package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.cards.Card;

import java.util.ArrayList;

public class Player implements IPlayer {
    protected Robot robot;
    protected String name;
    private ArrayList<Card> cards = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public Robot getRobot() {
        return robot;
    }

    @Override
    public void clearCards() {
        cards.clear();
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public void addRobot(Robot robot) {
        this.robot = robot;
    }

    @Override
    public Card useFirstCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("List with player cards is empty!");
        }

        // retrieves and removes first card in list.
        return cards.remove(0);
    }

    @Override
    public String getName() {
        return name;
    }

    public String toString(){
        return name;
    }

}
