package com.company;

import java.util.*;

/**
 * Created by bogda on 12/18/2016.
 */
public class BacterialForaging {
    int problemSize, numberOfCells, numElimDispersal, numReprod, numChemotaxis, numSwim;
    int stepSize;
    double dAttract, wAttract, hRepellant, wRepellant, probOfElimDispersal;

    List<Solution> cells;
    
    public BacterialForaging(int problemSize, int numberOfCells, int numElimDispersal, int numReprod,
                             int numChemotaxis, int numSwim, int stepSize, double dAttract, double wAttract,
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
        return 0;
    }

    public void chemotaxisAndSwim() {
        for (int i = 0; i < cells.size(); i++) {
            double fitness = cells.get(i).cost() + interaction(cells.get(i));
            cells.get(i).health = fitness;
            Solution newCell = new Solution(cells.get(i).numPowerPlants, cells.get(i).outputPerStation);

            for (int j = 0; j < numSwim; j++) {

            }

        }
    }

    public Solution solve(Problem p) {
        Solution bestCell = new Solution(problemSize, p.outputPerStation);
        Random g = new Random();

        for (int i = 0; i < numberOfCells; i++) {
            cells.add(new Solution(problemSize, p.outputPerStation));
        }

        for (int i = 0; i < numElimDispersal; i++) {
            for (int j = 0; j < numReprod; j++) {
                for (int k = 0; k < numChemotaxis; k++) {
                    chemotaxisAndSwim();
                }

                for (Solution sol : cells) {
                    if(sol.cost() < bestCell.cost()) {
                        bestCell = sol;
                    }
                }
            }
            Collections.sort(cells);
            List<Solution> newPop = new LinkedList<>();
            for (int j = 0; j < cells.size() / 2; j++) {
                newPop.add(cells.get(j));
                newPop.add(cells.get(j));
            }
            if(newPop.size() < cells.size()) {
                newPop.add(cells.get(cells.size() / 2));
                newPop.add(cells.get(cells.size() / 2));
            }

            for (int j = 0; j < cells.size(); j++) {
                if(Math.abs(g.nextDouble()) < probOfElimDispersal) {
                    cells.remove(j);
                    cells.add(j, new Solution(problemSize, p.outputPerStation));
                }
            }
        }
        return bestCell;
    }
}
