package main.buff;

import java.util.List;

public class HealProcessor {
    
    public static int calculateHeal(List<Buff> buffList, int value){
        
        processEnshroud(buffList);
        boolean hasLost = buffList.stream().anyMatch(buff -> "Lost".equals(buff.getName()));

        if (hasLost)
            return 0;
        return value;
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
