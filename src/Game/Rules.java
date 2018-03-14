package Game;

import Game.ColodaCards.Coloda;
import Game.Players.Dealer;
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

    Rules(Player[] players){
        random = new Random();
        this.players = players;
    }

   public String getGameStrategy(){
           return gameStrategy;
   }

   public void goStrategy(Dealer dealer){
        switch (gameStrategy){
            case "mizer":
                mizer();
                break;
            case "raspasovka":
                raspas();
                break;
            case "vzyatka":
                vzyatka(dealer);
                break;
                default:
                    break;
        }
   }

   public void mizer(){
       for (int i = 0; i < 10; i++) {
           for (int j = 0; j < players.length; j++) {
               
           }
       }
   }

   public void raspas(){

   }

   public void vzyatka(Dealer dealer){
        players[rulePlayer].dropCard(players[rulePlayer].findMinCard());
       players[rulePlayer].dropCard(players[rulePlayer].findMinCard());
       players[rulePlayer].takeCard(dealer.giveCard(31));
       players[rulePlayer].takeCard(dealer.giveCard(30));
       dealer.setMainMast(getStringMast(winnerMast));
       for (int i = 0; i < 10; i++) {
           Hod hod = new Hod();
           for (int j = 0; j < players.length; j++) {
               hod.setVzyatka(players[i].doHod());
           }
       }

       dealer.dropMainMast();
   }

    //в массиве будет начинаться с 0 игрока, но он будет перемешиваться в начале игры
    public void makePriority(){
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

    public void makeChoices(){
        boolean flag = false;
        List<Integer> choices = new ArrayList<>(players.length);
        winnerMast = 0; winnerVzyatka = 6; int countPass = 0;
        while (!flag) {
            for (int i = 0; i < players.length && !flag; i++) {
                if (choices.get(i)!= -1) {//перейдёт к другому игроку, если текущий пасс
                    choices.set(i, players[i].getChoiceTypeGame(winnerMast, winnerVzyatka));//после опроса игроков в торгах надо определить:
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
                        } else if (countPass == 2) {
                            flag=true;
                            gameStrategy = "vzyatka";
                            rulePlayer= getWinnerTorg(choices);//номер игрока победителя торгов
                            getVistOrPass(choices);
                        }
                    }
                }
            }
        }
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

    private String getStringMast(int mast){
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

    private class Calc{
        
    }
}
