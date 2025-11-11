package main.processor;

import main.player.Player;
import main.Main;
import main.enemy.Enemy;
import main.enemy.eliteEnemy.Watcher;
import main.buff.Buff;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DamageProcessor {
    
    private static final ThreadLocal<Boolean> isReflectiveDamage = ThreadLocal.withInitial(() -> false);
    
    public static void applyDamageToEnemy(int baseDamage, Enemy target) {
        processDamage(baseDamage, Player.getInstance(), target, false);
    }
    
    public static void applyDamageToPlayer(int baseDamage, Enemy attacker, Player target) {
        processDamage(baseDamage, attacker, target, false);
    }

    public static int calculateDamageToEnemy(int baseDamage, Enemy target) {
        return calculateDamageOnly(baseDamage, Player.getInstance(), target, false);
    }
    
    public static int calculateDamageToPlayer(int baseDamage, Enemy attacker, Player target) {
        return calculateDamageOnly(baseDamage, attacker, target, false);
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

            if (finalDamage > 0) {
                applyDamageEffect(baseDamage, finalDamage, attacker, target);
            } else applyZeroDamageEffect(target);

            if (hasReflective(target) && !isReflective && finalDamage > 0 && attacker != null && !hasIgnore(attacker)) {
                processDamage(finalDamage, target, attacker, true);
            }

            if (hasBloodLeeching(attacker) && finalDamage > 0 && attacker != null) {
                HealProcessor.applyHeal(attacker, finalDamage);
            }

            if (hasStratagem(attacker) && finalDamage > 0 && attacker != null && attacker instanceof Player) {
                Player playerAttacker = (Player) attacker;
                playerAttacker.changeCurrentActionPoint(1);
                playerAttacker.drawHandCards(1);
            }
            
        } finally {

            if (isReflective) {
                isReflectiveDamage.set(false);
            }

        }
    }
    
    private static int calculateDamageOnly(int baseDamage, Object attacker, Object target, boolean isReflective) {

        float damageMultiplier = 1.0f;

        boolean hasInvincible = hasInvincible(target);
        boolean hasIgnore = hasIgnore(attacker);

        boolean attackMistied = false;
        boolean attackAbsorbed = false;

        if (hasInvincible && !hasIgnore) return 0;

        if (attacker != null) {
            List<Buff> attackerBuffs = getBuffList(attacker);
            for (Buff buff : attackerBuffs) {
                switch (buff.getName()) {

                    case "Strengthened":
                        damageMultiplier += 0.3f;
                        break;
                    case "Weakened":
                        damageMultiplier -= 0.3f;
                        break;

                    case "Indomitable":
                        damageMultiplier *= 2.0f;
                        break;

                }
            }
        }

        List<Buff> targetBuffs = getBuffList(target);
        for (Buff buff : targetBuffs) {
            switch (buff.getName()) {

                case "Vulnerable":
                    damageMultiplier += 0.3f;
                    break;
                case "Tough":
                    if (!hasIgnore) damageMultiplier -= 0.3f;
                    break;
                    
                case "Enshroud":
                    damageMultiplier += 0.5f;
                    break;

                case "Misty":
                    if (Main.random.nextInt(2) == 0) {
                        attackMistied = true;
                        damageMultiplier = 0.0f;
                    }
                    break;

                case "Steelsoul":
                    attackAbsorbed = true;
                    break;

            }
        }

        damageMultiplier = Math.max(damageMultiplier, 0);
        int calculatedDamage = Math.round(baseDamage * damageMultiplier);
        
        if (attackMistied) {
            scheduleDamageMessage(target, "The attack fails (Misty)", 1);
            return 0;
        }
        
        if (attackAbsorbed && target instanceof Enemy) {
            Enemy enemy = (Enemy) target;
            enemy.absorbDamageWithSteelsoul(calculatedDamage);
            String message = getTargetName(target) + " absorbs " + 
                           getDamageDisplay(baseDamage, calculatedDamage) + " damage (Steelsoul)";
            scheduleDamageMessage(target, message, 1);
            
            if (hasSteadfast(target)) {
                ((Watcher) target).addGettedDamageCounter(calculatedDamage);
            }
            return 0;
        }
        
        return calculatedDamage;
    }
    
    private static void applyDamageEffect(int baseDamage, int finalDamage, Object attacker, Object target) {

        String damageDisplay = getDamageDisplay(baseDamage, finalDamage);
        String message;
        
        if (target instanceof Enemy) {

            Enemy enemy = (Enemy) target;
            enemy.deductHp(finalDamage);
            message = getTargetName(target) + " takes " + damageDisplay + " damage";
            
            if (hasSteadfast(target)) {
                ((Watcher) target).addGettedDamageCounter(finalDamage);
            }

        } else if (target instanceof Player) {

            ((Player) target).deductHp(finalDamage);
            message = "Took " + damageDisplay + " damage";

        } else {
            return;
        }
        
        scheduleDamageMessage(target, message, 1);
    }
    
    private static void applyZeroDamageEffect(Object target) {
        
        String message;

        if (target instanceof Enemy) {
            message = getTargetName(target) + " takes 0 damage";
        } else {
            message = "Took 0 damage";
        }

        scheduleDamageMessage(target, message, 1);
    }
    
    private static void scheduleDamageMessage(Object target, String message, int delaySeconds) {

        Main.executor.schedule(() -> {
            System.out.println(" >> " + message);
        }, delaySeconds, TimeUnit.SECONDS);
    }
    
    private static String getTargetName(Object target) {

        if (target instanceof Enemy) {
            return ((Enemy) target).getName();
        }
        return "";
    }
    
    private static boolean hasBloodLeeching(Object attacker) {

        if (attacker == null) return false;
        List<Buff> buffs = getBuffList(attacker);
        return buffs.stream().anyMatch(buff -> "BloodLeeching".equals(buff.getName()));
    }
    
    private static boolean hasInvincible(Object target) {

        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Invincible".equals(buff.getName()));
    }

    private static boolean hasReflective(Object target) {

        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Reflective".equals(buff.getName()));
    }

    private static boolean hasStratagem(Object target) {

        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Stratagem".equals(buff.getName()));
    }
    
    private static boolean hasSteadfast(Object target) {

        if (!(target instanceof Enemy)) return false;

        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Steadfast".equals(buff.getName()));
    }

    private static boolean hasIgnore(Object attacker) {

        if (attacker == null) return false;
        List<Buff> buffs = getBuffList(attacker);
        return buffs.stream().anyMatch(buff -> "Ignore".equals(buff.getName()));
    }
    
    private static List<Buff> getBuffList(Object obj) {

        if (obj instanceof Player) {
            return ((Player) obj).getBuffList();
        } else if (obj instanceof Enemy) {
            return ((Enemy) obj).getBuffList();
        }

        return java.util.Collections.emptyList();
    }

    private static String getDamageDisplay(int baseDamage, int finalDamage) {

        if (finalDamage == baseDamage) {
            return String.valueOf(finalDamage);
        } else if (finalDamage > baseDamage) {
            return finalDamage + "(+" + (finalDamage - baseDamage) + ")";
        } else {
            return finalDamage + "(-" + (baseDamage - finalDamage) + ")";
        }
    }
}