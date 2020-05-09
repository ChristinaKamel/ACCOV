package net.cofares.ljug.moniteurs;

public class Compteur2En2 implements Runnable{

    private final int DEBUT;
    private final int FIN;

    private Object lock;

    private static boolean tourPair;

    public Compteur2En2(int d, int f, Object lock) {
        DEBUT=d;
        FIN=f;
        this.lock = lock;
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
        synchronized (lock) {
            while (!tourPair) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tourPair = false;
            System.out.printf("%d ", n);
            lock.notifyAll();
        }
    }

    private void printImpair (int n) {
        synchronized (lock) {
            while (tourPair) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tourPair = true;
            System.out.printf("%d ", n);
            lock.notifyAll();
        }
    }

}