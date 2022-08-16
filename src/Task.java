//Byron Goldsack
//20111390

import java.util.ArrayList;
import java.util.List;

public abstract class Task<E,F> implements Runnable {

    private int id;
    private List<TaskObserver<F>> observers;

    public Task(E param) {
        this.id = UniqueIdentifier.getInstance().getIdentifier();
        observers = new ArrayList();
    }

    public int getId(){
        return this.id;
    }

    public abstract void run();

    public void addListener(TaskObserver<F> o){
        observers.add(o);
    }

    public void removeListener(TaskObserver<F> o) {
        observers.remove(o);
    }

    protected void notifyAll(F progress) {
        for (TaskObserver<F> observer : observers) {
            observer.update(progress);
        }
    }
}
