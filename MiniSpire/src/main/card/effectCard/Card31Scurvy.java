package main.card.effectCard;

import main.buff.oneRoundBuff.BuffScurvy;
import main.enemy.Enemy;
import main.player.Player;

public class Card31Scurvy extends EffectCard{
    
    public Card31Scurvy() {
        this.name = "Scurvy";
        this.info = "For the rest of this turn, convert all healing you receive into damage dealt to an enemy.";
        this.rarity = "epic";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffScurvy(1), 1);
    }
}