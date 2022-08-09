import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ThreadPool {

    private List<Thread> threadPool;
    private Queue<Runnable> taskQueue;

    public ThreadPool(int initialSize) {
        threadPool = new ArrayList<>();
        taskQueue = new LinkedList<>();

        System.out.println("creating threads");
        for(int i = 0; i < initialSize; i++)
        {
            threadProc p = new threadProc();
            Thread n = new Thread(p);
            threadPool.add(n);
            n.start();
        }
    }

    public int getSize() {
        return threadPool.size();
    }

    public int getAvailable() {
        for(Thread thread : threadPool)
        {

        }
        return 0;
    }

    public void resize(int newSize) {

    }

    public void destroyPool(){

    }

    public boolean perform(Runnable task)
    {
        System.out.println("task " + task + " enqueued");
        taskQueue.add(task);
        return true;
    }


    private class threadProc implements Runnable
    {
        @Override
        public void run() {
            while(true)
            {
                try {
                    if(taskQueue.size() != 0)
                    {
                        System.out.println();
                    }
                    Runnable task = taskQueue.poll();
                    task.run();
                    System.out.println("task complete");
                }
                catch (NullPointerException ex) {
                    //System.out.println("nullpointer: " + ex.getMessage());
                }

            }
        }
    }

}
