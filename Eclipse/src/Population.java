import java.util.Arrays;
import java.util.Collections;
import org.vu.contest.ContestEvaluation;

public class Population {
	private Individual[] population;
	private int populationSize;
	ContestEvaluation evaluation;

	public Population(int populationSize, ContestEvaluation evaluation) {
		this.populationSize = populationSize;
		this.evaluation = evaluation;
		this.population = new Individual[populationSize];
	}
	
	public void populationInit() {
		for (int i=0; i<populationSize; i++) {
			this.population[i] = new Individual(evaluation);
			this.population[i].init(); //.init() or initHeuristic() 
		}	
	}

	public Population clone() {
		Population copy = new Population(populationSize, evaluation);
		for (int i=0; i<populationSize; i++) {
			copy.population[i] = this.getIndividual(i);
		}
		return copy;
	}

	public void printPopulation() {
		for(int i=0; i<populationSize; i++) {
			System.out.println("Individual " + i + ": ");
			population[i].printIndividual();
		}
	}
	
	public void printPopulationFitness() {
		for(int i=0; i<populationSize; i++) {
			System.out.println("Fitness individual " + i + ": ");
			population[i].printIndividualFitness();
		}
	}

	public void sort() {
		Arrays.sort(population, Collections.reverseOrder());
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public Individual getIndividual(int i) {
		return population[i];
	}

	public void replace(Individual replacement, int i) {
		population[i] = replacement;
	}

}
