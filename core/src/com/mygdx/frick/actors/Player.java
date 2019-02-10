package com.mygdx.frick.actors;

import com.mygdx.frick.cards.Card;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Card> cards;

    public Player(String name) {
        this.name = name;

    }
    public void setCards(ArrayList<Card> cards) {
       this.cards = cards;
    }

    public ArrayList<Card> getCards(ArrayList<Card> cards) {
        return cards;
    }
}