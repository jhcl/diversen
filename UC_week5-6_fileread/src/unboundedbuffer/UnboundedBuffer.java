/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unboundedbuffer;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class UnboundedBuffer implements IBuffer {

    private Queue buffer;  // verzameling elementen
    private int eruit, erbij;

    public UnboundedBuffer() {
        this.buffer = new LinkedList();
        eruit = erbij = 0;
    }

    // producer calls this method
    @Override
    public synchronized void insert(Object item) {
        // voeg toe aan eind
        this.buffer.add(item);
        erbij++;
    }

    // consumer calls this method,
    // returns null als buffer leeg is.
    @Override
    public synchronized Object remove() {
//        if (buffer.size() > 0) {
            eruit++;
 //       }
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

}
