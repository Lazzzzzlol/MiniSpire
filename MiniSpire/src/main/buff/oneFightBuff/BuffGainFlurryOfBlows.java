package main.buff.oneFightBuff;

public class BuffGainFlurryOfBlows extends OneFightBuff {
    
    public BuffGainFlurryOfBlows(int duration) {
        this.name = "GainFlurryOfBlows";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}