package Game;

import Game.API.API;
import Game.Players.Dealer;
import Game.Players.Player;

public class Game {
    private Player[] players;
    private Rules rules;
    private API api;
    private StyleGame styleGame;

    public Game(){
        players = new Player[3];
        players[0] = new Player("Север");
        players[1] = new Player("Юг");
        players[2] = new Player("Запад");
        rules = new Rules(players);
        api = new API();
        styleGame = new StyleGame();
    }

    public void play(){
        for (int i = 0; i < 2; i++) {
            Dealer.dealer.reshuffle();
            Dealer.dealer.distributionCards(players);
            Dealer.dealer.incCountRazdach();
            rules.makePriorityPlayer();
            rules.makeChoices();
            styleGame.goStrategy(rules.getGameStrategy());
            //обнулять игру
        }
    }

    private class StyleGame{
        private int rulePlayer;

        private

        void mizer(){
            players[rulePlayer].dropCard(players[rulePlayer].findIndMaxCard());
            players[rulePlayer].dropCard(players[rulePlayer].findIndMaxCard());
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(31));
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(30));
            Hod hod;
            for (int i = 0; i < 10; i++) {
                hod = new Hod();
                for (int j = 0; j < players.length; j++) {
                    hod.setVzyatka(players[i].doHod(hod.getVzyatka(),hod.getCountCards()));
                }
                players[hod.getNumWhoTake()].takeVzyatka(hod.getVzyatka());
            }
        }

        void raspas(){

        }

        void vzyatka(){
            players[rulePlayer].dropCard(players[rulePlayer].findIndMinCard());
            players[rulePlayer].dropCard(players[rulePlayer].findIndMinCard());
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(31));
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(30));
            players[rulePlayer].setRealCntVzyatok(rules.getWinnerVzyatka());
            Dealer.dealer.setMainMast(rules.getStringMast(rules.getWinnerMast()));
            Hod hod;
            for (int i = 0; i < 10; i++) {
                hod = new Hod();
                for (int j = 0; j < players.length; j++) {
                    hod.setVzyatka(players[i].doHod(hod.getVzyatka(),hod.getCountCards()));//todo
                }
                players[hod.getNumWhoTake()].takeVzyatka(hod.getVzyatka());
            }
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
