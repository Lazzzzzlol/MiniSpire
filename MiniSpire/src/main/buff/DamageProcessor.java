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
            boolean hasStratagem = hasStratagem(attacker);
            
            if (finalDamage > 0) {

                String damageDisplay = getDamageDisplay(baseDamage, finalDamage);

                if (target instanceof Enemy) {
                    ((Enemy) target).deductHp(finalDamage);

                    Main.executor.schedule(() -> {
                        System.out.println(" >> " + ((Enemy) target).getName() + " takes " + damageDisplay + " damage");

                        if (((Enemy) target).getBuffList().stream().anyMatch(buff -> "Steadfast".equals(buff.getName())));
                            ((Watcher) target).addGettedDamageCounter(finalDamage);
                    }, 1, TimeUnit.SECONDS);

                } else if (target instanceof Player) {
                    ((Player) target).deductHp(finalDamage);
                    Main.executor.schedule(() -> {
                        System.out.println(" >> " + "Took " + damageDisplay + " damage");
                    }, 1, TimeUnit.SECONDS);
                }
            } else {
                if (target instanceof Enemy) {
                    Main.executor.schedule(() -> {
                        System.out.println(" >> " + ((Enemy) target).getName() + " takes 0 damage");
                    }, 1, TimeUnit.SECONDS);
                } else if (target instanceof Player) {
                    Main.executor.schedule(() -> {
                        System.out.println(" >> Took 0 damage");
                    }, 1, TimeUnit.SECONDS);
                }
            }
            
            if (hasReflective && finalDamage > 0 && attacker != null) {
                processDamage(finalDamage, target, attacker, true);
            }

            if (hasBloodLeeching && finalDamage > 0 && attacker != null) {
                HealProcessor.applyHeal(attacker, finalDamage);
            }

            if (hasStratagem && finalDamage > 0 && attacker != null) {
                if (attacker instanceof Player) {
                    ((Player) attacker).changeCurrentActionPoint(1);
                    ((Player) attacker).drawHandCards(1);
                }
            }
            
        } finally {

            if (isReflective) {
                isReflectiveDamage.set(false);
            }
        }
    }
    
    private static int calculateDamageOnly(int baseDamage, Object attacker, Object target, boolean isReflective) {
        
        if (hasInvincible(target)) return 0;

        float damageMultiplier = 1.0f;

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
                    damageMultiplier -= 0.3f;
                    break;
                case "Misty":
                    damageMultiplier = mistyDamageDecider();
                    break;
                case "Enshroud":
                    damageMultiplier += 0.5f;
            }
        }
        
        damageMultiplier = Math.max(damageMultiplier, 0);
        return Math.round(baseDamage * damageMultiplier);
    }

    private static float mistyDamageDecider() {

        switch (Main.random.nextInt(2)) {
            case 0:
                Main.executor.schedule(() -> {
                    System.out.println( " >> The damage is ignored (Misty)");
                }, 1, TimeUnit.MILLISECONDS);
                return 0.0f;
        
            case 1:
                return 1.0f;

            default:
                return 1.0f;
        }
    }
    
    private static boolean hasBloodLeeching(Object attacker) {
        
        if (attacker == null) return false;
        
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

    private static boolean hasStratagem(Object target) {
        
        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Stratagem".equals(buff.getName()));
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