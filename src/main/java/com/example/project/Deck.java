package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void initializeDeck(){ //hint.. use the utility class
        for (Card card : cards) {
            
        }
    }

    public void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
    }

    public Card drawCard(){
        if (isEmpty()) {
            return null;
        } else return cards.remove(0);
    }

    public boolean isEmpty(){
        return cards.size() == 0;
    }
}