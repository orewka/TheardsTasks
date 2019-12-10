import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Task2 {
    public static void main(String[] args) {
        File file = new File("src/wp.txt");
        List<String> strings = null;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> arrayList = new ArrayList<>();
        for (String string : strings) {
            String[] strings1 = string.replaceAll("\\p{Punct}", " ").trim().split(" ");
            for (String word : strings1) {
                if (word.length() > 0) {
                    arrayList.add(word);
                }
            }
        }
        int processors = Runtime.getRuntime().availableProcessors();
        TreeMap<String, Integer> res = new TreeMap<>();
        List<List<String>> arrayLists = new ArrayList<>();
        for (int i = 0; i < processors; i++) {
            arrayLists.add(arrayList.subList((arrayList.size()/processors)*i, (arrayList.size()/processors)*(i+1)));
        }
        for (List<String> list: arrayLists) {
            Thread tmp = new Thread(new Theards.War(list, res));
            tmp.start();
        }
        System.out.println(res.size());
        // как записать в treemap?
    }
}

class Theards {

    public static Map<String, Integer> generate(List<String> arrayList) {
        Map<String, Integer> mapStat = new HashMap<>();
        for (String word : arrayList) {
            String tmp = word.toLowerCase();
            int count;
            if (mapStat.containsKey(tmp)) {
                count = mapStat.get(tmp);
                mapStat.replace(tmp,count,++count);
            } else {
                count = 1;
                mapStat.put(tmp, count);
            }
        }
        return mapStat;
    }

    static class War implements Runnable {
        List<String> arrayList;
        Map<String, Integer> mapStat = new HashMap<>();
        TreeMap<String, Integer> res = new TreeMap<>();

        public War(List<String> arrayList, TreeMap<String, Integer> res) {
            this.arrayList = arrayList;
            this.res = res;
        }

        @Override
        public void run() {
            mapStat = generate(arrayList);
            System.out.println(mapStat.size());
            for (Map.Entry<String, Integer> entry: mapStat.entrySet()) {
                res.put(entry.getKey(), entry.getValue());
            }
        }
    }
}