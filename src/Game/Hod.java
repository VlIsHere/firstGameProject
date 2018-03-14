package Game;

import Game.ColodaCards.Card;

public class Hod {
    private Card[] vzyatka;
    private int countCards;

    Hod(){
        vzyatka = new Card[3];
    }

    public void setVzyatka(Card card){
        vzyatka[countCards++] = card;
    }

    public int getCountCards(){
        return countCards;
    }

    public Card[] getVzyatka(){
        return vzyatka;
    }

    public int getNumWhoTake(){
        Card max = vzyatka[0];
        int index = 0;
        for (int i = 1; i < vzyatka.length; i++) {
            if (vzyatka[i].compareTo(max)>1){
                max = vzyatka[i];
                index = i;
            }
        }
        return index;
    }

    public void resetvzyatka(){
        for (int i = 0; i < 3; i++) {
            vzyatka[i] = null;
        }
        countCards = 0;
    }
}
