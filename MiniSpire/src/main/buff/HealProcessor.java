package main.buff;

import java.util.List;

public class HealProcessor {
    
    public static int calculateHeal(List<Buff> buffList, int value){
        
        boolean hasLost = buffList.stream().anyMatch(buff -> "Lost".equals(buff.getName()));

        if (hasLost)
            return 0;
        return value;
    }
}
