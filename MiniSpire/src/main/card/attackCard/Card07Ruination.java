package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card07Ruination extends AttackCard {
    
    public Card07Ruination() {
        this.name = "Ruination";
        this.info = "Deal 25 damage; If it finally deals more than 25 damage, deal 25 damage again, only once.";
        this.cost = 3;
        this.rarity = "legendary";
        this.baseDamage = 25;
    }
    

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		int finalDamage = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		
		if (finalDamage > baseDamage) {
			DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		}
    }
}