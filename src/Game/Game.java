package Game;

import Game.Players.Dealer;
import Game.Players.Player;

public class Game {
    Player[] players;
    Rules rules;

    public Game(){
        players = new Player[3];
        players[0] = new Player("Север");
        players[1] = new Player("Юг");
        players[2] = new Player("Запад");
        rules = new Rules();
    }

    public void play(){
        for (int i = 0; i < 2; i++) {
            Dealer.dealer.reshuffle();
            Dealer.dealer.distributionCards(players);
            rules.makePriority(players);

        }
    }
}
