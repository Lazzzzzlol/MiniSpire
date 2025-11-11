package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.debuff.BuffVulnerable;
import main.enemy.Enemy;
import main.player.Player;

public class Card05DecesiveStrike extends AttackCard {
    
    public Card05DecesiveStrike() {
        this.name = "Decisive Strike";
        this.info = "Deal 15 damage; Apply 1 round Vulnerable.";
        this.cost = 2;
        this.rarity = "normal";
        this.baseDamage = 15;
    }
    

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);

		enemy.addBuff(new BuffVulnerable(1), 1);
    }
}