import java.util.Scanner;
import java.util.Random;
import java.util.LinkedList;

/**
 * @author Brennan Xavier McManus
 * See Github.com/Brennan913 for documentation and more projects!
 */
public class BashJack {
    
    //establish static scanner to read in all user input. 
    private static Scanner console = new Scanner(System.in);
    private static String playerName = null;
    private static LinkedList<Player> players = new LinkedList<>();
    private static final int ACEBONUS = 10;
    private static final int DEALER_MAXIMUM = 14;

    private static void greet(){
        //greet player
        System.out.println("Welcome to BashJack, a text-based card game!");
        System.out.println("Would you like to play? (y/n)");
        String answer = console.nextLine().trim().toLowerCase();
        Boolean playGame = (answer.charAt(0) == 'y');
        if(!playGame){
            System.out.println("Okay, have a nice day!");
            System.exit(1);
        }  
        System.out.println("Okay! Here we go!");
    }

    private static void play(){
        String playAgain;        
        do{

            //make a computer house
            Player house = new Player("House");
            //make a player character
            Player human = new Player("Player");
            int roundCount = 0;
            while(house.winCount < 5 && human.winCount < 5){
                Deck d = new Deck(true);
                house.hand.clear();
                human.hand.clear();
                System.out.println("\n\nRound " + ++roundCount + "!");
                deal(house, human, d);
                playRound(house, human, d);

            }
            if(house.winCount > human.winCount){
                System.out.println("The house wins! Would you like to play again? (y/n)");
                playAgain = console.nextLine().trim().toLowerCase();
            } else {
                System.out.println("You won! Would you like to play again? (y/n)");
                playAgain = console.nextLine().trim().toLowerCase();
            }

                //start a game (ends when a player or house the reaches n wins, n provided by user)
                //start a round (ends when every player and the house have had a turn,winCount++ to highest score) 
                //start a turn(ends when player elects to stay, or if they go over 21, returns players card value sum as int)   
                
        } while(playAgain.charAt(0) == 'y');
    }

    /**
     * Thanks the user for playing, used to end the program 
     */
    private static void sayGoodbye(){
        System.out.println("Okay, thanks for playing! I hope you enjoyed BashJack! \n --Brennan Xavier McManus");
    }
    
    /**
     * Utility method to play one round of the game, increments one player's winCount by one
     */
    private static void playRound(Player h, Player p, Deck d){
        
        int playerPoints = playTurn(p, d);
        int housePoints = playTurn(h, d);
        
        

        System.out.println("The house has " + h.displayHand());
        System.out.println("You have " + p.displayHand());
        //ties go to the house
        if(housePoints >= playerPoints){
            h.winCount++;
            System.out.println("The house wins this round! The house has won " + h.winCount + " times.");
        }
        else {
            p.winCount++;
            System.out.println("You win this round! You have won " + p.winCount + " times.");
        }
    }

    /**
     * Overloaded playRound allows for this game to be expanded to arbitrary number of players
     */
    private static void playRound(Player h, LinkedList<Player> lp, Deck d){
        for(Player p:lp)
            playRound(h, p, d);
    }

    /**
     * Utility method to deal cards before round starts
     */
    private static void deal(Player h, Player p, Deck d){
        try {
            h.hand.add(d.draw());
            p.hand.add(d.draw());
            h.hand.add(d.draw());
            p.hand.add(d.draw());
        } catch(java.util.NoSuchElementException n){
            System.out.println("The deck is empty!");
            System.out.println("Thanks for playing, I hope you enjoyed BashJack!");
            System.out.println("--Brennan Xavier McManus");
            System.exit(-1);
        }
        System.out.println("\n\nDealing cards!");
        System.out.println("The dealer slides you a card, then places another on the table, both facedown.");
        System.out.println("He then places a second card in front of each of you, both faceup.");
        System.out.println("You each have two cards: one face up and face down. \n");
        System.out.println("You can see the dealer has the " + h.hand.get(0).toString() + " faceup in front of him.\n");
        System.out.println("Faceup, you have the " + p.hand.get(0).toString() + "." );
        System.out.println("In your hand, you have the " + p.hand.get(1).toString() + ".");
    }

