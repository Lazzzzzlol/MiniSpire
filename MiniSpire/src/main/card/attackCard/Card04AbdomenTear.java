package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card04AbdomenTear extends AttackCard {
    
    public Card04AbdomenTear() {
        this.name = "Abdomen Tear";
        this.info = "Deal 12 damage; Take 3 damage.";
        this.cost = 1;
        this.rarity = "normal";
        this.baseDamage = 12;
    }
    

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);

		int baseSelfDamage = 3;
		DamageProcessor.applyDamageToPlayer(baseSelfDamage, player);
    }
}