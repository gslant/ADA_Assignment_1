public class UniqueIdentifier {
    private static UniqueIdentifier instance;
    private int counter;

    private UniqueIdentifier()
    {
        counter = 0;
    }

    public static UniqueIdentifier getInstance(){
        if(instance == null)
        {
            synchronized (UniqueIdentifier.class)
            {
                if(instance == null){
                    instance = new UniqueIdentifier();
                }
            }
        }
        return instance;
    }

    public synchronized int getIdentifier()
    {
        return counter++;
    }
}
