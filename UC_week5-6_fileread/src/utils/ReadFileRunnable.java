/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import timeutil.*;
import unboundedbuffer.*;

/**
 * deze runnable leest regels uit een file, beginnend met regel 'firstline' TOT
 * (niet including) 'lastline'
 *
 * @author erik
 */
public class ReadFileRunnable implements Runnable {

    private RandomAccessFile raf;
    private final int lineSize;
    private final long firstline;
    private final long lastline;
    private final byte[] buffer;
    IBuffer sharedbuffer;
    BufferedReader reader;

    TimeStamp tijd = new TimeStamp();

    /**
     * Runnable die een random access file verwerkt van (en met) gegeven
     * firstline tot (en zonder) lastline. regels worden genummer van 0 en
     * hoger.
     *
     * @param aFile te openen file
     * @param lineSize regellengte in de file
     * @param firstline eerste regel om te lezen. regelnummers beginne
     * @param lastline regel tot waar gelezen wordt. deze regel zelf wordt niet
     * gelezen.
     */
    public ReadFileRunnable(File aFile, int lineSize, long firstline, long lastline, IBuffer sharedbuffer) {
        try {
            this.raf = new RandomAccessFile(aFile, "r");
        } catch (FileNotFoundException ex) {
            this.raf = null;
        }
        this.sharedbuffer = sharedbuffer;
        this.lineSize = lineSize;
        this.firstline = firstline;
        this.lastline = lastline;
        this.buffer = new byte[(int) (this.lastline - this.firstline) * lineSize];
    }

    @Override
    // lees tot laatste regel en verwerk elke regel (nu gesimuleerd door sleep();
    /**
     * run method die de file daadwerkelijk verwerkt van regel firstline tot
     * regel lastline. geeft meldingen op System.out die: 1. aangeeft welke
     * thread, welke regels gaat verwerken 2. aangeeft welke regels verwerkt
     * zijn. 3. meldingen geeft bij interrupt van de thread.
     *
     */
    public void run() {
        try {
            System.out.println("thread " + Thread.currentThread().getId() + "running");
            tijd.setBegin();
            if (this.raf != null) {
                long nrOfLinesToRead = this.lastline - this.firstline;
                this.raf.seek(this.firstline * this.lineSize);

                this.raf.read(this.buffer);
//                System.out.println("in buffer: " + buffer.length);
                
                // verwerk buffer
                while (nrOfLinesToRead > 0) {

                    for (int i = 0; i < lineSize; ++i) {
                        sharedbuffer.insert(buffer[i + (int) lineSize * ((int) nrOfLinesToRead - 1)]);
                    }
                    // update regelteller
                    nrOfLinesToRead--;
                }
                tijd.setEnd();
                

                raf.close();
                System.out.println("Erbij: " + sharedbuffer.erbijGezet() + ", eraf: " + sharedbuffer.eruitGehaald());
                //
                System.out.print("thread " + Thread.currentThread().getId() + " ready. Time: " + tijd.toString());
            }

        } catch (IOException ex) {
            try {
                System.out.println("error at offset " + raf.getFilePointer());
            } catch (IOException ex1) {
                System.out.println("error at file");
            }
        } finally {
        }
    }
}
