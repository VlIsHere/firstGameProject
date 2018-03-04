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
                cards.add(new Card(String.valueOf(j),"Пики",j));
                cards.add(new Card(String.valueOf(j),"Червы",j));
                cards.add(new Card(String.valueOf(j),"Трефы",j));
                cards.add(new Card(String.valueOf(j),"Бубны",j));
            }
        }
        cards.add(new Card("Валет","Пики",11));
        cards.add(new Card("Валет","Червы",11));
        cards.add(new Card("Валет","Трефы",11));
        cards.add(new Card("Валет","Бубны",11));
        cards.add(new Card("Дама","Пики",12));
        cards.add(new Card("Дама","Червы",12));
        cards.add(new Card("Дама","Трефы",12));
        cards.add(new Card("Дама","Бубны",12));
        cards.add(new Card("Король","Пики",13));
        cards.add(new Card("Король","Червы",13));
        cards.add(new Card("Король","Трефы",13));
        cards.add(new Card("Король","Бубны",13));
        cards.add(new Card("Туз","Пики",14));
        cards.add(new Card("Туз","Червы",14));
        cards.add(new Card("Туз","Трефы",14));
        cards.add(new Card("Туз","Бубны",14));
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
