package main.buff.positiveBuff;

public class BuffRecovering extends PositiveBuff {
    
    public BuffRecovering(int duration) {
        this.name = "Recovering";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}