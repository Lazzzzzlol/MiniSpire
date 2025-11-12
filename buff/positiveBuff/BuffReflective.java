package main.buff.positiveBuff;

public class BuffReflective extends PositiveBuff {
    
    public BuffReflective(int duration) {
        this.name = "Reflective";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}