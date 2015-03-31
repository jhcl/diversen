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
public class BoundedBufferCondition_1_4 implements IBuffer {

    private final Lock lock = new ReentrantLock();
    private final Condition volGenoeg = lock.newCondition();
    private final IBuffer buf;

    public BoundedBufferCondition_1_4(IBuffer buf) {
        this.buf = buf;

    }

    @Override
    public void insert(Object item) {

        lock.lock();

        while (this.buf.getLengte() > 250) {
            try {
                volGenoeg.await();

            } catch (InterruptedException ex) {
                Logger.getLogger(BoundedBufferCondition_1_3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.buf.insert(item);
        lock.unlock();

    }

    // consumer calls this method,
    // returns null als buffer leeg is.
    @Override
    public Object remove() {
        Object o = null;
        lock.lock();
        if (buf.getLengte() != 0) o = this.buf.remove();
        volGenoeg.signal();
        lock.unlock();
        return o;
    }

    @Override
    public int getLengte() {
        lock.lock();
        int l = this.buf.getLengte();
        lock.unlock();
        return l;
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
