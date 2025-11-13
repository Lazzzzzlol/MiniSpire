package main.buff.positiveBuff;

import main.buff.Buff;

public abstract class PositiveBuff extends Buff {
    
    public PositiveBuff() {
        this.type = "positive";
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}