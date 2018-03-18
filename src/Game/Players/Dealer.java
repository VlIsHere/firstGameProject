package Game.Players;

import Game.ColodaCards.Card;
import Game.ColodaCards.Coloda;
import java.util.Date;
import java.util.Random;

public class Dealer {
    private Coloda coloda;
    private String mainMast;
    public static Dealer dealer = new Dealer();

    private Dealer(){
        coloda = new Coloda();
    }

    public Card giveCard(int i){
        return coloda.getCard(i);
    }

    //currRazdacha
    public void distributionCards(Player[] players){
        for (int i = 0; i < players.length; i++) {
            for (int j = i*10; j < 10*(i+1); j++) {
                players[i].takeCard(coloda.getCard(j));
            }
        }
    }

    //тасуется
    public void reshuffle() {
        if(coloda != null) {
            int length = coloda.getSize();
            //значения передаем системное время
            Random generator = new Random(new Date().getTime());
            //тосуем колоду перебираем все карты
            int newPos;
            Card curCard;
            for(int i = 0; i < length; i++) {
                //генерируем случайное число, в диапазоне от нуля до
                //конца колоды
                newPos = generator.nextInt(length);
                //меняем местами текущую карту с картой, которая находится
                //в pack[newPos]
                curCard = coloda.getCard(i);
                coloda.setCard(i,coloda.getCard(newPos));
                coloda.setCard(newPos,curCard);
                //для увеличения эффекта "случайности" возникновения чисел,
                //в течении перетасовки колоды, четыре раза устанавливаем
                //новое начальное значение генератора случайных чисел
                if(i%(length/4) == 0) {
                    //генерируем случайный интервал времени (мс)
                    int pause = generator.nextInt(20);
                    try {
                        //останавливаем работу программы на полученный
                        //интервал времени (максимально возможная задержка
                        //восемдесят миллисекунд)
                        Thread.currentThread().sleep(pause);
                    }
                    catch (InterruptedException ex) {ex.printStackTrace();}
                    //уставливаем новое начальное значение генератора
                    generator.setSeed(new Date().getTime());
                }
            }
        }
    }

    //выбрали козырь
    public void setMainMast(String mast){
        mainMast = mast;
        Card card;
        for (int i = 0; i < coloda.getSize(); i++) {
            card = coloda.getCard(i);
            if (card.getMast().equals(mast)){
                card.setCode(card.getCode()+100);
            }
        }
    }

    //убрали козырь
    public void dropMainMast(){
        if (mainMast!=null) {
            Card card;
            for (int i = 0; i < coloda.getSize(); i++) {
                card = coloda.getCard(i);
                if (card.getMast().equals(mainMast)) {
                    card.setCode(card.getCode() - 100);
                }
            }
        }
    }
}
