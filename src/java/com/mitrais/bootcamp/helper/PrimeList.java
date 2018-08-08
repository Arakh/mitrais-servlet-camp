/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.bootcamp.helper;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Aditia_RS458
 */
public class PrimeList implements Runnable {

    private ArrayList<BigInteger> primesFound;
    private int numPrimes, numDigits;

    /**
     * Finds numPrimes prime numbers, each of which is numDigits long or longer.
     * You can set it to return only when done, or have it return immediately,
     * and you can later poll it to see how far it has gotten.
     * @param numPrimes
     * @param numDigits
     * @param runInBackground
     */
    public PrimeList(int numPrimes, int numDigits, boolean runInBackground) {
        primesFound = new ArrayList<>(numPrimes);
        this.numPrimes = numPrimes;
        this.numDigits = numDigits;
        if (runInBackground) {
            Thread t = new Thread(this);
            // Use low priority so you don't slow down server.
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        } else {
            run();
        }
    }

    @Override
    public void run() {
        BigInteger start = Prime.random(numDigits);
        for (int i = 0; i < numPrimes; i++) {
            start = Prime.nextPrime(start);
            synchronized (this) {
                primesFound.add(start);
            }
        }
    }

    public synchronized boolean isDone() {
        return (primesFound.size() == numPrimes);
    }

    @SuppressWarnings("unchecked")
    public synchronized ArrayList<BigInteger> getPrimes() {
        if (isDone()) {
            return (primesFound);
        } else {
            return ((ArrayList<BigInteger>) primesFound.clone());
        }
    }

    public int numDigits() {
        return (numDigits);
    }

    public int numPrimes() {
        return (numPrimes);
    }

    public synchronized int numCalculatedPrimes() {
        return (primesFound.size());
    }
}
