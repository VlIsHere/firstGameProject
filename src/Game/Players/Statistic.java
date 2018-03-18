package Game.Players;

public final class Statistic {
    private static String nameConvention = "Sochinka";

    //запись имен игроков под соотв вистом
    public static void setPlayers(Player[] players){
        players[0].setVists(players[1].getName(),players[2].getName());
        players[1].setVists(players[0].getName(),players[2].getName());
        players[2].setVists(players[0].getName(),players[1].getName());
    }

    public static int getNumWinnerPlayerByStat(Player[] players){
        return 0;
    }

    public static void calcVzyatka(int rulePlayer,Player[] players) {
        Player tmp = players[rulePlayer];
        int koef = choiceCalcFromVzyat(tmp.getCntVzyatok());
        if (tmp.getVzyatkiSize()>=tmp.getCntVzyatok()){//vzyatka proshla!
            tmp.setPulya(tmp.getPulya() +koef);
            for (int i = 0; i < players.length; i++) {
                if (i!=rulePlayer) {
                    Player tmp2 = players[i];
                    tmp2.setGora(tmp2.getGora() + (tmp2.getCntVzyatok() - tmp2.getVzyatkiSize()) * koef);
                    for (int j = 0; j < tmp2.getVisty().length; j++) {
                        if (tmp2.getVisty()[j].getNamePlForVist().equals(tmp.getName())) {
                            tmp2.setVistsCount(j, tmp2.getVisty()[j].getCount() + tmp2.getVisty()[j].getCount() * koef);
                        }
                    }
                }
            }
        }else {
            int raznica = tmp.getCntVzyatok() - tmp.getVzyatkiSize();
            for (int i = 0; i < raznica; i++) {//в гору столько, сколько недобрал
                tmp.setGora(tmp.getGora()+koef);
            }
            for (int i = 0; i < players.length; i++) {//вистующим висты на проигравшего
                if (i!=rulePlayer){
                    Player tmp2 = players[i];
                    for (int j = 0; j < tmp2.getVisty().length; j++) {
                        if (tmp2.getVisty()[j].getNamePlForVist().equals(tmp.getName())){
                            tmp2.setVistsCount(j,tmp2.getVisty()[j].getCount()+(tmp2.getVzyatkiSize()+raznica)*koef);
                        }
                    }
                }
            }
        }
    }

    public static void calcMizer(int rulePlayer, Player[] players) {
        Player tmp = players[rulePlayer];
        if (tmp.getVzyatkiSize()>0) tmp.setGora(tmp.getGora()+10*tmp.getVzyatkiSize());
        else tmp.setPulya(tmp.getPulya() + 10);
    }

    public static void calcRaspas(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getVzyatkiSize()==0) players[i].setPulya(players[i].getPulya()+10);
            players[i].setGora(players[i].getGora() + players[i].getVzyatkiSize()*2);
        }
    }

    private static int choiceCalcFromVzyat(int vzyatka){
        switch (vzyatka){
            case 6:
                return 2;
            case 7:
                return 4;
            case 8:
                return 6;
            case 9:
                return 8;
                case 10:
                return 10;
            default:
                return -1;
        }
    }
}
