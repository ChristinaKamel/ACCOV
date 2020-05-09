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
public class Exemple {
    public static void main(String ...args) {
        ReentrantLock lock = new ReentrantLock();
        Condition tourPairCond = lock.newCondition();
        Condition tourImpairCond = lock.newCondition();

        Runnable paire = new Compteur2En2(2, 101, lock, tourPairCond, tourImpairCond);
        Runnable impaire = new Compteur2En2(1, 100, lock, tourPairCond, tourImpairCond);
        
        Thread t1=new Thread(paire, "paire");
        Thread t2=new Thread(impaire, "impaire");

        t1.start();
        t2.start();
    }
}
