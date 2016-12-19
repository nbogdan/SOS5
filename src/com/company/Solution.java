package com.company;

import java.util.Random;

class Problem {
    double[] outputPerStation;

    public Problem(int numPowerPlants) {
        Random g = new Random();
        for (int i = 0; i < numPowerPlants; i++) {
            outputPerStation[i] = Math.floor(100 + Math.abs(g.nextDouble()) * 100);
        }
    }
}

/**
 * Created by bogda on 12/18/2016.
 */
public class Solution implements Comparable {
    int numPowerPlants;
    double[][] coefficients;
    double[] outputPerStation;
    public double health;



    public Solution(int numPowerPlants, double[] outputPerStation) {
        this.numPowerPlants = numPowerPlants;
        coefficients = new double[numPowerPlants][3];
        this.outputPerStation = outputPerStation;

        Random g = new Random();
        for (int i = 0; i < numPowerPlants; i++) {
            coefficients[i][0] = Math.floor(1 + Math.abs(g.nextDouble()) * 10);
            coefficients[i][1] = Math.floor(1 + Math.abs(g.nextDouble()) * 10);
            coefficients[i][2] = Math.floor(1 + Math.abs(g.nextDouble()) * 10);
        }
    }

    public double plantCost(int i) {
        return coefficients[i][0] * outputPerStation[i] * outputPerStation[i] *
                coefficients[i][1] * outputPerStation[i] +
                coefficients[i][2];
    }

    public double cost() {
        double sum = 0;
        for (int i = 0; i < numPowerPlants; i++) {
            sum += plantCost(i);
        }
        return sum;
    }

    @Override
    public int compareTo(Object o) {
        return (int) Math.floor(100 * (this.health - ((Solution)o).health));
    }
}
