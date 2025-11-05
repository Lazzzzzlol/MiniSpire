package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard{
	
	String name = "Life Drain";
	String info = "Deal 6 damage; This attack has BloodLeeching. ";
	int cost = 2;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 12;

		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		int finalDamage = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		
		if (finalDamage > 0 && !hasBloodLeechingEffect(player)) {
        	player.addHp(finalDamage);
    	}
	}

	private boolean hasBloodLeechingEffect(Player player) {
    	return player.getBuffList().stream().anyMatch(buff -> "BloodLeeching".equals(buff.getName()));
	}
	
	@Override
	public String getName() {
		return name;
	}
}