package main.card.attackCard;

import main.processor.DamageProcessor;
import main.processor.HealProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard {
    
    public Card06LifeDrain() {
        this.name = "Life Drain";
        this.info = "Deal 12 damage; This attack has BloodLeeching.";
        this.cost = 2;
        this.rarity = "normal";
        this.baseDamage = 12;
    }
    

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
		int finalDamage = DamageProcessor.calculateDamageToEnemy(baseDamage, Player.getInstance(), enemy);
		
		if (finalDamage > 0 && !hasBloodLeechingEffect(player)) {
        	HealProcessor.applyHeal(player, finalDamage, null);
    	}
    }

    private boolean hasBloodLeechingEffect(Player player) {
    	return player.getBuffList().stream().anyMatch(buff -> "BloodLeeching".equals(buff.getName()));
	}
}