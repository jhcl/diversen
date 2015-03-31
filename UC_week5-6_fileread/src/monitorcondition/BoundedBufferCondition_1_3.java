/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorcondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import unboundedbuffer.IBuffer;

/**
 *
 * @author nl08940
 */
public class BoundedBufferCondition_1_3 implements IBuffer {

    private final Lock lock = new ReentrantLock();
    private final Condition volGenoeg = lock.newCondition();

    private final IBuffer buf;

    public BoundedBufferCondition_1_3(IBuffer buf) {
        this.buf = buf;

    }

    @Override
    public void insert(Object item) {
        lock.lock();
        this.buf.insert(item);
        while (this.buf.getLengte() == 0) {
            try {
                volGenoeg.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(BoundedBufferCondition_1_3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lock.unlock();

    }

    // consumer calls this method,
    // returns null als buffer leeg is.
    @Override
    public Object remove() {

        lock.lock();
        Object o = this.buf.remove();
        volGenoeg.signal();
        lock.unlock();
        return o;
    }

    @Override
    public int getLengte() {
        return this.buf.getLengte();
    }

    @Override
    public int eruitGehaald() {
        return this.buf.eruitGehaald();
    }

    @Override
    public int erbijGezet() {
        return this.buf.erbijGezet();
    }

}
