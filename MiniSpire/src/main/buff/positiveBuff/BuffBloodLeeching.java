package main.buff.positiveBuff;

public class BuffBloodLeeching extends PositiveBuff {
    
    public BuffBloodLeeching(int duration) {
        this.name = "BloodLeeching";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}