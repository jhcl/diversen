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
public class BoundedBufferCondition_1_2 implements IBuffer {

    private Lock lock = new ReentrantLock();
    private Condition volGenoeg = lock.newCondition();
    private boolean vol = false;

    private final IBuffer buf;

    public BoundedBufferCondition_1_2(IBuffer buf) {
        this.buf = buf;

    }

    @Override
    public  void insert(Object item) {
        lock.lock();
        this.buf.insert(item);
        volGenoeg.signalAll();
        lock.unlock();
    }

    // consumer calls this method,
    // returns null als buffer leeg is.
    @Override
    public Object remove() {
        Object o;
        lock.lock();
        try {
            while (!vol) {
                volGenoeg.await();
                if (buf.getLengte() == 100000) vol = true;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BoundedBufferCondition_1_2.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        System.out.println(buf.getLengte());
        o =  this.buf.remove();
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
