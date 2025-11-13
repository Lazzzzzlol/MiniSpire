package main.card.attackCard;

import main.processor.DamageProcessor;
import main.processor.HealProcessor;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffVulnerable;
import main.enemy.Enemy;
import main.player.Player;
import main.Main;

import java.util.concurrent.TimeUnit;

public class Card29PandorasBox extends AttackCard {
    
    public Card29PandorasBox() {
        this.name = "Pandora's Box";
        this.info = "Do 1 random action of belows, towards the enemy: Deal 13~66 damage; Heal 13~66; Apply 33 round of Vulnerable; Apply 66 round of Weakened; Apply 99 round of Strengthened and Tough; Deal 66 damage; Heal 66.";
        this.cost = 1;
        this.rarity = "epic";
        this.baseDamage = 13;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {

        int randomValue = Main.random.nextInt(100);
        int effectType;
        
        if (randomValue < 30) {
            effectType = 0; // 30%: 13~66 Damage
        } else if (randomValue < 45) {
            effectType = 1; // 15%: 13~66 Heal
        } else if (randomValue < 60) {
            effectType = 2; // 15%: 33 round Vulnerable
        } else if (randomValue < 75) {
            effectType = 3; // 15%: 66 round Weakened
        } else if (randomValue < 90) {
            effectType = 4; // 15%: 99 round Strengthened and Tough
        } else if (randomValue < 95) {
            effectType = 5; // 5%: 66 Damage
        } else {
            effectType = 6; // 5%: 66 Heal
        }
        
        switch (effectType) {
            case 0:
                int damage1 = Main.random.nextInt(54) + baseDamage; // 13-66
                DamageProcessor.applyDamageToEnemy(damage1, player, enemy);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *screams* ");
                }, 1, TimeUnit.MILLISECONDS);
                break;
                
            case 1:
                int heal1 = Main.random.nextInt(54) + baseDamage; // 13-66
                HealProcessor.applyHeal(enemy, heal1, null);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *puffs* ");
                }, 1, TimeUnit.MILLISECONDS);
                break;
                
            case 2:
                enemy.addBuff(new BuffVulnerable(33), 33);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *mumbles* ");
                }, 1, TimeUnit.MILLISECONDS);
                break;
                
            case 3:
                enemy.addBuff(new BuffWeakened(66), 66);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *wriggles* ");
                }, 1, TimeUnit.MILLISECONDS);
                break;
                
            case 4:
                enemy.addBuff(new BuffStrengthened(99), 99);
                enemy.addBuff(new BuffTough(99), 99);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *smirks* "); 
                }, 1, TimeUnit.MILLISECONDS);
                break;
                
            case 5:
                DamageProcessor.applyDamageToEnemy(66, player, enemy);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *opens* ");
                }, 1, TimeUnit.MILLISECONDS);
                break;
                
            case 6:
                HealProcessor.applyHeal(enemy, 66, null);
                Main.executor.schedule(() -> {
                    System.out.println(" >> Box: *giggles* ");
                }, 1, TimeUnit.MILLISECONDS);
                break;
        }
    }
}