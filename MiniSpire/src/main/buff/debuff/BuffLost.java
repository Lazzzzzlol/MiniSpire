package main.buff.debuff;

public class BuffLost extends Debuff {
    
    public BuffLost(int duration) {
        this.name = "Lost";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}