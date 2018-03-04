package Game.ColodaCards;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Coloda {
    private ArrayList<Card> cards;

    public Coloda(){
        cards = new ArrayList<>(36);
        for (int i = 0; i < 4; i++) {
            for (int j = 6; j < 11; j++) {
                cards.add(new Card(String.valueOf(j),"♠",j));
                cards.add(new Card(String.valueOf(j),"♥",j));
                cards.add(new Card(String.valueOf(j),"♣",j));
                cards.add(new Card(String.valueOf(j),"♦",j));
            }
        }
        cards.add(new Card("Валет","♠",11));
        cards.add(new Card("Валет","♥",11));
        cards.add(new Card("Валет","♣",11));
        cards.add(new Card("Валет","♦",11));
        cards.add(new Card("Дама","♠",12));
        cards.add(new Card("Дама","♥",12));
        cards.add(new Card("Дама","♣",12));
        cards.add(new Card("Дама","♦",12));
        cards.add(new Card("Король","♠",13));
        cards.add(new Card("Король","♥",13));
        cards.add(new Card("Король","♣",13));
        cards.add(new Card("Король","♦",13));
        cards.add(new Card("Туз","♠",14));
        cards.add(new Card("Туз","♥",14));
        cards.add(new Card("Туз","♣",14));
        cards.add(new Card("Туз","♦",14));
    }

    public void reshuffle(ArrayList<Card> pack) {
        if(pack != null) {
            int length = pack.size();
            //создаем генератор случайных чисел, в качестве начального
            //значения передаем системное время
            Random generator = new Random(new Date().getTime());
            //тосуем колоду карт
            //перебираем все карты колоды
            int newPos;
            Card curCard;
            for(int i = 0; i < length; i++) {
                //генерируем случайное число, в диапазоне от нуля до
                //конца колоды
                newPos = generator.nextInt(length);
                //меняем местами текущую карту с картой, которая находится
                //в pack[newPos]
                curCard = pack.get(i);
                pack.set(i,pack.get(newPos));
                pack.set(newPos,curCard);
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
                    catch (InterruptedException ex) {}
                    //уставливаем новое начальное значение генератора
                    generator.setSeed(new Date().getTime());
                }
            }
        }
    }


}
