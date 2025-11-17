package main.card.attackCard;

import main.processor.DamageProcessor;
import main.TextDisplay;
import main.enemy.Enemy;
import main.player.Player;

public class Card30SacrificialRitual_cOLoRS extends AttackCard {
    
    public Card30SacrificialRitual_cOLoRS() {
        this.name = "Sacrificial Ritual - cOLoRS";
        this.info = "Deal 100 damage. --Power demands sacrifice. Shall we begin?";
        this.cost = 0;
        this.rarity = "special";
        this.baseDamage = 100;
        this.disposable = true;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
        player.lostColors();
        TextDisplay.printLineWithDelay(" >> You sense it — the world grows pale before your very eyes.", 50);
    }

}

