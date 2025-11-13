package main.buff.oneRoundBuff;

import main.buff.Buff;

public abstract class OneRoundBuff extends Buff {
    
    public OneRoundBuff() {
        this.type = "positive";
    }
    
    @Override
    public void onEndTurn() {
        this.duration = 0;
    }
}