package main.buff.positiveBuff;

public class BuffTough extends PositiveBuff {
    
    public BuffTough(int duration) {
        this.name = "Tough";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}