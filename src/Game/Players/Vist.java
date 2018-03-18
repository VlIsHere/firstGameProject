package Game.Players;

public class Vist {
    private String namePlForVist;
    private int count;

    public String getNamePlForVist() {
        return namePlForVist;
    }

    public void setNamePlForVist(String namePlForVist) {
        this.namePlForVist = namePlForVist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString(){
        return namePlForVist +": "+ count;
    }
}
