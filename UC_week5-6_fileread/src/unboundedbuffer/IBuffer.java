package unboundedbuffer;

/**
 * An interface for buffers
 *
 */
public interface IBuffer {

    /**
     * insert an item into the Buffer.
     * 
     */
    public void insert(Object item);

    /**
     * remove an item from the Buffer.
     * 
     */
    public Object remove();
    
    public int getLengte();
    
    public int eruitGehaald();
    
    public int erbijGezet();  
}
