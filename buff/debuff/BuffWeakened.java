package main.buff.debuff;

public class BuffWeakened extends Debuff {
    
    public BuffWeakened(int duration) {
        this.name = "Weakened";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}