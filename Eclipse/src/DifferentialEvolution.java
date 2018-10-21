import java.util.Random;
import org.vu.contest.ContestEvaluation;

public class DifferentialEvolution {

	static final int POPULATION_SIZE = 2500;
	static final int BEST_PARENTS = 850;
	static final double REPLACE_RATE = 0.95; 
	static final double CROSS_PORBABILITY = 0.25;
	static final double  DIFFERENTIAL_WEIGHT = 0.025;
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
		double adjustedWeight = DIFFERENTIAL_WEIGHT - q * 0.0001;	
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
				currentForMutation.mutate(parentA, parentB, parentC, parentD, parentE, DIFFERENTIAL_WEIGHT, j);
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
				currentForCrossover.crossover(currentAfterMutation, j, certainReplacement, CROSS_PORBABILITY, randomNumber);
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
