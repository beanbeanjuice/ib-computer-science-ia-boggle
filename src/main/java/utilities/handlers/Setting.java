package utilities.handlers;

public class Setting {

    double num;
    int bit;

    public Setting(double num, int bit) {
        this.num = num;
        this.bit = bit;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public double getNum() {
        return num;
    }

    public int getBit() {
        return bit;
    }

}
