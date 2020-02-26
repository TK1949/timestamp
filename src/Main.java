public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Timestamp.currentTimeMillis());
        System.out.println(Timestamp.cacheTimeMillis());

        Thread.sleep(1000);

        System.out.println(Timestamp.currentTimeMillis());
        System.out.println(Timestamp.cacheTimeMillis());
    }
}