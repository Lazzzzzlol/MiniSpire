package main.processor;

import java.util.List;
import main.player.Player;
import main.enemy.Enemy;
import main.buff.Buff;

public class HealProcessor {
    
    public static int calculateHeal(List<Buff> buffList, int value){
        
        processEnshroud(buffList);
        boolean hasLost = buffList.stream().anyMatch(buff -> "Lost".equals(buff.getName()));

        if (hasLost)
            return 0;
        return value;
    }
    
    public static void applyHeal(Object target, int heal) {
        if (target instanceof Player) {
            Player player = (Player) target;
            int finalHeal = calculateHeal(player.getBuffList(), heal);
            player.addHp(finalHeal);
        } else if (target instanceof Enemy) {
            Enemy enemy = (Enemy) target;
            int finalHeal = calculateHeal(enemy.getBuffList(), heal);
            enemy.addHp(finalHeal);
        }
    }

    private static void processEnshroud(List<Buff> buffList) {

        boolean hasEnshroud = buffList.stream()
                .anyMatch(buff -> "Enshroud".equals(buff.getName()));

        if (hasEnshroud) {
            for (Buff buff : buffList) {
                if (buff.getName().equals("Enshroud")) {
                    buff.extendDuration(-1);
                    break;
                }
            }
        }
    }
}
