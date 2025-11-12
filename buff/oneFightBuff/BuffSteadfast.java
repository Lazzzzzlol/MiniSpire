package main.buff.oneFightBuff;

public class BuffSteadfast extends OneFightBuff {
    
    public BuffSteadfast(int duration) {
        this.name = "Steadfast";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}