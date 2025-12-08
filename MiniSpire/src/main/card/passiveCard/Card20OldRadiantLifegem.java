package main.card.passiveCard;

import main.processor.HealProcessor;
import main.buff.positiveBuff.BuffRecovering;
import main.player.Player;

public class Card20OldRadiantLifegem extends PassiveCard{
    
    public Card20OldRadiantLifegem() {
        this.name = "Old Radiant Lifegem";
        this.info = "When discarded, heal 12 hp and apply 3 round Recovery to self.";
        this.rarity = "rare";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {

        Player player = Player.getInstance();

		HealProcessor.applyHeal(player, 12, 0);

		player.addBuff(new BuffRecovering(3), 3);
    }
}