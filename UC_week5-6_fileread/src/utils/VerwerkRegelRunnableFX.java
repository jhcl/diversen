/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import unboundedbuffer.IBuffer;

/**
 *
 * @author nl08940
 */
public class VerwerkRegelRunnableFX implements Runnable {

    int lineSize;
    IBuffer sharedbuffer;

    public VerwerkRegelRunnableFX(int lineSize, IBuffer sharedbuffer) {
        this.lineSize = lineSize;
        this.sharedbuffer = sharedbuffer;
    }

    @Override
    public void run() {
        if (sharedbuffer.getLengte() > 0) {

            for (int i = 0; i < lineSize; ++i) {
                sharedbuffer.remove();
//                System.out.println("verwerk: " + sharedbuffer.getLengte() + " " + sharedbuffer.getClass());
            }
            System.out.println("Erbij: " + sharedbuffer.erbijGezet() + ", eraf: " + sharedbuffer.eruitGehaald());
        }
    }

}
