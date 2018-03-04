package Game.ColodaCards;

public class Card implements Comparable<Card> {
    private String name;
    private String mast;
    private int code;
    private static String mastPriority;

    public static void setMastPriority(String mast){
        mastPriority = mast;
    }

    public Card(String name, String mast,int code){
        this.mast = mast;
        this.name = name;
        this.code = code;
    }

    @Override
    public int compareTo(Card o) {
        if (!mast.equals(o.mast)) {
            if (mast.equals(mastPriority)) return  1;
            else if (o.mast.equals(mastPriority)) return -1;
            else return 0;
        }
        else {
            if (this.code > o.code) return 1;
            else return -1;
        }
    }
}
