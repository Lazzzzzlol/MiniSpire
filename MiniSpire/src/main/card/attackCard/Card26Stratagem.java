package main.card.attackCard;

import main.processor.DamageProcessor;
import main.buff.debuff.BuffVulnerable;
import main.buff.oneRoundBuff.BuffStratagem;
import main.enemy.Enemy;
import main.player.Player;

public class Card26Stratagem extends AttackCard {
    
    public Card26Stratagem() {
        this.name = "Stratagem";
        this.info = "During this round, when damage is dealt, gain 1 cost, draw 1 card. Apply 1 round of Vulnerable; Deal 8 damage.";
        this.cost = 3;
        this.rarity = "legendary";
        this.baseDamage = 8;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        player.addBuff(new BuffStratagem(1), 1);
		enemy.addBuff(new BuffVulnerable(1), 1);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
    }
}