/*
GNU LESSER GENERAL PUBLIC LICENSE V3.0 
https://www.gnu.org/licenses/lgpl-3.0.fr.html 
 */
package net.cofares.ljug.moniteurs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Pascal Fares <pascal.fares at cofares.net>
 */
public class Compteur2En2 implements Runnable{

    private final ReentrantLock lock;
    private final Condition tourPairCond, tourImpairCond;

    private static boolean tourPair;

    private final int DEBUT;
    private final int FIN;
    
    public Compteur2En2(int d, int f, ReentrantLock lock, Condition tourPairCond, Condition tourImpairCond) {
        DEBUT=d;
        FIN=f;
        this.lock = lock;
        this.tourPairCond = tourPairCond;
        this.tourImpairCond = tourImpairCond;
    }

    public void run () {
        for (int i=DEBUT; i<FIN; i +=2) {
            if (i % 2 == 0) {
                printPair(i);
            } else {
                printImpair(i);
            }
        }
    }

    private void printPair(int n) {
        lock.lock();
        try {
            while (!tourPair) {
                tourPairCond.await();
            }
            tourPair = false;
            System.out.printf("%d ", n);
            tourImpairCond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void printImpair(int n) {
        lock.lock();
        try {
            while (tourPair) {
                tourImpairCond.await();
            }
            tourPair = true;
            System.out.printf("%d ", n);
            tourPairCond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
