package Game;

import Game.API.API;
import Game.Players.*;

public class Game {
    private Player[] players;
    private Rules rules;
    private API api;
    private StyleGame styleGame;
    private int cntRazdach;
    private Statistic statistic;
    public static int currRazdacha;

    public Game(int cntRazdach){
        this.cntRazdach = cntRazdach;
        players = new Player[3];
        players[0] = new Player("Север");
        players[1] = new Player("Юг");
        players[2] = new Player("Запад");
        Statistic.setPlayers(players);
        api = new API(System.in,System.out);
        rules = new Rules(players);
        styleGame = new StyleGame();
    }

    public void play(){
        int winnerTorg;
        System.out.println("Процесс игры");
        cntRazdach = api.askAboutRazdacha();
        for (int i = 0; i < cntRazdach; i++) {
            currRazdacha = i;
            Dealer.dealer.reshuffle();
            Dealer.dealer.distributionCards(players);
            rules.makePriorityPlayer();
            api.playersInfo(players);
            System.out.println("Торги:");
            rules.makeChoices(api);
            styleGame.rulePlayer = rules.getRulePlayer();
            winnerTorg = styleGame.rulePlayer;
            if (winnerTorg==-1) System.out.println("РАСПАСОВКА");
            else System.out.println("В торгах победил: " + players[styleGame.rulePlayer].getName());
            styleGame.goStrategy(rules.getGameStrategy());
            api.resPlayers(players);
            api.resPlayersKoef(players);
            api.resultsRazdachi(players);
            api.resPlayers(players);
        }
        System.out.println("-------------------------Вызов моих API-------------------------------");
        System.out.println("API1-------------------");
        System.out.println(api.getPlayers(2));
        api.getPlayers(5);
        System.out.println("API2-------------------");
        System.out.println(api.getInfoAnalyzeTorgi(4));
        System.out.println("API3-------------------");
        System.out.println(api.getTorgi(3));
        System.out.println("API4-------------------");
        System.out.println(api.getRozygryshInfo(7));
        System.out.println("API5-------------------");
        System.out.println(api.getResultsRazdachi(6));
        System.out.println("API7-------------------");
        System.out.println(api.getResPlayers(8,0));
        System.out.println("API8-------------------");
        System.out.println(api.getResPlayersKoef(9,2));
        System.out.println("---------------------------API6-----------------------------------------");
        for (int i = 0; i < cntRazdach; i++) {
            System.out.println(api.getAllRazdacha(i));
        }
    }

    private class StyleGame{
        private int rulePlayer;

        void process(){
            Hod hod;
            int tmp;
            for (int i = 0; i < 10; i++) {
                hod = new Hod();
                for (int j = 0; j < players.length; j++) {
                    hod.setVzyatka(players[j].doHod(hod.getVzyatka(),hod.getCountCards()));
                }
                System.out.println("hod " + i);
                tmp = hod.getNumWhoTake();
                players[tmp].takeVzyatka(hod.getVzyatka());
                api.rozygryshInfo(hod,players);
                rules.reMakePriorityPlayer(tmp);
            }
        }

        private void addVzyatkiToPlayers(Player[] players){
            for (int i = 0; i < players.length; i++) {
                players[i].putVzyatkiInRazdacha(Game.currRazdacha,players[i].getVzyatki());
                players[i].resetVzyatki();
            }
        }

        void mizer(){
            players[rulePlayer].dropCard(players[rulePlayer].findIndMaxCard());
            players[rulePlayer].dropCard(players[rulePlayer].findIndMaxCard());
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(31));
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(30));
            process();
            Statistic.calcMizer(rulePlayer,players);
            addVzyatkiToPlayers(players);
        }

        void raspas(){
            process();
            Statistic.calcRaspas(players);
            addVzyatkiToPlayers(players);
        }

        void vzyatka(){
            players[rulePlayer].dropCard(players[rulePlayer].findIndMinCard());
            players[rulePlayer].dropCard(players[rulePlayer].findIndMinCard());
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(31));
            players[rulePlayer].takeCard(Dealer.dealer.giveCard(30));
            players[rulePlayer].setCntVzyatok(rules.getWinnerVzyatka());
            Dealer.dealer.setMainMast(rules.getStringMast(rules.getWinnerMast()));
            printStyleGamePlayers();
            process();
            Statistic.calcVzyatka(rulePlayer,players);
            addVzyatkiToPlayers(players);
            Dealer.dealer.dropMainMast();
        }

        void printStyleGamePlayers(){
            for (Player pl: players) {
                api.torgiInfo(pl,pl.getGameStyle());
            }
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
