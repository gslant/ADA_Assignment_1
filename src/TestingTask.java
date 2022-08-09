import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class TestingTask<String,F> extends Task
{
    private F progress;
    private String s;

    public TestingTask(String E, Object F) {
        super(E);
        this.s = E;
    }

    @Override
    public void run() {
        CharacterIterator iter = new StringCharacterIterator((java.lang.String) s);
        int numberOfTs = 0;

        while(iter.current() != CharacterIterator.DONE){
            if(iter.current() == 'T' || iter.current() == 't')
            {
                numberOfTs++;
            }
            iter.next();
        }

        notifyAll("no of t's = " + numberOfTs);
    }

}
