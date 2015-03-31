/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import static java.lang.Thread.sleep;
import javafx.concurrent.Task;
import unboundedbuffer.IBuffer;

/**
 *
 * @author nl08940
 */
public class VerwerkRegelRunnableFX extends Task {

    int lineSize, grootte;
    IBuffer sharedbuffer;

    public VerwerkRegelRunnableFX(int lineSize, IBuffer sharedbuffer) {
        this.lineSize = lineSize;
        this.sharedbuffer = sharedbuffer;
        grootte = sharedbuffer.getLengte();

    }

    @Override
    public Void call() throws InterruptedException {
        if (lineSize > 0) {
            for (int i = 0; i < lineSize; ++i) {
                if (isCancelled()) {
                    updateMessage("stopped");
                    break;
                }
                updateProgress(i, lineSize);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    if (isCancelled()) {
                        updateMessage("stopped");
                        break;
                    }
                }
                    sharedbuffer.remove();
                }
            }
            return null;
        }

    }
