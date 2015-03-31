/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorcondition;

import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import unboundedbuffer.*;
import utils.HandleRAFFilesWithThreads;
import utils.RAFFilesUtils;

/**
 *
 * @author nl08940
 */
public class MainBufferClass_1_2 {

    public static void main(String args[]) {
        int nrOfThreads = 10;
        int lineSize = 50;
        IBuffer bb = new UnboundedBuffer();
        File file = new File("testmon.txt");
        RAFFilesUtils.writeFile(file, lineSize, 2000);
        
        BoundedBufferCondition_1_2 mon = new BoundedBufferCondition_1_2(bb);
        HandleRAFFilesWithThreads hrwt = new HandleRAFFilesWithThreads(file, lineSize, nrOfThreads, mon);
        hrwt.readFile();
        hrwt.verwerk();
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainBufferClass_1_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        hrwt.stopAll();
    }
    

}
