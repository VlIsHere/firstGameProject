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
           temp = players[i];
           players[i] = players[i+1];
           players[i+1] = temp;
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

    public void makeChoices(API logger,int numRazd){
        boolean flag = false;
        List<Integer> choices = new ArrayList<>(players.length);
        winnerMast = 0; winnerVzyatka = 6;
        int countPass = 0;
        while (!flag) {
            for (int i = 0; i < players.length && !flag; i++) {
                if (choices.size()<3) {
                    choices.add(i, players[i].getChoiceTypeGame(winnerMast, winnerVzyatka,logger,numRazd));//todo все выбирают пасс даже с норм взятками!
                }
                if (choices.get(i)!= -1) {//перейдёт к другому игроку, если текущий пасс
                    choices.set(i, players[i].getChoiceTypeGame(winnerMast, winnerVzyatka,logger,numRazd));//после опроса игроков в торгах надо определить:
                    if (choices.get(i) == -2) { //кто их выиграл или пасанул, или ушёл в мизер, или 2виста или пас и полвиста.
                        gameStrategy= "mizer";
                        players[i].setGameStyle("mizer");
                        flag=true;
                    }
                    if (choices.get(i) == -1) {
                        countPass++;
                        if (countPass == 3) {
                            flag=true;
                            gameStrategy = "raspasovka";
                            for (int j = 0; j < players.length; j++) {
                                players[i].setGameStyle("pass");
                            }
                        } else if (countPass == 2 && choices.get(i)>=0) {
                            flag=true;
                            gameStrategy = "vzyatka";
                            rulePlayer= getWinnerTorg(choices);//номер игрока победителя торгов
                            //players[rulePlayer].setGameStyle("vzyatka");
                            //logger.torgiInfo(players[rulePlayer],players[rulePlayer].getGameStyle(),numRazd);
                            getVistOrPass(choices);
                        }
                    }
                }else {
                    countPass++;
                    if (countPass == 3) {
                        flag = true;
                        gameStrategy = "raspasovka";
                        for (int j = 0; j < players.length; j++) {
                            players[i].setGameStyle("pass");
                        }
                    }
                }
                players[i].setGameStyle(helpSetGameStyle(choices.get(i)));
                logger.torgiInfo(players[i],players[i].getGameStyle(),numRazd);
            }
        }
    }

    private String helpSetGameStyle(int numb){
        if (numb==-2) return "mizer";
        if (numb==-1) return "pass";
        if (numb>=0) return "vzyatka "+ numb;
        return "";
    }

    private void getVistOrPass(List<Integer> choices){//-5 это вист; -4 это пасс
        for (int i = 0; i < players.length; i++) {
            if (i!=rulePlayer) {
                choices.set(i,random.nextInt(1)-5);
                if (choices.get(i)==-5) players[i].setGameStyle("вист");
                else players[i].setGameStyle("pass");
            }
        }
    }

    private int getWinnerTorg(List<Integer> choices){//номер победителя в массиве
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i)!= -1) {
                players[i].setGameStyle(getStringMast(winnerMast) + " " + winnerVzyatka);
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
            case 3:
                return Coloda.BUBI;
            case 4:
                return Coloda.CHERVY;
                default:
                    return "errorMast!";
        }
    }
}
