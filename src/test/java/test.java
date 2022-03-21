import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class test {


    @Test
    void MapSort() {
        List<String> hastaglist = Arrays.asList("IT", "IT", "IT", "SPORT", "SPORT", "MOVIE", "STOKE", "STOKE","STOKE" );
        Map<String, Integer> map = new HashMap<>();

        for (String hastag : hastaglist) {
            if (map.containsKey(hastag)) {
                int cnt = map.get(hastag);
                cnt++;
                map.put(hastag, cnt);
            } else {
                map.put(hastag, 1);
            }
        }

//        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
//        entryList.sort(((o1, o2) -> map.get(o2.getKey()) - map.get(o1.getKey())));
//
//        for(Map.Entry<String, Integer> entry : entryList){
//            System.out.println("key : " + entry.getKey() + ", value : " + entry.getValue());
//        }


        final List<String> sortedStats = map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        sortedStats.forEach(System.out::println);
    }
}
