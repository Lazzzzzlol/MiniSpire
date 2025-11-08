package main.card.attackCard;

import main.card.Card;
import main.enemy.Enemy;
import main.player.Player;

public abstract class AttackCard extends Card {
    protected int baseDamage;

    public AttackCard() {
        this.type = "Attack";
        this.canPlay = true;
    }

    public int getBaseDamage(){
        return baseDamage;
    }
    
    public abstract void onPlay(Player player, Enemy enemy);
}