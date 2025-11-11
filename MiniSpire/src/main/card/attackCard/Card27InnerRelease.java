package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.enemy.Enemy;
import main.player.Player;

public class Card27InnerRelease extends AttackCard {
    
    public Card27InnerRelease() {
        this.name = "Inner Release";
        this.info = "Apply 2 round Strengthen, 2 round Tough to self, then deal 10 damage. If the card is never used in this round, cost change to 0.";
        this.cost = 0;
        this.rarity = "rare";
        this.baseDamage = 10;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        this.cost = 2;
        player.addBuff(new BuffStrengthened(2), 2);
        player.addBuff(new BuffTough(2), 2);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
    }
}