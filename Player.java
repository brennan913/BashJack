import java.util.LinkedList;
/**
 * Depends on Card.java, Deck.java
 */
public class Player{

    //member items
    public LinkedList<Card> hand;
    public int winCount;
    public String name;

    /**
     * Constructor
     */
    public Player(String n){
        hand = new LinkedList<>();
        winCount = 0;
        name = n;
    }
    
    /**
     * used to show all of players cards at the end of a round
     * @return String representation of players hand
     */
    public String displayHand(){
        StringBuilder handBuilder = new StringBuilder();
        int i = 0;
        for(Card c: hand){
            handBuilder.append( c.toString() );
            
            //format the list of cards with commas
            if( (++i)%hand.size() != 0)
                handBuilder.append(", ");
            else
                handBuilder.append(".");
        }

        return handBuilder.toString();
    }

    @Override
    public String toString(){
        return name;
    }

    
}