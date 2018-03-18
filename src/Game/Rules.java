package Game;

import Game.API.API;
import Game.ColodaCards.Coloda;
import Game.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rules {
    private int rulePlayer;
    private String gameStrategy;
    private Random random;
    private Player[] players;
    private int winnerMast;
    private int winnerVzyatka;

    public int getWinnerVzyatka() {
        return winnerVzyatka;
    }

    Rules(Player[] players){
        random = new Random();
        this.players = players;
    }

    public int getWinnerMast(){
        return winnerMast;
    }

    public int getRulePlayer(){
        return rulePlayer;
    }

   public String getGameStrategy(){
           return gameStrategy;
   }

   public void reMakePriorityPlayer(int numTakeVzPlayer){
            Player temp;
       for (int i = 0; i < numTakeVzPlayer; i++) {
           for (int j = 0; j < players.length-1; j++) {
               temp = players[j];
               players[j] = players[j + 1];
               players[j + 1] = temp;
           }
       }

   }

    //в массиве будет начинаться с 0 игрока, но он будет перемешиваться в начале игры
    public void makePriorityPlayer(){
        int j;  Player temp;
        for (int i = players.length-1; i >= 1; i--)
        {
            j = random.nextInt(i + 1);
            // обменять значения data[j] и data[i]
            temp = players[j];
            players[j] = players[i];
            players[i] = temp;
        }
    }

    public void makeChoices(API logger){
        boolean flag = false;
        List<Integer> choices = new ArrayList<>(players.length);
        winnerMast = -1; winnerVzyatka = 6;
        rulePlayer = -1; gameStrategy = null;
        int countPass = 0,tmp =0;
        int j = 0;
        while (j<25 && !flag) {
            for (int i = 0; i < players.length && !flag; i++) {
                if (j<1) {
                    choices.add(i, players[i].getChoiceTypeGame(winnerMast, winnerVzyatka,logger));
                }
                tmp = choices.get(i);
                if (tmp!= -1 && j>0) {//перейдёт к другому игроку, если текущий пасс вначале и прошёл 1 круг
                    if (countPass == 2 && tmp>=0) {
                        flag = true;
                        gameStrategy = "vzyatka";
                        rulePlayer = getWinnerTorg(choices);//номер игрока победителя торгов
                        break;
                    }
                    choices.set(i, players[i].getChoiceTypeGame(winnerMast, winnerVzyatka,logger));//после опроса игроков в торгах надо определить:
                    tmp = choices.get(i);//кто их выиграл или пасанул, или ушёл в мизер, или 2виста или пас и полвиста.
                    if (tmp>winnerMast) winnerMast++;
                    if (winnerMast>4){//0-piki;1-trefy;2-bubi;3-chervy;4-bez kozyrya
                        winnerMast =0;//чтоб контролировать переход по "ступеням" взяток
                        winnerVzyatka++;
                        choices.set(i,0);
                    }
                    if (tmp == -1) {//danger
                            if (countPass == 2) {
                            flag=true;
                            gameStrategy = "vzyatka";
                            rulePlayer= getWinnerTorg(choices);//номер игрока победителя торгов
                            //players[rulePlayer].setGameStyle("vzyatka");
                            //logger.torgiInfo(players[rulePlayer],players[rulePlayer].getGameStyle(),numRazd);
                            getVistOrPass(choices);
                        }
                        countPass++;
                    }
                }else if (j<1){
                    if (tmp>winnerMast) winnerMast++;
                    if (winnerMast>4){//0-piki;1-trefy;2-bubi;3-chervy;4-bez kozyrya
                        winnerMast =0;//чтоб контролировать переход по "ступеням" взяток
                        winnerVzyatka++;
                        choices.set(i,0);
                    }
                    if (tmp == -2) { //если на 1 круге сразу мизер
                        gameStrategy= "mizer";
                        players[i].setGameStyle("mizer");
                        while (choices.size()<3) choices.add(-1);
                        for (int k = 0; k < players.length; k++) {
                            if (!players[k].getGameStyle().equals("mizer")) players[k].setGameStyle("pass");
                        }
                        rulePlayer = i;
                        flag=true;
                    }else if (tmp ==-1){//если на 1 круге сразу пасс
                        countPass++;
                        if (countPass == 2) {
                            flag = true;
                            gameStrategy = "vzyatka";
                            if (choices.size()<3) {
                                players[i].setGameStyle(helpSetGameStyle(choices.get(i),winnerVzyatka));
                                logger.torgiInfo(players[i],players[i].getGameStyle());//[[[
                                choices.add(i+1, players[i+1].getChoiceTypeGame(winnerMast, winnerVzyatka,logger));
                                tmp = choices.get(i+1);
                                if (tmp>winnerMast) winnerMast++;
                                else if (tmp==-2) gameStrategy = "mizer";
                                if (winnerMast>4){//0-piki;1-trefy;2-bubi;3-chervy;4-bez kozyrya
                                    winnerMast =0;//чтоб контролировать переход по "ступеням" взяток
                                    winnerVzyatka++;
                                    choices.set(i,0);
                                }
                            }
                            rulePlayer = getWinnerTorg(choices);//номер игрока победителя торгов
                            if (rulePlayer==-1) {
                                countPass++;
                                players[i+1].setGameStyle(helpSetGameStyle(choices.get(i+1),winnerVzyatka));
                                logger.torgiInfo(players[i+1],players[i+1].getGameStyle());
                            }
                        }
                        if (countPass == 3) {
                            flag = true;
                            gameStrategy = "raspasovka";
                        }
                    }
                }
                    players[i].setGameStyle(helpSetGameStyle(choices.get(i), winnerVzyatka));
                    logger.torgiInfo(players[i], players[i].getGameStyle());
                if (j>0 && tmp==-2){
                    getVistOrPass(choices);
                    for (int k = players.length-1; k >=0; k--) {//остпапеми
                        if (k!=rulePlayer) logger.torgiInfo(players[k],players[k].getGameStyle());
                    }
                }
            }
            j++;
        }
        if (gameStrategy.equals("vzyatka")) getVistOrPass(choices);
        resetMaxVzyatka(players);
    }

    private String helpSetGameStyle(int mast,int vzyatka){
        if (mast==-2) return "mizer";
        if (mast==-1) return "pass";
        if (mast>=0) return "vzyatka "+ getStringMast(mast) + vzyatka;
        return "";
    }

    private void getVistOrPass(List<Integer> choices){//-5 это вист; -4 это пасс
        boolean flag = false;
        int tmp = 0;
        players[rulePlayer].setCntVzyatok(winnerVzyatka);
        for (int i = 0; i < players.length; i++) {
            if (i!=rulePlayer) {
                if (gameStrategy.equals("mizer") || gameStrategy.equals("pass")|| gameStrategy.equals("")) {
                    players[i].setCntVzyatok(0);
                    players[i].setGameStyle("pass");
                }
                else {
                    choices.set(i, random.nextInt(1) - 5);
                    if (choices.get(i) == -5) {
                        players[i].setGameStyle("vist");
                        int tmp2 = 10 - players[rulePlayer].getCntVzyatok() / 2;
                        if (tmp2 != 0) players[i].setCntVzyatok(tmp2);
                        else players[i].setCntVzyatok(1);
                        tmp = i;
                    } else {
                        players[i].setGameStyle("pass");
                        flag = true;
                    }
                }
            }
        }
        if (flag){
            players[tmp].setGameStyle("1/2 vist");//polvista
        }
    }

    private int getWinnerTorg(List<Integer> choices){//номер победителя в массиве
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i)!= -1) {
                if (choices.get(i)!=-2) {
                    players[i].setGameStyle(getStringMast(winnerMast) + " " + winnerVzyatka);
                } else players[i].setGameStyle("mizer");
                return i;
            }
        }
        return -1;
    }

    public String getStringMast(int mast){
        switch (mast){
            case 0:
                return Coloda.PIKI;
            case 1:
                return Coloda.TREFY;
            case 2:
                return Coloda.BUBI;
            case 3:
                return Coloda.CHERVY;
                case 4:
                    return "б/к";
                default:
                    return "errorMast!";
        }
    }

    private void resetMaxVzyatka(Player[] players){
        for (int i = 0; i < players.length; i++) {
            players[i].setMaxVzyatka(0);
        }
    }
}
