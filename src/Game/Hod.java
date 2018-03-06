package Game;

import Game.ColodaCards.Card;

public class Hod {
    private Card[] vzyatka;

    public Hod(Card one,Card two, Card three){
        vzyatka = new Card[3];
        vzyatka[0] = one;
        vzyatka[1] = two;
        vzyatka[2] = three;
    }

}
