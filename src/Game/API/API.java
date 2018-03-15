package Game.API;

import Game.ColodaCards.Card;
import Game.Hod;
import Game.Players.Dealer;
import Game.Players.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final Pattern pattern = Pattern.compile("^[1-9]$");

    public API (InputStream is, OutputStream os){
        reader = new Scanner(is);
        writer = new PrintWriter(os,true);
        sb = new StringBuffer();
        razdachi = new ArrayList<>();
        torgi = new ArrayList<>();
        analyzeTorgi = new ArrayList<>();
        rozygrysh = new ArrayList<>();
    }

    private boolean checkNumb(String s){
        Matcher m = pattern.matcher(s);
        return m.matches();
    }

    public void inputInSB(String s){
        sb.append(s + "\n");
    }
    public void inputInSB2(String s){
        sb.append(s);
    }

    public void inputInSB(String s,int numb){
        razdachi.get(numb).append(s+"\n");
    }
    public void inputInSB2(String s,int numb){
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

    public void println(String s){
        writer.println(s);
        inputInSB(s);
    }
    public void print(String s){
        writer.print(s);
        inputInSB2(s);
    }
//api1
    public StringBuffer getPlayers(int numb) {
        return razdachi.get(numb);
    }

    public void playersInfo(Player[] players,int numRazdachi){
        rozygrysh.add(new StringBuffer());
        analyzeTorgi.add(new StringBuffer());
        torgi.add(new StringBuffer());
        razdachi.add(new StringBuffer());
        println("№ раздачи: " + numRazdachi);
        inputInSB("№ раздачи: " + numRazdachi,numRazdachi);
        for (int i = 0; i < players.length; i++) {
            println((i+1) + "-ая рука - " + players[i].getName());
            inputInSB((i+1) + "-ая рука - " + players[i].getName(),numRazdachi);
            println("Его карты: ");
            inputInSB("Его карты: ",numRazdachi);
            for (int j = 0; j < players[i].sizeRazdacha(); j++) {
                print(players[i].getCard(j) + " ");
                inputInSB2(players[i].getCard(j) + " ",numRazdachi);
            }
            println("");
            inputInSB2("",numRazdachi);
        }
        println("В прикупе: " + Dealer.dealer.giveCard(30) + " " + Dealer.dealer.giveCard(31));
        inputInSB2("В прикупе: " + Dealer.dealer.giveCard(30) + " " + Dealer.dealer.giveCard(31),numRazdachi);
    }
//api3
    public StringBuffer getTorgi(int numb){
        return torgi.get(numb).insert(0,"№ раздачи: " + numb);
    }

    public void torgiInfo(Player player,String hisChoice,int numRazd){
         println(player.getName() + " выбрал " + hisChoice);
         torgi.get(numRazd).append(player.getName() + " выбрал " + hisChoice);
    }
//api2
    public StringBuffer getInfoAnalyzeTorgi(int numb){
        return analyzeTorgi.get(numb).insert(0,"№ раздачи: " + numb+ "\n"+ "прикуп: "
                                            + Dealer.dealer.giveCard(30) + " "
                                            + Dealer.dealer.giveCard(31));
    }

    public void infoAnalyzeTorgi(Player player,int dMastSize, Card card, int cntVzyatok, int numRazd){
        println(player.getName() + " рассчитал, что при козыре " + card.getMast() + " возможно взять " + cntVzyatok + " взяток");
        analyzeTorgi.get(numRazd).append(player.getName() + " рассчитал, что при козыре " + card.getMast() + " возможно взять " + cntVzyatok + " взяток");
    }

    //api4
    public StringBuffer getRozygryshInfo(int numb){
        return rozygrysh.get(numb).insert(0,"№ раздачи: " + numb+ "\n");
    }

    public void rozygryshInfo(Hod hod, Player[] players,int numRazd) {
        for (int i = 0; i < hod.getCountCards(); i++) {
            println(players[i].getName() + " сделал ход " + i + " картой " + hod.getVzyatka()[i] + "(Он имеет " + players[i].getVzyatkiSize() + " взяток)");
            rozygrysh.get(numRazd).append(players[i].getName() + " сделал ход " + i + " картой " + hod.getVzyatka()[i] + "(Он имеет " + players[i].getVzyatkiSize() + " взяток)");
        }
    }
//api5
//Метод получения данных о результатах розыгрыша определенной раздачи
//    (кто сколько взяток взял, какие цифры записаны в пулю, гору и висты).
//    Номер раздачи должен быть вынесен в параметры метода. Результаты
//    работы метода должны быть выданы на экран/записаны в файл
    public StringBuffer get(Player[] players,int numRazd){
        return null;
    }
}
