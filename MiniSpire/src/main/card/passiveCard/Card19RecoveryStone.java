package main.card.passiveCard;

import main.player.Player;
import main.processor.HealProcessor;

public class Card19RecoveryStone extends PassiveCard{
    
    public Card19RecoveryStone() {
        this.name = "Recovery Stone";
        this.info = "When discarded, heal 6 hp.";
        this.rarity = "normal";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {
        
        Player player = Player.getInstance();

		HealProcessor.applyHeal(player, 6, 0);
    }
}