package main.card.attackCard;

import java.util.List;

import main.processor.DamageProcessor;
import main.card.Card;
import main.enemy.Enemy;
import main.player.Player;

public class Card25TheSinisterBlade extends AttackCard {
    
    public Card25TheSinisterBlade() {
        this.name = "The Sinister Blade";
        this.info = "Deal 6 damage, draw 1 card. If Attack Card is drawn, gain 2 cost.";
        this.cost = 1;
        this.rarity = "epic";
        this.baseDamage = 6;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);

        List<Card> drawnCards = player.drawHandCardsWithDetails(1);
        int attackCardCount = drawnCards.stream()
                .filter(card -> "Attack".equals(card.getType()))
                .mapToInt(card -> 1)
                .sum();
        if (attackCardCount > 0) {
            player.changeCurrentActionPoint(2);
        }
    }
}