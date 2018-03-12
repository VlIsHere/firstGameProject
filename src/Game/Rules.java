package Game;

import Game.Players.Player;

import java.util.Random;

public class Rules {
    private int rulePlayer;
    private String gameStrategy;
    private Random random;

    Rules(){
        random = new Random();
    }

    //в массиве будет начинаться с 0 игрока, но он будет перемешиваться в начале игры
    public void makePriority(Player[] players){
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

    public String makeChoices(Player[] players){
        boolean flag = false;
        int condition = 0;
        int[] choices = new int[players.length];
        while (!flag) {
            for (int i = 0; i < players.length; i++) {
               if ((choices[i] = players[i].getChoiceTypeGame(condition, random)) == 26){
                   return "mizer";
               }
            }

        }

        return null;
    }
}
