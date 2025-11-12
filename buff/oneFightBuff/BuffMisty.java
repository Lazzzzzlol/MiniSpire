package main.buff.oneFightBuff;

public class BuffMisty extends OneFightBuff {
    
    public BuffMisty(int duration) {
        this.name = "Misty";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}