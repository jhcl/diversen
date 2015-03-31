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
public class MainBufferClass_1_4 {

    public static void main(String args[]) {
        int nrOfThreads = 10;
        int lineSize = 50;
        IBuffer bb = new BoundedBuffer();
        File file = new File("testmon.txt");
        RAFFilesUtils.writeFile(file, lineSize, 2000);
        
        BoundedBufferMonitor mon = new BoundedBufferMonitor(bb);
        HandleRAFFilesWithThreads hrwt = new HandleRAFFilesWithThreads(file, 50, nrOfThreads, mon);
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
