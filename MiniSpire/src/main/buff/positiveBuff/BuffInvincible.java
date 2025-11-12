package main.buff.positiveBuff;

public class BuffInvincible extends PositiveBuff {
    
    public BuffInvincible(int duration) {
        this.name = "Invincible";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}