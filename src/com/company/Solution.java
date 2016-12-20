package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class Problem {
    double[] maxOutputPerStation;
    double[][] coefficients;
    double targetPower;

    public Problem(int numPowerPlants, double targetPower) {
        this.targetPower = targetPower;
        Random g = new Random(2);
        maxOutputPerStation = new double[numPowerPlants];
        for (int i = 0; i < numPowerPlants; i++) {
            //maxOutputPerStation[i] = Math.floor(50 + Math.abs(g.nextDouble()) * 100);
            maxOutputPerStation[i] = 80;
        }
        coefficients = new double[numPowerPlants][3];
        for (int i = 0; i < numPowerPlants; i++) {
            coefficients[i][0] = Math.floor(1 + Math.abs(g.nextDouble()) * 100) / 100;
            coefficients[i][1] = Math.floor(1 + Math.abs(g.nextDouble()) * 100) / 100;
            coefficients[i][2] = Math.floor(1 + Math.abs(g.nextDouble()) * 100) / 100;
        }
    }

    @Override
    public String toString() {
        return "Problem{" +
                "maxOutputPerStation=" + Arrays.toString(maxOutputPerStation) +
                ", coefficients=" + Arrays.toString(coefficients) +
                ", targetPower=" + targetPower +
                '}';
    }
}

/**
 * Created by bogda on 12/18/2016.
 */
public class Solution implements Comparable {
    int numPowerPlants;
    double[] outputPerStation;
    public double health;
    Problem p;
    public double sumOutputs;

    @Override
    public String toString() {
        return "Solution{" +
                "outputPerStation=" + Arrays.toString(outputPerStation) +
                '}';
    }

    private Solution(int choice, double score, int choice2, double score2, Solution s) {
        this.numPowerPlants = s.numPowerPlants;
        this.p = s.p;
        this.outputPerStation = new double[numPowerPlants];
        sumOutputs = 0;
        for (int i = 0; i < numPowerPlants; i++) {
            this.outputPerStation[i] = s.outputPerStation[i];
            sumOutputs += s.outputPerStation[i];
        }
        sumOutputs += score - this.outputPerStation[choice];
        sumOutputs += score2 - this.outputPerStation[choice2];

        this.outputPerStation[choice] = score;
        this.outputPerStation[choice2] = score2;

        if(Math.abs(sumOutputs - p.targetPower) > 0.11) {
            adjust();
        }
    }

    public Solution(int numPowerPlants, Problem p) {
        this.numPowerPlants = numPowerPlants;
        this.p = p;
        Random g = new Random();
        sumOutputs = 0;
        this.outputPerStation = new double[numPowerPlants];

        for (int i = 0; i < numPowerPlants; i++) {
            this.outputPerStation[i] = Math.abs(g.nextDouble()) * p.maxOutputPerStation[i];
            sumOutputs += this.outputPerStation[i];
        }

        adjust();
    }

    private void adjust() {
        if(sumOutputs == p.targetPower) {
            return;
        }
        if(sumOutputs > p.targetPower) {
            if(sumOutputs - p.targetPower < 1) {
                Random g = new Random();
                int choice = Math.abs(g.nextInt()) % numPowerPlants;
                this.outputPerStation[choice] -= sumOutputs - p.targetPower;
                sumOutputs = p.targetPower;
            }
            Random g = new Random();
            int choice = Math.abs(g.nextInt()) % numPowerPlants;
            if(this.outputPerStation[choice] - 1 >= 0) {
                this.outputPerStation[choice] -= 1;
                sumOutputs -= 1;
            }
        }
        else {
            Random g = new Random();
            int choice = Math.abs(g.nextInt()) % numPowerPlants;
            if(this.outputPerStation[choice] + 1 <= p.maxOutputPerStation[choice]) {
                this.outputPerStation[choice] += 1;
                sumOutputs += 1;
            }
        }
        adjust();
    }

    public double plantCost(int i) {
        return p.coefficients[i][0] * outputPerStation[i] * outputPerStation[i] *
                p.coefficients[i][1] * outputPerStation[i] +
                p.coefficients[i][2];
    }

    public double cost() {
        double sum = 0;
        for (int i = 0; i < numPowerPlants; i++) {
            sum += plantCost(i);
        }
        return sum;
    }

    public double sum() {
        double sum = 0;
        for (int i = 0; i < numPowerPlants; i++) {
            sum += outputPerStation[i];
        }
        return sum;
    }

    public Solution step(double stepSize) {
        Random g = new Random();
        int choice = Math.abs(g.nextInt()) % numPowerPlants;
        int choice2 = Math.abs(g.nextInt()) % numPowerPlants;
        double aux = ((Math.abs(g.nextInt()) % 2) - 0.5) * 2 * stepSize;
        double newValue = outputPerStation[choice] + aux;
        double newValue2 = outputPerStation[choice2] - aux;
        if(newValue > 0 && newValue < p.maxOutputPerStation[choice] && newValue2 > 0 && p.maxOutputPerStation[choice2] > newValue2) {
            return new Solution(choice, newValue, choice2, newValue2, this);
        }
        return step(stepSize);
    }



    @Override
    public int compareTo(Object o) {
        return (int) Math.floor(100 * (this.health - ((Solution)o).health));
    }
}
