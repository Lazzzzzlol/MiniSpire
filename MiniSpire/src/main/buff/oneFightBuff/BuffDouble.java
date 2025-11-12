package main.buff.oneFightBuff;

public class BuffDouble extends OneFightBuff {
    
    public BuffDouble(int duration) {
        this.name = "Double";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}