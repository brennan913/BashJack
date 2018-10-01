/**
 * Depends on Enums Colors and Suits
 */
public class Card implements Comparable<Card>{
    // member variables
    public static final String[] cardNames = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Jack", "Queen", "King" };
    final int rank;
    public final Suits suit;
    public final Colors color;
    public final String name;
    public final int value;

    /**
     * Constructor
     */
    public Card(int r, Suits s) {

        rank = r;
        suit = s;

        if(r > 10)
            r = 10;
        value = r;

        if (s == Suits.Clubs || s == Suits.Spades)
            color = Colors.BLACK;
        else
            color = Colors.RED;

        name = cardNames[r - 1];
    }

    /**
     * compareTo Implementation
     */
    public int compareTo(Card c){
        if(this.rank > c.rank)
            return 1;
        else if(this.rank < c.rank)
            return -1;
        else 
            return 0;
    }

    @Override
    public String toString() {
        return name + " of " + suit;
    }

}