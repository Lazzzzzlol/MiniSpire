package main.processor;

import main.player.Player;
import main.resourceFactory.CardFactory;
import main.Main;
import main.card.Card;
import main.card.attackCard.AttackCard;
import main.card.effectCard.EffectCard;
import main.enemy.Enemy;

import java.util.concurrent.TimeUnit;

public class PlayCardProcessor {
    
    public static void processCardPlay(Player player, Card card, Enemy enemy) {
        if (!card.getCanPlay()) {
            System.out.println(" >> Unplayable card type.");
            return;
        }

        switch (card.getType()) {
            case "Attack":
                processAttackCard(player, card, enemy);
                break;
            case "Effect":
                processEffectCard(player, card, enemy);
                break;
            default:
                System.out.println(" >> Unknown or unplayable card type.");
                break;
        }
    }
    
    private static void processAttackCard(Player player, Card card, Enemy enemy) {
        AttackCard attackCard = (AttackCard) card;
        attackCard.onPlay(player, enemy);
        System.out.println(" >> Played card: " + card.getName());

        if (hasBuff(player, "Double")) {
            Main.executor.schedule(() -> {
                attackCard.onPlay(player, enemy);
                System.out.println(" >> Played card: " + card.getName() + " (Double)");

                player.getBuffList().removeIf(buff -> "Double".equals(buff.getName()));
            }, 1, TimeUnit.SECONDS);
        }
    }
    
    private static void processEffectCard(Player player, Card card, Enemy enemy) {
        EffectCard effectCard = (EffectCard) card;
        effectCard.onUse(player, enemy);
        System.out.println(" >> Played card: " + card.getName());

        if (hasBuff(player, "GainFlurryOfBlows")) {
            addFlurryOfBlowsToHand(player);
        }
    }
    
    private static boolean hasBuff(Player player, String buffName) {
        return player.getBuffList().stream()
                .anyMatch(buff -> buffName.equals(buff.getName()));
    }
    
    private static void addFlurryOfBlowsToHand(Player player) {
        Card flurryCard = CardFactory.getInstance().createCard(24);
        player.getHandCardList().add(flurryCard);
        Main.executor.schedule(() -> {
            System.out.println(" >> Drawed " + flurryCard.getName());
        }, 1, TimeUnit.SECONDS);
    }
}