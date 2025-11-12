package main.buff.oneRoundBuff;

public class BuffStratagem extends OneRoundBuff {
    
    public BuffStratagem(int duration) {
        this.name = "Stratagem";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}