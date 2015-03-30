/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import unboundedbuffer.*;

/**
 * deze methode verwerkt random access files door verschillende threads ieder
 * een verschillend deel van de file te laten verwerken.
 *
 * @author erik
 */
public class HandleRAFFilesWithThreads implements I_HandleRAFFiles {

    private File file = null;
    private int lineSize;
    private int nrOfThreads;
    ExecutorService exser;
    ScheduledExecutorService st;
//    ExecutorService st;
    protected IBuffer sharedbuffer;

    public HandleRAFFilesWithThreads() {
    }
    
    public HandleRAFFilesWithThreads(File aFile, int lineSize, int nrOfThreads, IBuffer shared) {
        exser = Executors.newFixedThreadPool(nrOfThreads);
        
        st = Executors.newSingleThreadScheduledExecutor();
//        st = Executors.newFixedThreadPool(10);
        this.file = aFile;
        this.lineSize = lineSize;
        this.nrOfThreads = nrOfThreads;
        this.sharedbuffer = shared;
    }

    @Override
    /**
     * maak threads aan om concurrent file te lezen.
     */
    public void readFile(File file, int lineSize) {
        // berekening in hoeveel blokken de file gehakt moet worden en 
        // dus hoeveel threads er nodig zijn.
        //
        // bereken hoe groot de aantallen regels per thread zijn
        long nrOfLines = this.file.length() / lineSize;
        // alle threads krijgen gelijke blokken als dat mogelijk is,
        // anders krijgt 1 extra thread de restjes
        long nrOfLinesPerBlock = nrOfLines / nrOfThreads;

        // maak threads
        int lineTeller = 0;

        do {
            exser.execute(new ReadFileRunnable(
                    this.file, this.lineSize, lineTeller, lineTeller + nrOfLinesPerBlock, sharedbuffer));

            // verhoog de lineTeller
            lineTeller += nrOfLinesPerBlock;
        } while (lineTeller < nrOfLines);
//        exser.shutdown();

        // print activiteit van threads
    }

    public void verwerk() {
        st.scheduleAtFixedRate(new VerwerkRegelRunnable(lineSize, sharedbuffer), 0, 1, MILLISECONDS);
//        st.execute(new VerwerkRegelRunnable(lineSize, sharedbuffer));
    }
    
    public void stopVerwerk() {
        st.shutdown();
    }
    
    public void stopAll() {
        st.shutdown();
        exser.shutdown();
    }

}
