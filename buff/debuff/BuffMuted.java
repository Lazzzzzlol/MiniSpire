package main.buff.debuff;

public class BuffMuted extends Debuff {
    
    public BuffMuted(int duration) {
        this.name = "Muted";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}