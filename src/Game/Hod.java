package Game;

import Game.ColodaCards.Card;

public class Hod {
    private Card[] vzyatka;
    private int countCards;

    public Hod(){
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

    public void resetvzyatka(){
        for (int i = 0; i < 3; i++) {
            vzyatka[i] = null;
        }
        countCards = 0;
    }
}
