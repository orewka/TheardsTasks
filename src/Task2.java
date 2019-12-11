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
            String[] strings1 = string.replaceAll("[\\p{Punct}[0-9]]", " ").trim().split(" ");
            for (String word : strings1) {
                if (word.length() > 0) {
                    arrayList.add(word);
                }
            }
        }

        int processors = Runtime.getRuntime().availableProcessors();
        Map<String, Integer> res = new TreeMap<>();
        Map<Integer, String> resRev = new TreeMap<>(Collections.reverseOrder());
        List<List<String>> arrayLists = new ArrayList<>();
        for (int i = 0; i < processors; i++) {
            arrayLists.add(arrayList.subList((arrayList.size()/processors)*i, (arrayList.size()/processors)*(i+1)));
        }
        for (List<String> list: arrayLists) {
            Thread tmp = new Thread(new Threads.War(list, res));
            tmp.start();
            try {
                tmp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(res.size());

        for (Map.Entry<String, Integer> entry: res.entrySet()) {
            resRev.put(entry.getValue(), entry.getKey());
        }
        int count = 0;
        for (Map.Entry<Integer, String> entry: resRev.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                count++;
            if (count == 100) break;
        }
    }
}

class Threads {

    private static Map<String, Integer> generate(List<String> arrayList) {
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

    public static class War implements Runnable {
        List<String> arrayList;
        Map<String, Integer> mapStat = new HashMap<>();
        Map<String, Integer> res;

        public War(List<String> arrayList, Map<String, Integer> res) {
            this.arrayList = arrayList;
            this.res = res;
        }

        @Override
        public void run() {
            mapStat = generate(arrayList);
            System.out.println(mapStat.size());
            synchronized (res) {
                for (Map.Entry<String, Integer> entry: mapStat.entrySet()) {
                    if (res.containsKey(entry.getKey()))
                        res.replace(entry.getKey(), res.get(entry.getKey()), res.get(entry.getKey()) + entry.getValue());
                    else res.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}