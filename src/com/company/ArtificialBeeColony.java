package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bogda on 12/20/2016.
 */
public class ArtificialBeeColony {
    public int problemSize;
    public int beePopSize;
    public int numFoodSources;
    public int maxTrials;
    public int maxForagingAttempts;

    public ArrayList<Solution> foodSources;
    public ArrayList<Solution> solutions;
    public Solution bestSolution;
    public Problem p;

    public ArtificialBeeColony(int n, int beePopSize, Problem p) {
        this.problemSize = n;
        this.beePopSize = beePopSize;
        this.numFoodSources = beePopSize/2;
        this.maxTrials = 50;
        this.maxForagingAttempts = 1000;
        bestSolution = null;
        this.p = p;
        foodSources = new ArrayList<>();
        solutions = new ArrayList<>();
    }

    public void init() {
        for(int i = 0; i < numFoodSources; i++) {
            Solution sol = new Solution(problemSize, p);

            foodSources.add(sol);
        }
    }

    public boolean algorithm() {
        Random g = new Random();
        boolean done = false;
        int epoch = 0;

        init();
    }
}
