import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<String>q = new ArrayBlockingQueue<>(4);
        Producer producer1 = new Producer(q,"Producer1");
        Consumer consumer1 = new Consumer(q,"consumer1");

    }
}
