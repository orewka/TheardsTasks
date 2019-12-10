public class Task1 {
    volatile static boolean test = true;
    public static void main(String[] args) {
        Thread thread1 = new Thread(()->{
            while (true) {
                if (test) {
                    System.out.println("thread1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test = !test;
                }
            }
        });
        Thread thread2 = new Thread(()->{
            while (true) {
                if (!test) {
                    System.out.println("thread2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test = !test;
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}