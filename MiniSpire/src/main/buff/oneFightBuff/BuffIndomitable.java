package main.buff.oneFightBuff;

public class BuffIndomitable extends OneFightBuff {
    
    public BuffIndomitable(int duration) {
        this.name = "Indomitable";
        this.duration = duration;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}