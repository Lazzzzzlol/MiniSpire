package main.buff.oneFightBuff;

public class BuffBlessed extends OneFightBuff {
    
    public BuffBlessed(int duration) {
        this.name = "Blessed";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}