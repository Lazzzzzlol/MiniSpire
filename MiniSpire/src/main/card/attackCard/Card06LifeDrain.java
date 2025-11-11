package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.HealProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard {
    
    public Card06LifeDrain() {
        this.name = "Life Drain";
        this.info = "Deal 6 damage; This attack has BloodLeeching.";
        this.cost = 2;
        this.rarity = "normal";
        this.baseDamage = 12;
    }
    

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		int finalDamage = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		
		if (finalDamage > 0 && !hasBloodLeechingEffect(player)) {
        	HealProcessor.applyHeal(player, finalDamage);
    	}
    }

    private boolean hasBloodLeechingEffect(Player player) {
    	return player.getBuffList().stream().anyMatch(buff -> "BloodLeeching".equals(buff.getName()));
	}
}