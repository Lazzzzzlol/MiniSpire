package main.buff.oneFightBuff;

import main.buff.Buff;

public abstract class OneFightBuff extends Buff {
    
    public OneFightBuff() {
        this.type = "positive";
    }
    
    @Override
    public void extendDuration(int d) {
        duration += 0;
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 0;
    }
}
