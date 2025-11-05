package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card07Ruination extends AttackCard{
	
	String name = "Ruination";
	String info = "Deal 25 damage; If it finally deals more than 25 damage, deal 25 damage again, only once. ";
	String rarity = "legendary";
	int cost = 3;
	int baseDamage = 25;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		int finalDamage = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		
		if (finalDamage > baseDamage) {
			DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBaseDamage(){
		return baseDamage;
	}
}