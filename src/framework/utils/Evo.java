package framework.utils;

import java.util.Random;

public class Evo
{
    public static Random rnd;

    public static int genomeLength;

    public static double[][] genes;

    public static int nGenes; //number of different genes.

    public static double[][] currentWeights;

    public static double[][] createNewRandomIndividual()
    {
        double [][] ind = new double[genomeLength][3];
        for(int i = 0; i<genomeLength; ++i)
        {
            int idx = rnd.nextInt(nGenes);
            double targets[] = new double[3];
            for(int j = 0; j < targets.length; ++j)
            {
                targets[j] = genes[idx][j];
            }
            ind[i] = targets;
        }

        return ind;
    }

    public static void init()
    {
        nGenes = 7;
        int i = 0;

        genes = new double[nGenes][3];

        genes[i++] = new double[]{.33,.33,.33};
        //genes[i++] = new double[]{0,0,1};
        genes[i++] = new double[]{.1,.3,.6};
        genes[i++] = new double[]{.1,.6,.3};

        genes[i++] = new double[]{.3,.1,.6};
        //genes[i++] = new double[]{0,.5,.5};
        //genes[i++] = new double[]{0,1,0};
        genes[i++] = new double[]{.3,.6,.1};
        //genes[i++] = new double[]{.5,0,.5};
        //genes[i++] = new double[]{.5,.5,0};
        //genes[i++] = new double[]{1,0,0};
        genes[i++] = new double[]{.6,.1,.3};
        genes[i++] = new double[]{.6,.3,.1};

        genomeLength = 14;

        rnd = new Random();
    }
}
