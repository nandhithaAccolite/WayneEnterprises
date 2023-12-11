public class ProducerThreads implements Runnable{

    @Override
    public void run() {
        ProblemImplementation i= new ProblemImplementation();
        try {
            i.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
