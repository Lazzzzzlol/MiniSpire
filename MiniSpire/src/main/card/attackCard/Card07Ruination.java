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
		this.needRemove = true;
    }
    

    @Override
    public void onPlay(Player player, Enemy enemy) {
		int finalDamage = DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
		
		if (finalDamage > baseDamage) {
			DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
		}
    }
}
