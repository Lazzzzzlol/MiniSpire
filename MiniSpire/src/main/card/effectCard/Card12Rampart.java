package main.card.effectCard;

import main.buff.HealProcessor;
import main.buff.positiveBuff.BuffTough;
import main.enemy.Enemy;
import main.player.Player;

public class Card12Rampart extends EffectCard{
	
	String name = "Rampart";
	String info = "Apply 2 rounds Tough to self; Heal 5 to self. ";
	String rarity = "rare";
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		int processedHeal = HealProcessor.processHealValue(Player.getInstance().getBuffList(), 5);
		player.addBuff(new BuffTough(2), 2);
		player.addHp(processedHeal);
	}
	
	@Override
	public String getInfo(){
		return info;
	}
	
	@Override
	public String getName() {
		return name;
	}
}