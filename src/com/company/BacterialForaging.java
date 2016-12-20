package com.company;

import java.util.*;

/**
 * Created by bogda on 12/18/2016.
 */
public class BacterialForaging {
    int problemSize, numberOfCells, numElimDispersal, numReprod, numChemotaxis, numSwim;
    double stepSize;
    double dAttract, wAttract, hRepellant, wRepellant, probOfElimDispersal;

    List<Solution> cells;
    
    public BacterialForaging(int problemSize, int numberOfCells, int numElimDispersal, int numReprod,
                             int numChemotaxis, int numSwim, double stepSize, double dAttract, double wAttract,
                             double hRepellant, double wRepellant, double probOfElimDispersal) {
        this.problemSize = problemSize;
        this.numberOfCells = numberOfCells;
        this.numElimDispersal = numElimDispersal;
        this.numReprod = numReprod;
        this.numChemotaxis = numChemotaxis;
        this.numSwim = numSwim;
        this.stepSize = stepSize;
        this.dAttract = dAttract;
        this.wAttract = wAttract;
        this.hRepellant = hRepellant;
        this.wRepellant = wRepellant;
        this.probOfElimDispersal = probOfElimDispersal;
    }

    public double interaction(Solution s) {
        double sum = 0, sumB = 0;
        for (int i = 0; i < cells.size(); i++) {
            double auxSum = 0;
            for (int j = 0; j < problemSize; j++) {
                auxSum = (s.outputPerStation[j] - cells.get(i).outputPerStation[j]) * (s.outputPerStation[j] - cells.get(i).outputPerStation[j]);
            }
            sum += -dAttract * Math.exp(- wAttract * auxSum);
        }
        for (int i = 0; i < cells.size(); i++) {
            double auxSum = 0;
            for (int j = 0; j < problemSize; j++) {
                auxSum = (s.outputPerStation[j] - cells.get(i).outputPerStation[j]) * (s.outputPerStation[j] - cells.get(i).outputPerStation[j]);
            }
            sum += hRepellant * Math.exp(- wRepellant * auxSum);
        }
        return sum;
    }

    public void chemotaxisAndSwim() {
        for (int i = 0; i < cells.size(); i++) {
            double fitnessOld = cells.get(i).cost() + interaction(cells.get(i));
            cells.get(i).health = fitnessOld;
            Solution newCell;
            for (int j = 0; j < numSwim; j++) {
                newCell = cells.get(i).step(stepSize);
                double fitnessNew = newCell.cost() + interaction(newCell);
                if(fitnessNew > fitnessOld) {
                    j = numSwim;
                } else {
                    newCell.health = cells.get(i).health;
                    cells.remove(i);
                    cells.add(i, newCell);
                }
            }
        }
    }

    public Solution solve(Problem p) {
        Solution bestCell = new Solution(problemSize, p);
        Random g = new Random();
        cells = new LinkedList<>();

        for (int i = 0; i < numberOfCells; i++) {
            cells.add(new Solution(problemSize, p));
        }
        System.out.println(cells);

        for (int i = 0; i < numElimDispersal; i++) {
            for (int j = 0; j < numReprod; j++) {

                for (int k = 0; k < numChemotaxis; k++) {
                    chemotaxisAndSwim();
                }

                for (Solution sol : cells) {
                    if (sol.cost() < bestCell.cost()) {
                        bestCell = sol;
                    }
                }
                Collections.sort(cells);
                List<Solution> newPop = new LinkedList<>();
                double sum = 0;
                for (int k = cells.size() / 2; k < cells.size(); k++) {
                    newPop.add(cells.get(k));
                    newPop.add(cells.get(k));
                    sum += 2 * cells.get(k).cost();
                }

                if (newPop.size() < cells.size()) {
                    newPop.add(cells.get(cells.size() / 2));
                    newPop.add(cells.get(cells.size() / 2));
                    sum += 2 * cells.get(cells.size() / 2).cost();
                }
                cells = newPop;
                System.out.println(bestCell.cost() + " " + ((int)sum / 10000));
            }
            for (int j = 0; j < cells.size(); j++) {
                if(Math.abs(g.nextDouble()) < probOfElimDispersal) {
                    cells.remove(j);
                    cells.add(j, new Solution(problemSize, p));
                }
            }

        }
        return bestCell;
    }
}
