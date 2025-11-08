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
		int finalHeal = HealProcessor.calculateHeal(Player.getInstance().getBuffList(), 5);
		player.addBuff(new BuffTough(2), 2);
		player.addHp(finalHeal);
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