package net.cofares.ljug.moniteurs;

public class Exemple {
    public static void main(String ...args) {
        final Object lock = new Object();
        Runnable paire = new Compteur2En2(2, 101, lock);
        Runnable impaire = new Compteur2En2(1, 100, lock);

        Thread t1=new Thread(paire, "paire");
        Thread t2=new Thread(impaire, "impaire");

        t1.start();
        t2.start();
    }
}
