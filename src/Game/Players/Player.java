package Game.Players;

import Game.API.API;
import Game.ColodaCards.Card;

import java.util.*;

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
    private int cntVzyatok;//взятки, которые игрок должен взять, если он выиграл торги
    private int realCntVzyatok;
    private Map<Integer,List<Card[]>> vzyatInRazd;
    private List<Card[]> vzyatki;
    private String styleGame;
    private Vist[] visty;
    private int gora;
    private int pulya;

    public List<Card[]> getVzyatkiByNumRazd(int num){
        return vzyatInRazd.get(num);
    }

    public Vist[] getVisty() {
        return visty;
    }

    public String vistsToString(){
        String s = "";
        for (int i = 0; i < visty.length; i++) {
            s+="\nВисты на " + visty[i].toString();
        }
        return s;
    }

    public void setVistsCount(int numb,int count) {
        this.visty[numb].setCount(count);
    }

    public int getGora() {
        return gora;
    }

    public void setGora(int gora) {
        this.gora = gora;
    }

    public int getPulya() {
        return pulya;
    }

    public void setPulya(int pulya) {
        this.pulya = pulya;
    }

    public void setStyleGame(String styleGame){
        this.styleGame = styleGame;
    }

    public void setVists(String name1Vist, String name2Vist){//висты на др игроков
        getVisty()[0].setNamePlForVist(name1Vist);
        getVisty()[1].setNamePlForVist(name2Vist);
    }

    public void setMaxVzyatka(int numb){
        maxVzyatka = numb;
    }

    public int getVzyatkiSize(){return vzyatki.size();}

    public String getGameStyle(){return gameStyle;}

    public int sizeRazdacha(){return razdacha.size();}

    public String getName(){return name;}

    public void setCntVzyatok(int cntVzyatok) {
        this.cntVzyatok = cntVzyatok;
    }

    public int getCntVzyatok() {
        return cntVzyatok;
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
       masti = new ArrayList<>(4);
       gameStyle = "";
       vzyatki = new LinkedList<>();
       vzyatInRazd = new HashMap<>();
       realCntVzyatok = 0;
        visty = new Vist[2];
        visty[0] = new Vist();
        visty[1] = new Vist();
    }

    public List<Card[]> getVzyatki(){
        return vzyatki;
    }

    public void resetVzyatki(){
        vzyatki = new LinkedList<>();
    }

    public Card doHod(Card[] cardsShown,int countCardsOpened){
        Card res; int indexRes;
        if (gameStyle.equals("mizer") || gameStyle.equals("pass") || gameStyle.equals("")){
            if (countCardsOpened==0){
                indexRes = findIndMinCard();
            }else if (countCardsOpened==1){
                indexRes = getCardSmallerThis(cardsShown[0]);
                if (indexRes==-1) indexRes = findIndMaxCard();
            }else {
                indexRes = cardsShown[0].compareTo(cardsShown[1])<0?
                        getCardSmallerThis(cardsShown[0]):getCardSmallerThis(cardsShown[1]);
                if (indexRes==-1) indexRes = findIndMaxCard();
            }
        }else {
            if (cntVzyatok>realCntVzyatok){
                if (countCardsOpened==0){
                    indexRes = findIndMaxCard();
                }else if (countCardsOpened==1){
                    indexRes = getCardBiggerThis(cardsShown[0]);
                    if (indexRes==-1) indexRes = findIndMinCard();
                }else {
                    indexRes = cardsShown[0].compareTo(cardsShown[1])>0?
                            getCardBiggerThis(cardsShown[0]):getCardBiggerThis(cardsShown[1]);
                    if (indexRes==-1) indexRes = findIndMinCard();
                }
            }else {
                indexRes = findIndMaxCard();
            }
        }
        res = razdacha.get(indexRes);
        razdacha.remove(indexRes);
        return res;
    }

    public void takeVzyatka(Card[] cards){
        vzyatki.add(cards);
    }

    public void putVzyatkiInRazdacha(int numRazd,List<Card[]> vzyatki){
        vzyatInRazd.put(numRazd,vzyatki);
    }

    private int getCardBiggerThis(Card card){
        for (int i = 0; i < razdacha.size(); i++) {
            if (razdacha.get(i).compareTo(card)>0 && razdacha.get(i).getMast().equals(card.getMast())){
                return i;
            }
        }//если нет по масти карты старше - выбросим какую есть
        for (int i = 0; i < razdacha.size(); i++) {
            if (razdacha.get(i).getMast().equals(card.getMast())){
                return i;
            }
        }
        //если нет такой масти, выбрасываем козырь
        if (dominateMast!= null) {
            for (int i = 0; i < razdacha.size(); i++) {
                if (razdacha.get(i).getMast().equals(dominateMast.get(0).getMast())) {
                    return i;
                }
            }
        }//нет козырей - просто random
        return -1;
    }

    private int getCardSmallerThis(Card card){
        for (int i = 0; i < razdacha.size(); i++) {
            if (razdacha.get(i).compareTo(card)<0 && razdacha.get(i).getMast().equals(card.getMast())){
                return i;
            }
        }
        //если нет по масти карты младше - выбросим какую есть
        for (int i = 0; i < razdacha.size(); i++) {
            if (razdacha.get(i).getMast().equals(card.getMast())){
                return i;
            }
        }
        //если нет такой масти, выбрасываем козырь, если он есть
        if (dominateMast!= null) {
            for (int i = 0; i < razdacha.size(); i++) {
                if (razdacha.get(i).getMast().equals(dominateMast.get(0).getMast())) {
                    return i;
                }
            }
        }//нет козырей - просто random
        return -1;
    }

    public int getChoiceTypeGame(int mast,int oldMaxVzyatka,API api){
        Random rnd = new Random();
        if (maxVzyatka ==0) {
            maxVzyatka = analyzeCards();
            api.infoAnalyzeTorgi(this,dominateMast.size(),dominateMast.get(0).getMast(),maxVzyatka);
            if (maxVzyatka<6) {
                if (maxVzyatka<4) return -2;
                else return -1;
            }
            else {
                return ++mast;
            }
        }
        else if (oldMaxVzyatka<maxVzyatka) {
            if (mast==numbInmast && rnd.nextInt(1)==0) return mast;
            else return ++mast;
        }
        else if (oldMaxVzyatka==maxVzyatka){
            if (mast<numbInmast) return ++mast;
            else return -1;
        }
        return -1;//pass
    }

