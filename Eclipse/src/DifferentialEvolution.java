import java.util.Random;
import org.vu.contest.ContestEvaluation;

public class DifferentialEvolution {

	static final int POPULATION_SIZE = 20;
	static final int BEST_PARENTS = 20;
	static final double REPLACE_RATE = 1; 
	static final double CROSS_PROBABILITY = 0.8;
	static final double  DIFFERENTIAL_WEIGHT = 0.6;
	static final double  STEPSIZE_COEFFICIENT = 0.0001;
	static final int DIMENSION = 10;

	private Population population;
	ContestEvaluation evaluation;

	public DifferentialEvolution(ContestEvaluation evaluation) {
		this.evaluation = evaluation;
	}

	public void init() { // Initializes the algorithm; compute starting population
		this.population = new Population(POPULATION_SIZE, evaluation);
		 population.populationInit();
	}

	public void iterate(int q) { 
		double adjustedWeight = DIFFERENTIAL_WEIGHT - q * STEPSIZE_COEFFICIENT;	
		Population mutationPopulation = population.clone();
		Population crossoverPopulation = population.clone();			
		Random random = new Random();
		population.sort();
		//Create Mutation population
		for(int i=0; i<POPULATION_SIZE; i++) {
			Individual currentForMutation = population.getIndividual(i).clone();
			int a =i; int b =i; int c =i; int d =i; int e =i;
			while(a == i) {
				a = random.nextInt(BEST_PARENTS);
			}
			while(b == i | b==a) {
				b = random.nextInt(BEST_PARENTS);
			}
			while(c == i | c==a | c==b) {
				c = random.nextInt(BEST_PARENTS);
			}
			while(d == i | d==a | d==b | d==c) {
				d = random.nextInt(BEST_PARENTS);
			}
			while(e == i | e==a | e==b | e==c | e==d) {
				e = random.nextInt(BEST_PARENTS);
			}
			Individual parentA = population.getIndividual(a);
			Individual parentB = population.getIndividual(b);
			Individual parentC = population.getIndividual(c);
			Individual parentD = population.getIndividual(d);
			Individual parentE = population.getIndividual(e);
			for(int j=0; j<DIMENSION ;j++) {
				currentForMutation.mutate(parentA, parentB, parentC, parentD, parentE, DIFFERENTIAL_WEIGHT, j); //DIFFERENTIAL_WEIGHT or adjustedWeight 
			}
			mutationPopulation.replace(currentForMutation, i);
		}
		//Create Crossover population
		int certainReplacement = random.nextInt(DIMENSION);
		for(int i=0; i<POPULATION_SIZE; i++) {
			Individual currentForCrossover = population.getIndividual(i).clone();
			Individual currentAfterMutation = mutationPopulation.getIndividual(i).clone();
			for(int j=0; j<DIMENSION ;j++) {
				double randomNumber = random.nextDouble();
				currentForCrossover.crossover(currentAfterMutation, j, certainReplacement, CROSS_PROBABILITY, randomNumber);
			}
			crossoverPopulation.replace(currentForCrossover, i);
		}
		//Select Surviving population
		for(int i=0; i<POPULATION_SIZE; i++) {
			crossoverPopulation.getIndividual(i).reevaluateFitness();
			if(crossoverPopulation.getIndividual(i).getIndividualFitness() > REPLACE_RATE * population.getIndividual(i).getIndividualFitness()) {
				population.replace(crossoverPopulation.getIndividual(i), i);
			}
		}
	}

	public Population getPopulation() {
		return population;
	}

	public double[] getPopulationFitness() {
		double[] result = new double[POPULATION_SIZE];
		for(int i=0; i<POPULATION_SIZE; i++) {
			result[i] = population.getIndividual(i).getIndividualFitness();
		}
		return result;
	}

	public int getPopulationSize(){
		return POPULATION_SIZE;
	}

}
