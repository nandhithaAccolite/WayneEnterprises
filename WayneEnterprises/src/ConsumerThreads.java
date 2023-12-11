import org.w3c.dom.DOMImplementation;

import java.util.ArrayList;
import java.util.List;

public class ConsumerThreads implements Runnable{

    @Override
    public void run() {
        ProblemImplementation i= new ProblemImplementation();
        try {
            i.consume();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        int x=Main.delivereda.size();
        if(x>0) {
            for (int s = 0; s < x; s++) {
                Main.cancelled.add(Main.delivereda.remove(s));
                Main.s=Main.s-250;
            }
        }
        int y=Main.deliveredg.size();
        if(x>0) {
            for (int p = 0; p < y; p++) {
                Main.cancelled.add(Main.deliveredg.remove(p));
                Main.s=Main.s-250;
            }
        }
    }
}
