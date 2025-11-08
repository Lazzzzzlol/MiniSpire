package main.buff;

import main.player.Player;
import main.Main;
import main.enemy.Enemy;
import main.enemy.eliteEnemy.Watcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DamageProcessor {
    
    private static final ThreadLocal<Boolean> isReflectiveDamage = ThreadLocal.withInitial(() -> false);
    
    public static void applyDamageToEnemy(int baseDamage, Enemy target) {
        processDamage(baseDamage, Player.getInstance(), target, false);
    }
    
    public static void applyDamageToPlayer(int baseDamage, Player target) {
        processDamage(baseDamage, null, target, false);
    }

    public static int calculateDamageToEnemy(int baseDamage, Enemy target) {
        return calculateDamageOnly(baseDamage, Player.getInstance(), target, false);
    }
    
    public static int calculateDamageToPlayer(int baseDamage, Player target) {
        return calculateDamageOnly(baseDamage, null, target, false);
    }

    private static void processDamage(int baseDamage, Object attacker, Object target, boolean isReflective) {

        if (isReflective && isReflectiveDamage.get()) {
            return;
        }
        
        try {

            if (isReflective) {
                isReflectiveDamage.set(true);
            }
            
            int finalDamage = calculateDamageOnly(baseDamage, attacker, target, isReflective);
            boolean hasBloodLeeching = hasBloodLeeching(attacker);
            boolean hasReflective = hasReflective(target) && !isReflective;
            
            if (finalDamage > 0) {
                if (target instanceof Enemy) {
                    ((Enemy) target).deductHp(finalDamage);

                    if (((Enemy) target).getBuffList().stream().anyMatch(buff -> "Steadfast".equals(buff.getName()) &&
                        ((Enemy) target).getIsDied() == false));
                        ((Watcher) target).addGettedDamageCounter(finalDamage);

                } else if (target instanceof Player) {
                    ((Player) target).deductHp(finalDamage);
                }
            }
            
            if (hasReflective && finalDamage > 0 && attacker != null) {
                processDamage(finalDamage, target, attacker, true);
            }

            if (hasBloodLeeching && finalDamage > 0 && attacker != null) {
                int finalHeal = HealProcessor.calculateHeal(Player.getInstance().getBuffList(), finalDamage);
                if (attacker instanceof Player) {
                    ((Player) attacker).addHp(finalHeal);
                } else if (attacker instanceof Enemy) {
                    ((Enemy) attacker).addHp(finalHeal);
                }
            }
            
        } finally {

            if (isReflective) {
                isReflectiveDamage.set(false);
            }
        }
    }
    
    private static int calculateDamageOnly(int baseDamage, Object attacker, Object target, boolean isReflective) {
        
        if (hasInvincible(target)) {
            System.out.println(" >> Dealed 0 damage (Due to Invincible).");
            return 0;
        }

        float damageMultiplier = 1.0f;

        if (attacker != null) {
            List<Buff> attackerBuffs = getBuffList(attacker);
            for (Buff buff : attackerBuffs) {
                switch (buff.getName()) {
                    case "Strengthened":
                        damageMultiplier += 0.25f;
                        break;
                    case "Weakened":
                        damageMultiplier -= 0.25f;
                        break;
                }
            }
        }
        
        List<Buff> targetBuffs = getBuffList(target);
        for (Buff buff : targetBuffs) {
            switch (buff.getName()) {
                case "Vulnerable":
                    damageMultiplier += 0.25f;
                    break;
                case "Tough":
                    damageMultiplier -= 0.25f;
                    break;
                case "Misty":
                    damageMultiplier = mistyDamageDecider();
            }
        }
        
        damageMultiplier = Math.max(damageMultiplier, 0);
        return Math.round(baseDamage * damageMultiplier);
    }

    private static float mistyDamageDecider() {

        switch (Main.random.nextInt(2)) {
            case 0:
                Main.executor.schedule(() -> {
                    System.out.println( " >> The damage is ignored (Due to Misty).xiu");
                }, 1, TimeUnit.MILLISECONDS);
                return 0.0f;
        
            case 1:
                return 1.0f;

            default:
                return 1.0f;
        }
    }
    
    private static boolean hasBloodLeeching(Object attacker) {
        
        if (attacker == null) {
            return false;
        }
        
        List<Buff> attackerBuffs = getBuffList(attacker);
        return attackerBuffs.stream().anyMatch(buff -> "BloodLeeching".equals(buff.getName()));
    }
    
    private static boolean hasInvincible(Object target) {
        
        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Invincible".equals(buff.getName()));
    }

    private static boolean hasReflective(Object target) {
        
        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Reflective".equals(buff.getName()));
    }
    
    private static List<Buff> getBuffList(Object obj) {
        
        if (obj instanceof Player) {
            return ((Player) obj).getBuffList();
        } else if (obj instanceof Enemy) {
            return ((Enemy) obj).getBuffList();
        }
        return java.util.Collections.emptyList();
    }
}