//код определения доминирующей масти в руке и возможность участвовать в торгах(колво взяток до опред числа)
    private int analyzeCards(){
        cntVzyatok = 0;
        piki.clear();
        trefy.clear();
        bubi.clear();
        chervy.clear();
        int res;
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
        masti.add(piki);
        masti.add(trefy);
        masti.add(bubi);
        masti.add(chervy);
        dominateMast = Collections.max(masti, Comparator.comparingInt(ArrayList::size));
        numbInmast = masti.indexOf(dominateMast);
        if (dominateMast.size()>=4) {
            tmp = 0;
            for (int i = 0; i < dominateMast.size(); i++) {
                if (dominateMast.get(i).getCode()%10>2) tmp++;
            }
            if (tmp>1) res = 6+(sum-tmp)/2;
            else res = 5 + (sum - tmp)/2;
        } else res = sum/2+1;
        res += (int) Math.round(Math.random());//возможность взятки от прикупа
        return res;
    }

    public Card getCard(int i){return razdacha.get(i);}

    public void takeCard(Card card){
        razdacha.add(card);
    }

    public void dropCard(int i){
        razdacha.remove(i);
    }

    public int findIndMinCard(){
        int min = razdacha.get(0).getCode();
        int indexMin = 0; Card tmp;
        for (int i = 0; i < razdacha.size(); i++) {
            tmp = razdacha.get(i);
            if (tmp.getCode()<min && tmp.getCode()%10<min%10){
                min = tmp.getCode();
                indexMin = i;
            }
        }
        return indexMin;
    }

    public int findIndMaxCard(){
        int max = razdacha.get(0).getCode();
        int indexMax = 0; Card tmp;
        for (int i = 0; i < razdacha.size(); i++) {
            tmp = razdacha.get(i);
            if (tmp.getCode()>max && tmp.getCode()%10>max%10){
                max = tmp.getCode();
                indexMax = i;
            }
        }
        return indexMax;
    }
}