    /**z
     * Plays a single turn for one player
     * handles hitting and staying
     * reports if they go bust
     * reports their score
     * @param Player p player who's turn it is
     * @param Deck d deck used for this game
     * @return int total value of their hand this turn
     */
    private static int playTurn(Player p, Deck d){

        int points = 0;
        int hits = 0;
        if(p.name.equals("House")){
            return playHouseTurn(p, d);
        }else{
            
            if (hits++ !=0 ){
                System.out.println("In your hand you have: " + p.displayHand() );
                for(Card c: p.hand)
                    points += aceChoice(c);
            }
            System.out.println("Would you like to hit?");
            String hit = console.nextLine().trim().toLowerCase();
            System.out.println();
            while(hit.charAt(0) == 'y'){
                Card c = d.draw();
                System.out.println( "You drew the " + c.toString() );
                points += aceChoice(c);
                p.hand.add(c);
                if(getPoints(p) > 21){
                    System.out.println("You've gone bust! You get 0 points!");
                    return 0;
                } else if(getPoints(p) == 21) {
                    System.out.println("Bashjack!");
                    return 21;
                }else {
                    System.out.println("\nYou currently have: " + p.displayHand() );
                    System.out.println("Hit again? (y/n)");
                    hit = console.nextLine().trim().toLowerCase();
                }
            }
            if(getPoints(p) > 21){
                System.out.println("You've gone bust! You get 0 points!");
                return 0;
            } else if(getPoints(p) == 21) {
                System.out.println("Bashjack!");
                return 21;
            }   
            points += getPoints(p);
            System.out.println("Ending your turn! Your hand was worth " + points +  " points.\n" );
            return points;
        }
    }
    /**
     * Acecheck
     * @param card to check 
     * @return int points to add 
     */
    private static int aceChoice(Card c){
        int points = 0;
        if (c.name.equals("Ace")){
            System.out.println("This card is an Ace! Aces can be 1 point, or 11.");
            System.out.println("Would you like it to be worth 11 points? (y/n)");
            String response = console.nextLine().trim().toLowerCase();
            if(response.charAt(0) == 'y'){
                points += ACEBONUS;
                System.out.println("Adding extra points!");
            } else{
                System.out.println("Understood, keeping this cards value as 1 point.");
            }
        }
        return points;
    }
    /**
     * Play house turn makes house hit or stay 
     * based on predetermined rules
     * @param Player h house 
     * @param Deck d deck to use
     * @return int s total value of his hand this turn
     */
    private static int playHouseTurn(Player h, Deck d){
        int points = getPoints(h);
        while(points < DEALER_MAXIMUM){
            Card c = d.draw();
            System.out.println("The dealer draws the " + c.toString() + ".");
            h.hand.add(c);
            points = getPoints(h);
        }
        if(points > 21){
            System.out.println("The house goes bust! They get 0 points.");
            return 0;
        } else if (points == 21){
            System.out.println("The house gets Bashjack! They get 21 points!");
            return 21;
        } else {
            return points;
        }

    }
    /**
     * Utility method to assess total value of current hand
     * @param Player owning hand to be checked
     * @return int value of their hand
     */
    private static int getPoints(Player p){
        int points = 0;
        for(Card c: p.hand)
            points += c.value;
        return points;
    } 
    public static final void main(String[] args){   
        
        //greet player
        //create a shuffled deck
        //make a computer house 
        //make a player character
        //start a game (ends when a player or house the reaches 10 wins)
        //start a round (ends when every player and the house have had a turn)
        //start a turn(ends when player elects to stay, or if they go over 21)
        greet();
        play();
        sayGoodbye();

        
    }
}