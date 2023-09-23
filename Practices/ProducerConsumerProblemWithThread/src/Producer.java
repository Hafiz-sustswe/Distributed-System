import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    BlockingQueue<String>q;
    Thread t;
    String name;
    Producer(BlockingQueue<String>q, String name)
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

                if(q.size() >= 4)
                {
                    System.out.println(name + " queue is full");
                    Thread.sleep(300);
                }
                q.put(name + i);
                System.out.println(name + " created Cake " + (i+1));
                i++;
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
