import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    BlockingQueue<String>q;
    Thread t;
    String name;
    Consumer(BlockingQueue<String>q, String name)
    {
        this.q = q;
        this.name = name;
        t = new Thread(this, name);
        t.start();
    }
    @Override
    public void run() {
        int i = 0;
        while(true)
        {
            try {
                if(q.size() == 0)
                {
                    System.out.println(name + " queue is empty ");
                    Thread.sleep(2000);

                }
                System.out.println(name + " got " + q.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
