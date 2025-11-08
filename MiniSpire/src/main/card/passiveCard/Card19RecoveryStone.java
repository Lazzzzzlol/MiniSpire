package main.card.passiveCard;

import main.player.Player;
import main.*; //use to trigger warning, delete it after fill onUse()

public class Card19RecoveryStone extends PassiveCard{
    
    public Card19RecoveryStone() {
        this.name = "Recovery Stone";
        this.info = "When discarded, heal 6 hp.";
        this.rarity = "normal";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {
        //need to fill
    }
}