package Game;

import Game.Players.Dealer;
import Game.Players.Player;

public class Game {

    public Game(){
        Player[] players = new Player[3];
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
        while (true) {
            Dealer.dealer.reshuffle();
            Dealer.dealer.distributionCards(players);

        }
    }

    public void play(){

    }
}
