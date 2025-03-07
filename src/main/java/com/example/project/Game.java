package com.example.project;
import java.util.ArrayList;


public class Game{
    // Determines the winner of a round of poker
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        // Uses the utility class the get the ranking of the player's hands
        int rank1 = Utility.getHandRanking(p1Hand);
        int rank2 = Utility.getHandRanking(p2Hand);

        // Compares the hands, returns a string stating who wins based on the ranking
        if (rank1 > rank2) {
            return "Player 1 wins!";
        } else if (rank2 > rank1) {
            return "Player 2 wins!";
        } else return tie(p1, p2); // calls the tie method if the ranking is the same
    }

    // Acts as a tiebreaker
    public static String tie(Player p1, Player p2) {
        // pulls the sorted hands from both players
        ArrayList<Card> cards1 = p1.getHand();
        ArrayList<Card> cards2 = p2.getHand();
        
        //Compares the ranks of the second card, which is greater than the first card
        int rank1 = Utility.getRankValue(cards1.get(1).getRank()); // Utility class is used again
        int rank2 = Utility.getRankValue(cards2.get(1).getRank());
        System.out.println(rank1 + " " + rank2); // for testing
        // Compares the ranks of the highest cards in hand, returns a string stating who wins based on the ranking
        if (rank1 > rank2) {
            return "Player 1 wins!";
        } else if (rank2 > rank1) {
            return "Player 2 wins!";
        }
        
        System.out.println(); // if it's still a tie, the suits are then compared
        int suit1 = Utility.getSuitValue(cards1.get(1).getSuit()); // Utility class is used again
        int suit2 = Utility.getSuitValue(cards2.get(1).getSuit());
        System.out.println(suit1 + " " + suit2); // for testing

        // Compares the suits of the highest cards in hand, returns a string stating who wins based on the ranking
        if (suit1 > suit2) {
            return "Player 1 wins!";
        } else if (suit2 > suit1) {
            return "Player 2 wins!";
        }
        // returns tie if it's still a tie
        return "Tie!"; 
    }


    public static void play(){ //simulate card playing
    
    }
        
        

}