import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ThreadPool {

    private List<workerThread> threadPool;
    private Queue<Task> taskQueue;

    public ThreadPool(int initialSize) {
        threadPool = new ArrayList<>();
        taskQueue = new LinkedList<>();

        System.out.println("creating threads");
        for(int i = 0; i < initialSize; i++)
        {
            workerThread worker = new workerThread();
            Thread t = new Thread(worker);
            threadPool.add(worker);
            t.start();
        }
    }

    public int getSize() {

        return threadPool.size();
    }

    public int getAvailable() {
        int numAvailable = 0;
        for(workerThread t : threadPool)
        {
            if(t.available == true) {
                numAvailable++;
            }
        }
        return numAvailable;
    }

    public void resize(int newSize) {
        if(newSize > getSize()) //if increasing in size
        {
            int noOfNewThreads = newSize - getSize();
            for(int i = 0; i < noOfNewThreads; i++)
            {
                workerThread p = new workerThread();
                Thread n = new Thread(p);
                threadPool.add(p);
                n.start();
            }
        }
        else if(newSize < getSize()) //if decreasing in size
        {
            int toRemove = getSize() - newSize;
            while(getSize() != newSize)
            {
                workerThread toKill = threadPool.get(0);
                if(toKill.available == true)
                {
                    toKill.setStopRequested();
                }
            }
        }
        else{
            System.out.println("Requested size is the same as current size");
        }
    }

    public void destroyPool(){
        for(workerThread t : threadPool)
        {
            t.setStopRequested();
            System.out.println("threads destroyed");
        }
    }

    public boolean perform(Task task)
    {
        synchronized (taskQueue) {
            System.out.println("task " + task.getId() + " enqueued");
            taskQueue.add(task);
            taskQueue.notifyAll();
            //System.out.println("available threads: " + getAvailable());
        }
        return true;
    }


    private class workerThread implements Runnable
    {
        private boolean stopRequested = false;
        public boolean available;
        @Override

        //queue can be monitor
        //queue notifies when task added
        public void run() {
            available = true;
            while(!stopRequested ||  taskQueue.size() != 0)
            {
                synchronized (taskQueue) {
                    try {
                        System.out.println("thread " + threadPool.indexOf(this) + " waiting for notification");
                        taskQueue.wait();
                    }
                    catch (NullPointerException ex) {
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread " + threadPool.indexOf(this) + " notified now");
                    try {
                        Task task = taskQueue.poll();
                        available = false;
                        task.run();
                        System.out.println("completed task: " + task.getId());
                    }catch(NullPointerException ex){}
                }
            }
        }

        public void setStopRequested()
        {
            stopRequested = true;
            System.out.println("stop requested, thread: " +threadPool.indexOf(this));
        }
    }

}
