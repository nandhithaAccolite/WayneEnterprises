import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;


public class Main {
    static int s,t=0;
    static List<String> deliveredg = new ArrayList<>();
    static List<String> delivereda = new ArrayList<>();
    static List<String> cancelled = new ArrayList<>();

    static List<String> destination= new ArrayList<>();

    public static void main(String[] args){
        ProducerThreads p =new ProducerThreads();
        ConsumerThreads c= new ConsumerThreads();
        ExecutorService e= Executors.newFixedThreadPool(12);
        for(int i=0;i<5;i++){
            Random r = new Random();
            String d = r.nextBoolean() ? "Gotham" : "Atlanta";
            destination.add(d);
        }
        System.out.println(destination);
        boolean h=true;
        while(true){
            e.submit(p);
            e.submit(p);
            e.submit(p);
            e.submit(p);
            e.submit(p);
            e.submit(p);
            e.submit(p);
            e.submit(c);
            e.submit(c);
            e.submit(c);
            e.submit(c);
            e.submit(c);
        }
    }
}
class ProblemImplementation {
    static BlockingQueue<Integer> b1 = new ArrayBlockingQueue<Integer>(7);
    static BlockingQueue<Integer> b2 = new ArrayBlockingQueue<Integer>(7);

    public void consume() throws InterruptedException {
        int w = 0;
        if (Main.destination.get(Main.t).equals("Gotham")) {
            synchronized (this){
                while (true) {
                    int c = b1.take();
                    w = w + c;
                    Main.s = Main.s + 1000;
                    Main.deliveredg.add("Gotham " + c);
                    if (w > 50 && w <= 300) {
                        if (Main.s >= 20000) {
                            System.out.println(Main.s);
                            System.out.println(Main.deliveredg);
                            System.out.println(Main.delivereda);
                            System.out.println(Main.cancelled);
                            exit(0);
                        }
                        Main.destination.add(Main.t,"Atlanta");
                        Main.t++;
                        break;
                    } else if (w > 300) {
                        b1.add(c);
                        Main.s = Main.s - 1000;
                        Main.deliveredg.remove(Main.deliveredg.size() - 1);
                        break;
                    }
                }}
        } else {
            while (true) {
                int c = b2.take();
                w = w + c;
                Main.s = Main.s + 1000;
                Main.delivereda.add("Atlanta " + c);
                if (w > 50 && w <= 300) {
                    if (Main.s >= 20000) {
                        System.out.println(Main.s);
                        System.out.println(Main.deliveredg);
                        System.out.println(Main.delivereda);
                        System.out.println(Main.cancelled);
                        exit(0);
                    }
                    Main.destination.add(Main.t,"Gotham");
                    Main.t++;
                    break;
                } else if (w > 300) {
                    b2.add(c);
                    Main.s = Main.s - 1000;
                    Main.delivereda.remove(Main.delivereda.size() - 1);
                    break;
                }
            }
        }
    }
    public void produce() throws InterruptedException {
        Random r = new Random();
        synchronized(this){
            String d = r.nextBoolean() ? "Gotham" : "Atlanta";
            int i = r.nextInt(41) + 10;
            if((b1.size() + b2.size()) < 7) {
                if (d.equals("Gotham")) {
                    b1.add(i);
                } else {
                    b2.add(i);
                }
            }
        }
    }
}