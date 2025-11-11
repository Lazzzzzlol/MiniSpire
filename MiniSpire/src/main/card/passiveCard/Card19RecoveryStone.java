package main.card.passiveCard;

import main.player.Player;
import main.buff.HealProcessor;

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

		int finalHeal = HealProcessor.calculateHeal(player.getBuffList(), 6);
		player.addHp(finalHeal);
    }
}