package com.example.project;
import java.util.ArrayList;
import java.util.Collections;


public class Player{
    private ArrayList<Card> hand; // Player's private hand 
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits(); //Array of suits, used for frequency methods
    String[] ranks = Utility.getRanks(); //Array of ranks
    
    // Constructor to intialize empty hand and all cards
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;} // returns the player's hand
    public ArrayList<Card> getAllCards(){return allCards;} // returns the all cards list

    // adds a card to the player's hand
    public void addCard(Card c){
        hand.add(c);
    }

    // finds the best hand awailable to the player based on the player's hand + community cards
    public String playHand(ArrayList<Card> communityCards){  
        allCards.clear(); // makes the allcards list empty
        allCards.addAll(hand);  // adds to hand and communitity cards to allCards
        allCards.addAll(communityCards);
        sortAllCards(); // sorts allCard
        sortHand(); // sorts players hand, used for the tiebreaking function

        // Check hand rankings from strongest to weakest
        if (royalFlush()) {
            return "Royal Flush";
        }
        if (straightFlush()) {
            return "Straight Flush";
        }
        if (fourOfAKind()) {
            return "Four of a Kind";
        }
        if (fullHouse()) {
            return "Full House";
        }
        if (flush()) {
            return "Flush";
        }
        if (straight()) {
            return "Straight";
        }
        if (threeOfAKind()) {
            return "Three of a Kind";
        }
        if (twoPair()) {
            return "Two Pair";
        }
        if (pair()) {
            return "A Pair";
        }
        if (nothing()) {
            return "Nothing";
        }
        return "High Card"; // if no other hand is possible
    }

    // sorts all cards by rank and suit if ranks are the same
    public void sortAllCards(){
        for (int i = 1; i < allCards.size(); i++) {
            Card compare = allCards.get(i); // used insertion, this is the current card to be inserted
            int j = i - 1;
            while (j >= 0) {
                int rank1 = Utility.getRankValue(allCards.get(j).getRank()); //rank of prev card
                int rank2 = Utility.getRankValue(compare.getRank()); // rank of current card
                boolean swap = false;
                if (rank1 > rank2) { // if the prev rank is higher, swap is set to true
                    swap = true;
                } else if (rank1 == rank2) { // checks if ranks are equal, then commpares using suits
                    int suit1 = Utility.getSuitValue(allCards.get(j).getSuit());
                    int suit2 = Utility.getSuitValue(compare.getSuit());
                    swap = suit1 > suit2;
                }
                if (!swap) { // stops soritng if no longer needs to sort
                    break;
                }
                allCards.set(j + 1, allCards.get(j)); // shifts prev cr=ard right
                j--;
            }
            allCards.set(j + 1, compare); // puts card in the right direction
        }
    } 

    // exaactly the same as sortAllCards, but for the player's hand. I used ctrlc, ctrlv cause I was too lazy
    public void sortHand(){
        for (int i = 1; i < hand.size(); i++) {
            Card compare = hand.get(i);
            int j = i - 1;
            while (j >= 0) {
                int rank1 = Utility.getRankValue(hand.get(j).getRank());
                int rank2 = Utility.getRankValue(compare.getRank());
                boolean swap = false;
                if (rank1 > rank2) {
                    swap = true;
                } else if (rank1 == rank2) {
                    int suit1 = Utility.getSuitValue(hand.get(j).getSuit());
                    int suit2 = Utility.getSuitValue(compare.getSuit());
                    swap = suit1 > suit2;
                }
                if (!swap) {
                    break;
                }
                hand.set(j + 1, hand.get(j));
                j--;
            }
            hand.set(j + 1, compare);
        }
    }

    // Finds frequency of all ranks of cards
    public ArrayList<Integer> findRankingFrequency(){
        // creates a new arraylist the same size as the ranks 
        ArrayList<Integer> frequency = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) { // sets them all to 0
            frequency.add(0);
        }

        for (Card card : allCards) { // iterates through all cards
            String rank = card.getRank();
            int index = Utility.getRankValue(rank) - 2; // -2 because 2 is mapped to 2 instead of 0
            frequency.set(index, frequency.get(index) + 1); // increases the count of the rank index
        }
        return frequency; // returns the frequency list of the ranks
    } 

    // Finds frequency of all suits of cards,
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>(); // creates a new arraylist the same size as the ranks 
        for (int i = 0; i < suits.length; i++) {  // sets them all to 0
            frequency.add(0);
        }

        for (Card card : allCards) { // iterates through all cards
            for (int i = 0; i < suits.length; i++) { // loops through all suits
                if (card.getSuit().equals(suits[i])) {
                    frequency.set(i, frequency.get(i) + 1);
                    break; // exits inner loop to move onto next card
                }
            }
        }
        return frequency;
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    // checks if the hand has a pair
    private boolean pair() {
        // Uses the list returned by findRankingRequency
        for (int rank : findRankingFrequency()) {
            // If a rank has 2 counts, it return true
            if (rank == 2) {
                return true;
            }
        }
        return false;
    }

    // checks if the hand has two pairs
    private boolean twoPair() {
        // Uses this variable to see if there are two distinct pairs
        int count = 0;
         // Uses the list returned by findRankingRequency
        for (int rank : findRankingFrequency()) {
            // If a rank has 2 counts, count increases by 1
            if (rank == 2) {
                count++; 
            }
        }
        return count >= 2;
    }

    // checks if the hand has a 3 of a kind
    private boolean threeOfAKind() {
         // Uses the list returned by findRankingRequency
        for (int rank : findRankingFrequency()) {
            // If a rank has 3 counts, it return true
            if (rank == 3) {
                return true;
            }
        }
        return false;
    }

    // checks if the player has a straight
    private boolean straight() {
        int count = 0; int prev = -1;
        for (Card card : allCards) {
            int rank = Utility.getRankValue(card.getRank());
            if (prev != -1 && rank == prev + 1) { // checks if rank increases by 1
                count++;
                if (count == 4) { // straight needs 5 in a row
                    return true;
                }
            } else if (rank != prev) { // resets the count if the streak is broken
                count = 0;
            }
            prev = rank;
        }
        return false;
    }
    
    //checks if the player has a flush
    private boolean flush() {
         // Uses the list returned by findSuitRequency
        for (int suit : findSuitFrequency()) {
            // If a suit has 5 counts, it return true
            if (suit >= 5) {
                return true;
            }
        }
        return false;
    }

    // checks if the player has a full fhouse
    private boolean fullHouse() {
        // checks if the player has a three of a kind and a pair
        return threeOfAKind() && pair();
    }

    // checks if the player has a 4 of a kind
    private boolean fourOfAKind() {
         // Uses the list returned by findRankingRequency
        for (int rank : findRankingFrequency()) {
            // If a rank has 4 counts, it return true
            if (rank == 4) {
                return true;
            }
        }
        return false;
    }

    // checks if the player has a straight flush
    private boolean straightFlush() {
        // checks if the player has both a flush and a straight
        return flush() && straight();
    }

    // checks if the player has a royal flush
    private boolean royalFlush() {
        // checks if the player has a straight flush and their largest card is A, which is the final part of a royal flush
        return straightFlush() && allCards.get(allCards.size() - 1).getRank().equals("A");
    }

    // checks if the player has nothing
    private boolean nothing() {
        // finds the highest card
        Card high = allCards.get(allCards.size() - 1);
        // Iterates through player's hand to see if the higheset card is part of their hand
        for (Card card : hand) {
            // If so, the player doesn't have nothing
            if (card.getRank().equals(high.getRank()) && card.getSuit().equals(high.getSuit())) {
                return false;
            }
        }
        return true;
    }
}


