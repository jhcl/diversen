/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * utility class om grote text files met vaste regellengte te maken.
 *
 * @author erik
 */
public class RAFFilesUtils {

    private static final int DEFAULT_LINESIZE = 20;
    private static final int DEFAULT_LINES = 5000;

     /**
     * Maak nieuwe FILE object met onderliggend bestand bigFile.txt 
     * met gegeven aantal regels en
     * regellengte. Verwijder oude versie indien deze bestaat.
     * @param linesize
     * @param nroflines
     * @return File object.
     */
    public static File createFile(int linesize, int nroflines) {
        // 
        // of creer indien deze niet bestaat
        File file = new File("/bigFile.txt");
        if (file.exists()) {
            // verwijder file eerst
            file.delete();
        }
        System.out.println("writing file ");
        writeFile(file, linesize, nroflines);

        return file;
    }
    
    /**
     * Hulpmethode om random access file te vullen met random waarden per regel.
     *
     * @param aFile de file om te creeren en vullen
     * @param lineSize lengte van de regels in de file
     * @param nrOfLines aantal te schrijven regels in de file
     */
    public static void writeFile(File aFile, int lineSize, int nrOfLines) {
        // check input
        if(lineSize <=1){
            lineSize = DEFAULT_LINESIZE;
        }
        if(nrOfLines <= 0){
            nrOfLines = DEFAULT_LINES;
        }
        // maak file en vul.
        byte[] buffer = new byte[lineSize];
        RandomAccessFile raf = null;
        // create random access file.
        raf = openRAFforWriting(aFile);

        try {
            if (raf != null) {
                // ga naar eerste regel
                long nrOfLinesToWrite = nrOfLines;
                raf.seek(0);
                // schrijf
                while (nrOfLinesToWrite > 0) {
                    // create random line
                    createRandomLine(buffer);
                    raf.write(buffer);

                    //
                    nrOfLinesToWrite--;
                }
                
            }

        } catch (IOException ex) {
            try {
                if (raf != null) {
                    System.out.println("error at offset " + raf.getFilePointer());
                }
            } catch (IOException ex1) {
                System.out.println("error at file");
            }
        } finally {
            try {
                raf.close();
            } catch (IOException ex) {
                Logger.getLogger(RAFFilesUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static RandomAccessFile openRAFforWriting(File aFile) {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(aFile, "rw");
        } catch (FileNotFoundException ex) {
            raf = null;
        }
        return raf;
    }

    private static void createRandomLine(byte[] buffer) {
        // schrijf een hele random regel
        Arrays.fill(buffer, (byte) Math.round((float) Math.random() * 256));
//        buffer[buffer.length - 1] = '\n';
    }

   
}
