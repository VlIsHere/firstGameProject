package Game.Players;

import Game.ColodaCards.Card;
import Game.Hod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private String choiceForTypeGame;
    private List<Card> razdacha;
    private Hod playerHod;
    private int[] analyz;

    public Player(String name){
        this.name = name;
        razdacha = new ArrayList<>(10);
    }

    public int getChoiceTypeGame(int condition, Random rnd){
        //todo подумай над алгоритмом принятия решения о контракте!!!!
        if (analyz==null) analyz = analyzeCards(); //{больш карты,популярн масть,ее колво}
        if (condition ==0){
            if (analyz[2]>4 && analyz[0]>5) return analyz[1];//выберет в торгах 6 определ. масти
            else return 26 + rnd.nextInt(1);// 26 mizer, 27 - pass
        }
        else {
            if (condition%5 > analyz[1]) return 27;
            else if (condition%5==analyz[1] && analyz[2]>=5) return condition;
            else if (condition==27) return 27;
            else if (analyz[2]>6 || analyz[0]>6) return condition;
            else return ++condition;
        }
    }

    public int[] analyzeCards(){
        int sum = 0;
        int tmp;
        int[] masti = new int[4];
        for (int i = 0; i < 10; i++) {
            tmp = razdacha.get(i).getCode();
            if (tmp%10>1 && tmp%10<5) sum++;
            if (tmp<15) masti[0]++;//piki
            if (tmp>16 && tmp<25) masti[1]++;//trefy
            if (tmp>26 && tmp<35) masti[2]++;//bubi
            if (tmp>36) masti[3]++;//chervy
        }
        tmp = 0;
        int count = masti[0];
        for (int i = 1; i < 4; i++) {
            if (count<masti[i]) {count = masti[i];
                tmp = i;
            }
        }
        return new int[]{sum,++tmp,count};
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
