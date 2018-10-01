import java.util.LinkedList;
import java.util.Random;

/**
 * Class
 */
public class Deck {

    // member items
    private LinkedList<Card> cards = new LinkedList<>();
    private final int MINIMUM_SHUFFLE_COUNT = 7; 

    /**
     * Constructor
     */
    public Deck() {
        
        Card newCard;
        
        for (Suits s : Suits.values())
            for (int i = 13; i > 0; i--) {
                newCard = new Card(i, s);
                cards.push(newCard);
            }
        }
    public Deck(boolean shuffled) {
        this();
        if(shuffled)
            this.shuffle();

    }
    /**
     * @param Card b second card to be swapped
     * @param Card a first card to be swapped
     */
    private void swapReferences(LinkedList<Card> l, int a, int b) {
        Card temp = l.get(a);
        l.set( a, l.get(b) );
        l.set(b, temp);
    }

    /**
     * Shuffle attempts to randomize the ordering of the Deck object
     */
    public void shuffle() {
        Random r = new Random();
        for(int i=0;i<MINIMUM_SHUFFLE_COUNT;i++)
            for(int j=0;j<52;j++)
                swapReferences(cards, j, r.nextInt(52) );
    }

    /**
     * Shuffle overload for multiple shuffles at once 
     * @param int shuffleCount - number of times deck is to be shuffled
     */
    public void shuffle(int shuffleCount) {
        for (int i = 0; i < shuffleCount; i++)
            shuffle();
    }

    /**
     * Draw one card from the deck
     */
    public Card draw(){
        return cards.pop();
    }
    /**
     * Print the deck
     */
    public void print() {
        for(int i=0;i<cards.size();i++){
            System.out.print(cards.get(i));
           
            if((i+1)%13 == 0 && i!=0)
                System.out.println("\n");
            else{
                System.out.print(", ");
            }

        }
    }

    public static final void main(String[] args){
        Deck d = new Deck(true);
        d.print();
    }
}
