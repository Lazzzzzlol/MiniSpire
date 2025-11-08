package main.card.effectCard;

import main.buff.positiveBuff.BuffInvincible;
import main.enemy.Enemy;
import main.player.Player;

public class Card16Holmgang extends EffectCard{
    
    public Card16Holmgang() {
        this.name = "Holmgang";
        this.info = "Apply 1 round Invincible to self.";
        this.rarity = "epic";
        this.cost = 2;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffInvincible(1), 1);
    }
}