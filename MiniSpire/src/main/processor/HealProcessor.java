package main.processor;

import java.util.List;
import main.player.Player;
import main.enemy.Enemy;
import main.game.Game;
import main.node.NodeBattle;
import main.node.NodeBoss;
import main.buff.Buff;

public class HealProcessor {
    
    public static int calculateHeal(List<Buff> buffList, int value){
        
        processEnshroud(buffList);
        boolean hasLost = buffList.stream().anyMatch(buff -> "Lost".equals(buff.getName()));

        if (hasLost)
            return 0;
        return value;
    }
    
    public static void applyHeal(Object target, int heal, Integer time) {
        if (target instanceof Player) {
            Player player = (Player) target;
            int finalHeal = calculateHeal(player.getBuffList(), heal);
            boolean hasScurvy = player.getBuffList().stream().anyMatch(buff -> "Scurvy".equals(buff.getName()));
            Enemy enemy = getCurrentEnemy();
            if (hasScurvy && enemy != null){
                System.out.println(" >> Life force is channeled into destructive power.");
                DamageProcessor.applyDamageToEnemy(finalHeal, null, enemy);
            }else{
                player.addHp(finalHeal, time);
            }
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

    private static Enemy getCurrentEnemy() {
        try {
            Game game = Game.getInstance();
            if (game != null && (game.getCurrentNode() instanceof NodeBattle || game.getCurrentNode() instanceof NodeBoss)) {
                NodeBattle battleNode = (NodeBattle) game.getCurrentNode();
                return battleNode.getEnemy();
            }
        } catch (Exception e) {

        }
        return null;
    }
}
