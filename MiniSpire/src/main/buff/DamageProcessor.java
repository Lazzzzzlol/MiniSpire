package main.buff;

import main.player.Player;
import main.enemy.Enemy;

import java.util.List;

public class DamageProcessor {
    
    public static DamageResult calculateDamageToEnemy(int baseDamage, Enemy target) {
        return calculateDamage(baseDamage, Player.getInstance(), target);
    }
    
    public static DamageResult calculateDamageToPlayer(int baseDamage, Player target) {
        return calculateDamage(baseDamage, null, target);
    }
    
    private static DamageResult calculateDamage(int baseDamage, Object attacker, Object target) {
        
        float damageMultiplier = 1.0f;

        if (isInvincible(target)) {
            return new DamageResult(0, baseDamage);
        }
        
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
        
        int finalDamage = Math.round(baseDamage * damageMultiplier);
        return new DamageResult(finalDamage, baseDamage);
    }
    
    private static boolean isInvincible(Object target) {

        List<Buff> buffs = getBuffList(target);
        return buffs.stream().anyMatch(buff -> "Invincible".equals(buff.getName()));
    }
    
    private static List<Buff> getBuffList(Object obj) {

        if (obj instanceof Player) {
            return ((Player) obj).getBuffList();
        } else if (obj instanceof Enemy) {
            return ((Enemy) obj).getBuffList();
        }
        return java.util.Collections.emptyList();
    }
    
    public static class DamageResult {

        private final int finalDamage;
        private final int baseDamage;
        
        public DamageResult(int finalDamage, int baseDamage) {
            this.finalDamage = finalDamage;
            this.baseDamage = baseDamage;
        }
        
        public int getFinalDamage() { return finalDamage; }
        public int getBaseDamage() { return baseDamage; }
    }
}