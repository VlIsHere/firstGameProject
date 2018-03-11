package Game.Players;

import Game.ColodaCards.Card;
import Game.ColodaCards.Coloda;
import Game.Hod;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String choiceForTypeGame;
    private List<Card> razdacha;
    private Hod playerHod;

    public Player(){
        razdacha = new ArrayList<>(10);
    }

    public void setChoiceForTypeGame(String choiceForTypeGame) {
        this.choiceForTypeGame = choiceForTypeGame;
    }

    public void takeCard(Card card){
        razdacha.add(card);
    }

    public Hod doHod(){
        return  null;
    }
    public Card getCard(Hod playerHod){
        //todo   выборка карт
        return null;//razdacha.getCard(0);
    }

}
