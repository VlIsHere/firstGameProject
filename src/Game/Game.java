package Game;

import Game.API.API;
import Game.Players.Dealer;
import Game.Players.Player;

public class Game {
    private Player[] players;
    private Rules rules;
    private API api;
    private StyleGame styleGame;
    private int cntRazdach;

    public Game(){
        players = new Player[3];
        players[0] = new Player("Север");
        players[1] = new Player("Юг");
        players[2] = new Player("Запад");
        api = new API(System.in,System.out);
        rules = new Rules(players);
        styleGame = new StyleGame();
    }

    public void play(){
   //     cntRazdach = api.askAboutRazdacha();
        cntRazdach = 1;
        for (int i = 0; i < cntRazdach; i++) {
            Dealer.dealer.reshuffle();
            Dealer.dealer.distributionCards(players);
            rules.makePriorityPlayer();
            api.playersInfo(players,i);
            rules.makeChoices(api,i);
            styleGame.rulePlayer = rules.getRulePlayer();
            styleGame.numbRazd = i;
            styleGame.goStrategy(rules.getGameStrategy());
            //обнулять игру
        }
    }

    private class StyleGame{
        private int rulePlayer;
        private int numbRazd;

        void process(){
            Hod hod;
            int tmp = 0;
            for (int i = 0; i < 10; i++) {
                hod = new Hod();
                for (int j = 0; j < players.length; j++) {
                    hod.setVzyatka(players[j].doHod(hod.getVzyatka(),hod.getCountCards()));
                }
                tmp = hod.getNumWhoTake();
                api.rozygryshInfo(hod,players,numbRazd);
                players[tmp].takeVzyatka(hod.getVzyatka());
                rules.reMakePriorityPlayer(tmp);
            }
        }

        void mizer(){
            players[rulePlayer].dropCard(players[rulePlayer].findIndMaxCard());
            players[rulePlayer].dropCard(players[rulePlayer].findIndMaxCard());
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(31));
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(30));
            process();
        }

        void raspas(){
            process();
        }

        void vzyatka(){
            players[rulePlayer].dropCard(players[rulePlayer].findIndMinCard());
            players[rulePlayer].dropCard(players[rulePlayer].findIndMinCard());
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(31));
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(30));
            players[rulePlayer].setCntVzyatok(rules.getWinnerVzyatka());
            Dealer.dealer.setMainMast(rules.getStringMast(rules.getWinnerMast()));
            process();
            Dealer.dealer.dropMainMast();
        }

        void goStrategy(String gameStrategy){
            switch (gameStrategy){
                case "mizer":
                    mizer();
                    break;
                case "raspasovka":
                    raspas();
                    break;
                case "vzyatka":
                    vzyatka();
                    break;
                default:
                    break;
            }
        }
    }
}
