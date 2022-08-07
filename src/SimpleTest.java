import static java.lang.Integer.valueOf;

public class SimpleTest {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(10);
        String s = "Hello One Two Three Four Five";
        Integer i = valueOf(10);
        TestingTask t = new TestingTask(s,i);

        pool.perform(t);
    }
}
