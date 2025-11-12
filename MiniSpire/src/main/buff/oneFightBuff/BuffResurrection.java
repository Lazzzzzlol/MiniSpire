package main.buff.oneFightBuff;

public class BuffResurrection extends OneFightBuff {
    
    public BuffResurrection(int duration) {
        this.name = "Resurrection";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}