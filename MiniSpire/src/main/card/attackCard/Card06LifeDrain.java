package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard{
	
	String name = "Life Drain";
	String info = "Deal 6 damage; This attack has BloodLeeching. ";
	String rarity = "rare";
	int cost = 2;
	int baseDamage = 12;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

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

	@Override
	public String getInfo(){
		return info;
	}

	@Override
	public int getBaseDamage(){
		return baseDamage;
	}
}