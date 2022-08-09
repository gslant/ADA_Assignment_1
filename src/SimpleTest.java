import static java.lang.Integer.valueOf;

public class SimpleTest {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(1);
        String s = "Hello One Two Three Four Five";
        String sa = "Hello One TTwo Three Four Five";
        String sb = "Hello One Two Three Four FTTive";
        String sc = "Hello OnTTTe Two Three Four Five";
        Integer i = valueOf(10);
        TestingTask t = new TestingTask(s,i);
        TestingTask ta = new TestingTask(sa,i);
        TestingTask tb = new TestingTask(sb,i);
        TestingTask tc = new TestingTask(sc,i);

        testGui gui = new testGui();
        t.addListener(gui.t);
        ta.addListener(gui.ta);
        tb.addListener(gui.tb);
        tc.addListener(gui.tc);

        pool.perform(t);
        pool.perform(ta);
        pool.perform(tb);
        pool.perform(tc);
    }

}
