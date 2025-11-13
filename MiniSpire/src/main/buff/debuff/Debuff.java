package main.buff.debuff;

import main.buff.Buff;

public abstract class Debuff extends Buff {

    public Debuff() {
        this.type = "negative";
    }
    
    @Override
    public void onEndTurn() {
        this.duration -= 1;
    }
}
