package main.card.passiveCard;

import main.buff.HealProcessor;
import main.buff.positiveBuff.BuffRecovering;
import main.player.Player;

public class Card20OldRadiantLifegem extends PassiveCard{
    
    public Card20OldRadiantLifegem() {
        this.name = "Old Radiant Lifegem";
        this.info = "When discarded, heal 12 hp and apply 3 round Recovery to seld.";
        this.rarity = "rare";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {

        Player player = Player.getInstance();

		int finalHeal = HealProcessor.calculateHeal(player.getBuffList(), 12);
		player.addHp(finalHeal);

		player.addBuff(new BuffRecovering(3), 3);
    }
}