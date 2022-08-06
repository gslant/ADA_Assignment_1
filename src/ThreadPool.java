import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ThreadPool {

    private List<Thread> threadPool;
    private Queue<Task> taskQueue;

    public ThreadPool(int initialSize) {
        threadPool = new ArrayList<>();
        taskQueue = new LinkedList<>();

        for(int i = 0; i < initialSize; i++)
        {
            Thread n = new Thread();
            threadPool.add(n);
        }
    }

    public int getSize(){
        return threadPool.size();
    }

    public int getAvailable() {
        return 0;
    }

    public void resize(int newSize) {

    }

    public void destroyPool(){

    }

    public boolean perform(Runnable task)
    {

        return false;
    }

}
