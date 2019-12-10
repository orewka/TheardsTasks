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
        TreeMap<String, Integer> res = new TreeMap<>();
        List<String> arrayList1 = arrayList.subList(0, arrayList.size()/3);
        List<String> arrayList2 = arrayList.subList(arrayList.size()/3, (arrayList.size()/3)*2);
        List<String> arrayList3 = arrayList.subList((arrayList.size()/3)*2, arrayList.size());
        Thread war1 = new Thread(new Theards.War1(arrayList1));
        Thread war2 = new Thread(new Theards.War2(arrayList2));
        Thread war3 = new Thread(new Theards.War3(arrayList3));
        war1.start();
        war2.start();
        war3.start();
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

    static class War1 implements Runnable {
        List<String> arrayList;
        Map<String, Integer> mapStat = new HashMap<>();

        public War1(List<String> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public void run() {
            mapStat = generate(arrayList);
            System.out.println(mapStat.size());
        }
    }

    static class War2 implements Runnable {
        List<String> arrayList;
        Map<String, Integer> mapStat = new HashMap<>();

        public War2(List<String> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public void run() {
            mapStat = generate(arrayList);
            System.out.println(mapStat.size());
        }
    }

    static class War3 implements Runnable {
        List<String> arrayList;
        Map<String, Integer> mapStat = new HashMap<>();

        public War3(List<String> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public void run() {
            mapStat = generate(arrayList);
            System.out.println(mapStat.size());
        }
    }
}