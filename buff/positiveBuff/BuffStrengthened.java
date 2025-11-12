package main.buff.positiveBuff;

public class BuffStrengthened extends PositiveBuff {
    
    public BuffStrengthened(int duration) {
        this.name = "Strength";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}