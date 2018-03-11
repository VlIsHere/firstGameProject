package Game.ColodaCards;

import java.util.ArrayList;
import java.util.List;

public class Coloda {
    private List<Card> cards;
    public static final String PIKI = "♠";
    public static final String CHERVY = "♥";
    public static final String TREFY = "♣";
    public static final String BUBI = "♦";

    public Coloda(){
        cards = new ArrayList<>(32);
            for (int j = 7; j < 11; j++) {
                cards.add(new Card(String.valueOf(j),PIKI,j));
                cards.add(new Card(String.valueOf(j),CHERVY,30+j));
                cards.add(new Card(String.valueOf(j),BUBI,20+j));
                cards.add(new Card(String.valueOf(j),TREFY,10+j));
            }
        cards.add(new Card("Валет",CHERVY,41));
        cards.add(new Card("Валет",BUBI,31));
        cards.add(new Card("Валет",TREFY,21));
        cards.add(new Card("Валет",PIKI,11));
        cards.add(new Card("Дама",CHERVY,42));
        cards.add(new Card("Дама",BUBI,32));
        cards.add(new Card("Дама",TREFY,22));
        cards.add(new Card("Дама",PIKI,12));
        cards.add(new Card("Король",CHERVY,43));
        cards.add(new Card("Король",BUBI,33));
        cards.add(new Card("Король",TREFY,23));
        cards.add(new Card("Король",PIKI,13));
        cards.add(new Card("Туз",CHERVY,44));
        cards.add(new Card("Туз",BUBI,34));
        cards.add(new Card("Туз",TREFY,24));
        cards.add(new Card("Туз",PIKI,14));
    }

    public Card getCard(int i){
        return cards.get(i);
    }

    public void setCard(int i, Card card){
        cards.set(i,card);
    }

    public  int getSize(){
        return cards.size();
    }
}
