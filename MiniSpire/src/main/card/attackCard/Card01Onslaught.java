package main.card.attackCard;

import main.processor.DamageProcessor;
import main.buff.debuff.BuffWeakened;
import main.enemy.Enemy;
import main.player.Player;

public class Card01Onslaught extends AttackCard {
    
    public Card01Onslaught() {
        this.name = "Onslaught";
        this.info = "Deal 6 damage; Apply 1 round Weakened.";
        this.cost = 1;
        this.rarity = "normal";
        this.baseDamage = 6;
    }
    
    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
		
		enemy.addBuff(new BuffWeakened(1), 1);
    }
    
}