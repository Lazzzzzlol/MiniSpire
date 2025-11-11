package main.card.effectCard;

import main.processor.HealProcessor;
import main.buff.positiveBuff.BuffTough;
import main.enemy.Enemy;
import main.player.Player;

public class Card12Rampart extends EffectCard{
    
    public Card12Rampart() {
        this.name = "Rampart";
        this.info = "Apply 2 rounds Tough to self; Heal 5 to self.";
        this.rarity = "rare";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffTough(2), 2);
		HealProcessor.applyHeal(player, 5, null);
    }
}