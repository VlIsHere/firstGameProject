package Game.Players;

import Game.ColodaCards.Card;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> razdacha;
    private int maxVzyatka;
    private int numbInmast;
    private ArrayList<ArrayList<Card>> masti;
    private ArrayList<Card> piki;
    private ArrayList<Card> trefy;
    private ArrayList<Card> bubi;
    private ArrayList<Card> chervy;
    private ArrayList<Card> dominateMast;
    private String gameStyle;
    private int countVzyatok;
    private int realCntVzyatok;
    private List<Card[]> vzyatki;

    public void setRealCntVzyatok(int realCntVzyatok) {
        this.realCntVzyatok = realCntVzyatok;
    }

    public void setGameStyle(String gameStyle) {
        this.gameStyle = gameStyle;
    }

    public Player(String name){
        this.name = name;
        razdacha = new ArrayList<>();
       piki = new ArrayList<>(8);
       trefy= new ArrayList<>(8);
       bubi= new ArrayList<>(8);
       chervy= new ArrayList<>(8);
       gameStyle = "";
       vzyatki = new LinkedList<>();
    }

    public Card doHod(Card[] cardsShown,int countCardsOpened){
        Card res = null; int indexRes = 0;
        if (gameStyle.equals("mizer") || gameStyle.equals("pass")){
            if (countCardsOpened==0){
                indexRes = findIndMinCard();
            }else if (countCardsOpened==1){
                indexRes = getCardSmallerThis(cardsShown[0]);
                if (indexRes==-1) indexRes = findIndMaxCard();
            }else {
                indexRes = cardsShown[0].compareTo(cardsShown[1])<0?
                        getCardSmallerThis(cardsShown[0]):getCardSmallerThis(cardsShown[1]);
            }
        }else {
            if (countVzyatok>realCntVzyatok){
                if (countCardsOpened==0){
                    indexRes = findIndMaxCard();
                }else if (countCardsOpened==1){
                    indexRes = getCardBiggerThis(cardsShown[0]);
                    if (indexRes==-1) indexRes = findIndMinCard();
                }else {
                    indexRes = cardsShown[0].compareTo(cardsShown[1])>0?
                            getCardBiggerThis(cardsShown[0]):getCardBiggerThis(cardsShown[1]);
                }
            }else {
                indexRes = (int)(Math.random()*razdacha.size());
            }
        }
        res = razdacha.get(indexRes);
        razdacha.remove(indexRes);
        return res;
    }

    public void takeVzyatka(Card[] cards){
        vzyatki.add(cards);
    }

    public int getCardBiggerThis(Card card){
        for (int i = 0; i < razdacha.size(); i++) {
            if (razdacha.get(i).compareTo(card)>0){
                return i;
            }
        }
        return -1;
    }

    public int getCardSmallerThis(Card card){
        for (int i = 0; i < razdacha.size(); i++) {
            if (razdacha.get(i).compareTo(card)<0){
                return i;
            }
        }
        return -1;
    }

    public int getChoiceTypeGame(int mast,int oldMaxVzyatka){
        //todo подумай над алгоритмом принятия решения о контракте!!!!
       // if (mast==-3) return (int)Math.round(Math.random()-4);// это выбор, когда 1 пасанул, а другой либо пас, либо полвиста
        if (mast>4){//0-piki;1-trefy;2-bubi;3-chervy;4-bez kozyrya
            mast =0;//чтоб контролировать переход по "ступеням" взяток
            oldMaxVzyatka++;
        }
        if (maxVzyatka ==0) {
            maxVzyatka = analyzeCards();
            if (maxVzyatka<6) return (int) Math.round(Math.random()-2);//-1 - pass; -2 - mizer
        }
        else if (oldMaxVzyatka<maxVzyatka) {
            if (mast==numbInmast && Math.round(Math.random())==0) return mast;
            else return ++mast;
        }
        else if (oldMaxVzyatka==maxVzyatka){
            if (mast<numbInmast) return ++mast;
            else return -1;//todo tyt problem if 2 players will be identical
        }
        return -1;//pass
    }
//ужасный код определения доминирующей масти в руке и возможность участвовать в торгах(колво взяток до опред числа)
    private int analyzeCards(){
        int res = 0;
        int sum = 0;
        int tmp;
        for (int i = 0; i < 10; i++) {
            tmp = razdacha.get(i).getCode();
            if (tmp%10>1 && tmp%10<5) sum++;//колво старших карт от дамы до туза
            if (tmp<15) piki.add(razdacha.get(i));//piki
            if (tmp>16 && tmp<25) trefy.add(razdacha.get(i));//trefy
            if (tmp>26 && tmp<35) bubi.add(razdacha.get(i));//bubi
            if (tmp>36) chervy.add(razdacha.get(i));//chervy
        }
        masti = new ArrayList<>(4);
        masti.add(piki);
        masti.add(trefy);
        masti.add(bubi);
        masti.add(chervy);
        tmp = 0;
        for (int i = 0; i < 3; i++) {
            if (masti.get(i).size()>masti.get(i+1).size()) tmp = i;
        }
        numbInmast = tmp;
        dominateMast = masti.get(tmp);
        if (dominateMast.size()>4) {
            tmp = 0;
            for (int i = 0; i < dominateMast.size(); i++) {
                if (dominateMast.get(i).getCode()%10>2) tmp++;
            }
            if (tmp>1) res = 6+(sum-tmp)/2;
            else res = 5 + (sum - tmp)/2;
        } else res = sum/2;
        res += (int) Math.round(Math.random());//возможность взятки от прикупа
        return res;
    }

    public void takeCard(Card card){
        razdacha.add(card);
    }

    public void dropCard(int i){
        razdacha.remove(i);
    }

    public int findIndMinCard(){
        int min = razdacha.get(0).getCode()%10;
        int indexMin = 0; Card tmp = null;
        for (int i = 0; i < razdacha.size(); i++) {
            tmp = razdacha.get(i);
            if (tmp.getCode()%10<min){
                min = tmp.getCode()%10;
                indexMin = i;
            }
        }
        return indexMin;
    }

    public int findIndMaxCard(){
        int max = razdacha.get(0).getCode()%10;
        int indexMax = 0; Card tmp = null;
        for (int i = 0; i < razdacha.size(); i++) {
            tmp = razdacha.get(i);
            if (tmp.getCode()%10>max){
                max = tmp.getCode()%10;
                indexMax = i;
            }
        }
        return indexMax;
    }
}
