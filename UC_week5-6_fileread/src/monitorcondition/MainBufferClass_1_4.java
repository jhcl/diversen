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
        int nrOfLines = 500;
        IBuffer bb = new UnboundedBuffer();
        File file = new File("testmon3.txt");
        RAFFilesUtils.writeFile(file, lineSize, nrOfLines);
        
        BoundedBufferCondition_1_4 mon = new BoundedBufferCondition_1_4(bb);
        HandleRAFFilesWithThreads hrwt = new HandleRAFFilesWithThreads(file, lineSize, nrOfThreads, mon);
        hrwt.readFile();
        hrwt.verwerk();
        try {
            sleep(30000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainBufferClass_1_4.class.getName()).log(Level.SEVERE, null, ex);
        }
        hrwt.stopAll();
    }
    

}
