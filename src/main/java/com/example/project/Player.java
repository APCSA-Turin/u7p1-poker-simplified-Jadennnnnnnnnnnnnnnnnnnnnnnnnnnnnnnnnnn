package com.example.project;
import java.util.ArrayList;
import java.util.Collections;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){  
        allCards.addAll(communityCards); 
        allCards.addAll(hand);
        return "Nothing";
    }

    public void sortAllCards(){
        for (int i = 1; i < allCards.size(); i++) {
            Card current = allCards.get(i);
            int compare = Utility.getRankValue(current.getRank());
            int inner = i;
            while (inner > 0 && compare <= Utility.getRankValue(allCards.get(inner - 1).getRank())) {
                if (compare == Utility.getRankValue(allCards.get(inner - 1).getRank())) {
                    if (Utility.getSuitValue(current.getSuit()) > Utility.getSuitValue(allCards.get(inner - 1).getSuit())) {
                        break;
                    }
                }
                allCards.set(inner, allCards.get(inner - 1));
                inner--;
            }
            allCards.set(inner, current);
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        for (String rank : Utility.getRanks()) {
            for (Card card : allCards) {
                if (card.getRank().equals(rank)) {}
            }
        }
        return new ArrayList<>(); 
    }

    public ArrayList<Integer> findSuitFrequency(){
        return new ArrayList<>(); 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }




}
