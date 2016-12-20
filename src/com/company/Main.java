package com.company;

public class Main {

    public static void main(String[] args) {
        int numPlants = 6;
        System.out.println(2.5 % 2.5);
        Problem p = new Problem(numPlants, 330);
        System.out.println(p);
        /*
        Solution s = new Solution(3, p);
        System.out.println(s);*/

        BacterialForaging bF = new BacterialForaging(numPlants, 50,
                100, 5, 20, 20, 2,
                0.1, 0.2, 0.1, 10, 0.1);

        bF.solve(p);
    }
}
