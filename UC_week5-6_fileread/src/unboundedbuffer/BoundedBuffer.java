/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unboundedbuffer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class BoundedBuffer implements IBuffer {

    private final BlockingQueue buffer;  // verzameling elementen
    private int eruit, erbij;
    private final int MAX = 1000;

    public BoundedBuffer() {
        this.buffer = new LinkedBlockingQueue(1000);
        eruit = erbij = 0;

    }

    // producer calls this method
    @Override
    public void insert(Object item) {
        try {
            // voeg toe aan eind
            this.buffer.put(item);
        } catch (InterruptedException ex) {
            Logger.getLogger(BoundedBuffer.class.getName()).log(Level.SEVERE, null, ex);
        }
        erbij++;
    }

    // consumer calls this method,
    // returns null als buffer leeg is.
    @Override
    public Object remove() {
        eruit++;
        return this.buffer.poll();

    }

    public int getLengte() {
        return buffer.size();
    }

    public int eruitGehaald() {
        return eruit;
    }

    public int erbijGezet() {
        return erbij;
    }

    public int getMax() {
        return MAX;
    }
}
