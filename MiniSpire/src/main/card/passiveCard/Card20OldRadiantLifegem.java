package main.card.passiveCard;

import main.*; //use to trigger warning, delete it after fill onUse()

public class Card20OldRadiantLifegem extends PassiveCard{
    
    public Card20OldRadiantLifegem() {
        this.name = "Old Radiant Lifegem";
        this.info = "When discarded, heal 12 hp and apply 3 round Recovery to seld.";
        this.rarity = "rare";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {
        //need to fill
    }
}