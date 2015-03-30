/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorcondition;

import unboundedbuffer.IBuffer;

/**
 *
 * @author nl08940
 */
public class BoundedBufferCondition implements IBuffer {
    private final IBuffer buf;
    public BoundedBufferCondition(IBuffer buf){
        this.buf = buf;
    }
    
    @Override
    public synchronized void insert(Object item) {
        this.buf.insert(item);
    }
    
    // consumer calls this method,
    // returns null als buffer leeg is.
    @Override
    public  Object remove() {
        return this.buf.remove();
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
