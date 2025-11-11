package main.card.passiveCard;

import main.processor.DamageProcessor;
import main.player.Player;

public class Card22DeathBrand extends PassiveCard{
    
    public Card22DeathBrand() {
        this.name = "Death Brand";
        this.info = "When discarded, take 4 damage.";
        this.rarity = "special";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {

        int baseDamage = 4;
		Player player = Player.getInstance();
		DamageProcessor.applyDamageToPlayer(baseDamage, player);
	}
}