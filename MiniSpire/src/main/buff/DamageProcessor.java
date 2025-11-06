package main.buff;

import main.player.Player;
import main.enemy.Enemy;

import java.util.List;

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
                } else if (target instanceof Player) {
                    ((Player) target).deductHp(finalDamage);
                }
            }

            if (hasBloodLeeching && finalDamage > 0 && attacker != null) {
                if (attacker instanceof Player) {
                    ((Player) attacker).addHp(finalDamage);
                } else if (attacker instanceof Enemy) {
                    ((Enemy) attacker).addHp(finalDamage);
                }
            }

            if (hasReflective && finalDamage > 0 && attacker != null) {
                processDamage(finalDamage, target, attacker, true);
            }
            
        } finally {

            if (isReflective) {
                isReflectiveDamage.set(false);
            }
        }
    }
    
    private static int calculateDamageOnly(int baseDamage, Object attacker, Object target, boolean isReflective) {
        if (hasInvincible(target)) {
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
            }
        }
        
        damageMultiplier = Math.max(damageMultiplier, 0);
        return Math.round(baseDamage * damageMultiplier);
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