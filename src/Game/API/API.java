package Game.API;

import Game.ColodaCards.Card;
import Game.Game;
import Game.Hod;
import Game.Players.Dealer;
import Game.Players.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class API {
    private Scanner reader;
    private PrintWriter writer;
    private StringBuffer sb;
    private ArrayList<StringBuffer> razdachi;
    private ArrayList<StringBuffer> torgi;
    private ArrayList<StringBuffer> analyzeTorgi;
    private ArrayList<StringBuffer> rozygrysh;
    private ArrayList<StringBuffer> resultsRazdachi;
    private ArrayList<StringBuffer> allRazdacha;
    private ArrayList<StringBuffer[]> resPlayers;
    private final Pattern pattern = Pattern.compile("^[1-9]$");

    public API (InputStream is, OutputStream os){
        reader = new Scanner(is);
        writer = new PrintWriter(os,true);
        sb = new StringBuffer();
        razdachi = new ArrayList<>();
        torgi = new ArrayList<>();
        analyzeTorgi = new ArrayList<>();
        rozygrysh = new ArrayList<>();
        resultsRazdachi = new ArrayList<>();
        allRazdacha = new ArrayList<>();
        resPlayers = new ArrayList<>();
    }

    private boolean checkNumb(String s){
        Matcher m = pattern.matcher(s);
        return m.matches();
    }

    private void inputInSB(String s){
        sb.append(s).append("\n");
    }
    private void inputInSB2(String s){
        sb.append(s);
    }

    private void inputInSB(String s,int numb){
        razdachi.get(numb).append(s).append("\n");
    }
    private void inputInSB2(String s,int numb){
        razdachi.get(numb).append(s);
    }

    public int  askAboutRazdacha(){
        int numb;
        writer.print("Введите количество раздач: ");
        writer.flush();
        String s = reader.nextLine();
        while (!checkNumb(s)) {
            writer.println("Неверно! Надо ввести число от 1 до 9!");
            s = reader.nextLine();
        }
        inputInSB("количество раздач: ");
        inputInSB(s);
        numb = Integer.parseInt(s);
        return numb;
    }

    private void println(String s){
        writer.println(s);
        inputInSB(s);
    }
    private void print(String s){
        writer.print(s);
        inputInSB2(s);
    }

    private void addSBAll(){
        rozygrysh.add(new StringBuffer());
        analyzeTorgi.add(new StringBuffer());
        torgi.add(new StringBuffer());
        razdachi.add(new StringBuffer());
        resultsRazdachi.add(new StringBuffer());
        allRazdacha.add(new StringBuffer());

        resPlayers.add(new StringBuffer[3]);
        resPlayers.get(Game.currRazdacha)[0] = new StringBuffer();
        resPlayers.get(Game.currRazdacha)[1] = new StringBuffer();
        resPlayers.get(Game.currRazdacha)[2] = new StringBuffer();
    }
//api1
    public StringBuffer getPlayers(int numRazd) {
        return razdachi.get(numRazd);
    }

    public void playersInfo(Player[] players){
        addSBAll();//initall
        println("№ раздачи: " +Game.currRazdacha);
        inputInSB("№ раздачи: " + Game.currRazdacha,Game.currRazdacha);
        for (int i = 0; i < players.length; i++) {
            println((i+1) + "-ая рука - " + players[i].getName());
            inputInSB((i+1) + "-ая рука - " + players[i].getName(),Game.currRazdacha);
            println("Его карты: ");
            inputInSB("Его карты: ",Game.currRazdacha);
            for (int j = 0; j < players[i].sizeRazdacha(); j++) {
                print(players[i].getCard(j) + " ");
                inputInSB2(players[i].getCard(j) + " ",Game.currRazdacha);
            }
            println("");
            inputInSB2("",Game.currRazdacha);
        }
        println("В прикупе: " + Dealer.dealer.giveCard(30) + " " + Dealer.dealer.giveCard(31));
        inputInSB2("В прикупе: " + Dealer.dealer.giveCard(30) + " " + Dealer.dealer.giveCard(31),Game.currRazdacha);
    }
//api3
    public StringBuffer getTorgi(int numRazd){
        return torgi.get(numRazd).insert(0,"№ раздачи: " + numRazd);
    }

    public void torgiInfo(Player player,String hisChoice){
         println(player.getName() + " выбрал " + hisChoice);
         torgi.get(Game.currRazdacha).append(player.getName() + " выбрал " + hisChoice);
    }
//api2
    public StringBuffer getInfoAnalyzeTorgi(int numRazd){
        return analyzeTorgi.get(numRazd).insert(0,"№ раздачи: " + numRazd+ "\n"+ "прикуп: "
                                            + Dealer.dealer.giveCard(30) + " "
                                            + Dealer.dealer.giveCard(31));
    }

    public void infoAnalyzeTorgi(Player player,int dMastSize,String mast , int cntVzyatok){
        println(player.getName() + " рассчитал, что при козыре " + mast + " возможно взять " + cntVzyatok + " взяток");
        analyzeTorgi.get(Game.currRazdacha).append(player.getName() + " рассчитал, что при козыре " + mast + " возможно взять " + cntVzyatok + " взяток");
    }

    //api4
    public StringBuffer getRozygryshInfo(int numRazd){
        return rozygrysh.get(numRazd).insert(0,"№ раздачи: " + numRazd+ "\n");
    }

    public void rozygryshInfo(Hod hod, Player[] players) {
        for (int i = 0; i < hod.getCountCards(); i++) {
            println(players[i].getName() + " сделал ход " + i + " картой " + hod.getVzyatka()[i] +
                    "(Он имеет " + players[i].getVzyatkiSize() + " взяток(-тки))");
            rozygrysh.get(Game.currRazdacha).append(players[i].getName() + " сделал ход " + i +
                    " картой " + hod.getVzyatka()[i] + "(Он имеет " + players[i].getVzyatkiSize() + " взяток(-тки))");
        }
    }
//api5
//Метод получения данных о результатах розыгрыша определенной раздачи
//(кто сколько взяток взял, какие цифры записаны в пулю, гору и висты).
    public StringBuffer getResultsRazdachi(int numRazd){
        return resultsRazdachi.get(numRazd).insert(0,"№ раздачи: " + numRazd+ "\n");
    }

    public void resultsRazdachi(Player[] players){
        for (int i = 0; i < players.length; i++) {
            Player pl = players[i];
            resultsRazdachi.get(Game.currRazdacha).append(pl.getName() + " взял взяток " + pl.getVzyatkiByNumRazd(Game.currRazdacha).size() +
                    "; Пуля: " + pl.getPulya() + "\nГора:" + pl.getGora() + "\nВисты: " + pl.vistsToString());
        }
    }
//api 6
//Метод получения данных о полном процессе розыгрыша определенной
//раздачи (раздача, торговля, заявка, игра, результаты). Номер раздачи
    public StringBuffer getAllRazdacha(int numRazd){
        allRazdacha.get(numRazd).insert(0,"№ раздачи: " + numRazd+ "\n");
        allRazdacha.get(numRazd).append(razdachi.get(numRazd)).append("\n"+torgi.get(numRazd) + "\n");
        allRazdacha.get(numRazd).append(analyzeTorgi.get(numRazd)).append("\n"+rozygrysh.get(numRazd) + "\n");
        allRazdacha.get(numRazd).append(resultsRazdachi.get(numRazd) + "\n");
        return allRazdacha.get(numRazd);
    }
    //api 7
//Метод получения данных о текущем состоянии пули, горы и вистах игрока
//после определенной раздачи. Номер раздачи и идентификатор игрока
//должны быть вынесены в параметры метода.
    public StringBuffer getResPlayers(int numRazd,int idPlayer){
        return resPlayers.get(numRazd)[idPlayer];
    }

    public void resPlayers(Player[] pls){
        for (int i = 0; i < pls.length; i++) {
            resPlayers.get(Game.currRazdacha)[i].append(pls[i].getName() + " взял взяток " + pls[i].getVzyatkiByNumRazd(Game.currRazdacha).size()
                    + "; Пуля: " + pls[i].getPulya() + "\nГора:" + pls[i].getGora() + "\nВисты: " + pls[i].vistsToString());
        }
    }

    //api8
//    Метод получения промежуточного результата игрока после определенной
//    раздачи. Результат работы метода – определенное число, показывающее
//    как успешно играет игрок (определяется исходя из состояния пули, горы и
//вистов всех игроков, как если бы игра была прекращена после указанной
//раздачи и надо было бы подсчитывать результаты).
    public StringBuffer getResPlayersKoef(int numRazd,int idPlayer){
        return resPlayers.get(numRazd)[idPlayer];
    }

    public void resPlayersKoef(Player[] pls){
        int koef = 0;
        int sumvist = 0;
        for (int i = 0; i < pls.length; i++) {
            koef = pls[i].getPulya()-pls[i].getGora();
            for (int j = 0; j < pls[i].getVisty().length; j++) {
                sumvist+=pls[i].getVisty()[j].getCount();
            }
            resPlayers.get(Game.currRazdacha)[i].append(pls[i].getName() + " " + (koef+sumvist/10));
        }
    }
}
