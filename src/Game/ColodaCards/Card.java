package Game.ColodaCards;

public class Card implements Comparable<Card> {

    private String name;
    private String mast;
    private int code;

    public String getName() {
        return name;
    }

    public String getMast() {
        return mast;
    }

    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }

    Card(String name, String mast,int code){
        this.mast = mast;
        this.name = name;
        this.code = code;
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(code,o.code);
    }

    @Override
    public String toString(){
        return name + mast;
    }
}
