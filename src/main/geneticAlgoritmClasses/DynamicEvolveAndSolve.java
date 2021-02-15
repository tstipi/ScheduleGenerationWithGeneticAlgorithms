package main.geneticAlgoritmClasses;

import main.classes.*;
import org.jgap.*;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;

import java.util.ArrayList;
import java.util.Map;

public class DynamicEvolveAndSolve {

    public static final double FITNESS_VALUE = 161;
    static final int EVOLUTION_REVOLUTIONS = 500;
    static final int POPULATION_SIZE = 100;
    public static  ArrayList<ClassGroup> classGroupsArrayList;
    public static ArrayList<Course> coursesArrayList;
    public static ArrayList<Lecturer> lecturersArrayList;
    public static ArrayList<LectureHall> lectureHallsArrayList;

    public static void execute(ArrayList<ClassGroup> classGroupsArrayList,
                               ArrayList<Course> coursesArrayList, ArrayList<Lecturer> lecturersArrayList,
                               ArrayList<LectureHall> lectureHallsArrayList) {

        DynamicEvolveAndSolve.classGroupsArrayList = classGroupsArrayList;
        DynamicEvolveAndSolve.coursesArrayList = coursesArrayList;
        DynamicEvolveAndSolve.lecturersArrayList = lecturersArrayList;
        DynamicEvolveAndSolve.lectureHallsArrayList = lectureHallsArrayList;

        Configuration.reset();
        Configuration conf = new DefaultConfiguration();

        DynamicSchedule fitnessFunction = new DynamicSchedule();
        
        try {

            conf.setFitnessFunction(fitnessFunction);
            conf.addGeneticOperator(new CrossoverOperator(conf));
            conf.addGeneticOperator(new MutationOperator(conf));
            conf.setKeepPopulationSizeConstant(true);
            conf.setPreservFittestIndividual(true);

            Chromosome chromosome = ChromosomeGeneration.setDynamicChromosome(conf, classGroupsArrayList, coursesArrayList, lecturersArrayList, lectureHallsArrayList);
            conf.setSampleChromosome(chromosome);
            conf.setPopulationSize(POPULATION_SIZE);

            Genotype population = Genotype.randomInitialGenotype(conf);

            long startTime = System.currentTimeMillis();
            int evolucija = 0;

            for (int i = 0; i < EVOLUTION_REVOLUTIONS; i++) {
                evolucija += 1;
                population.evolve();
                IChromosome theFittestOne = population.getFittestChromosome();

                if (theFittestOne.getFitnessValue() == FITNESS_VALUE) {
                    break;
                }

            }
            long finishTime = System.currentTimeMillis();
            System.out.println((double) (finishTime - startTime) / 1000 + " sekundi");
            IChromosome theFittestOne = population.getFittestChromosome();
            System.out.println("Najbolje rješenje pronađeno nakon: " + evolucija + " evolucija");
            System.out.println("Fitness vrijednost: " + theFittestOne.getFitnessValue() + " \n ");
            DynamicSchedule.printSchedule(new DynamicSchedule(theFittestOne));

           /* Schedule s = new Schedule();
            s.finalEval(theFittestOne);*/

        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}