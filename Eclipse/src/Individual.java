import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import org.vu.contest.ContestEvaluation;

public class Individual implements Comparable<Individual> {

	private int dimensions = 10;
	private int lowerbound = -5;
	private int upperbound = 5; 

	private double[] individual;
	private double individualFitness;
	ContestEvaluation evaluation;

	public Individual(ContestEvaluation evaluation) { 
		this.individual = new double[dimensions];
		this.individualFitness = Double.NEGATIVE_INFINITY;
		this.evaluation = evaluation;
	}

	public void init() { 
		Random random = new Random();
		double startValues [] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		double startValuesKatsuura [] = {0.23, 0.38, -0.79, 0.29, 0.3, -0.06, -0.24, -0.03, -0.09, 0.02};
		for(int i=0; i<dimensions; i++) {
			if(random.nextDouble() > 0.5) {
				individual[i] = startValuesKatsuura[i] + 0.1 * (lowerbound + (upperbound - lowerbound) * random.nextDouble());

			} else {
				individual[i] = startValuesKatsuura[i] - 0.1 * (lowerbound + (upperbound - lowerbound) * random.nextDouble());
			}
		}
		individualFitness = (double) evaluation.evaluate(individual);
	}

	public Individual clone() {
		Individual copy = new Individual(evaluation);
		for(int i=0; i<dimensions; i++) {
			copy.individual[i] = this.individual[i];
		}
		copy.individualFitness = this.individualFitness;
		return copy;
	}

	public int compareTo(Individual individual){  
		if(this.individualFitness > individual.getIndividualFitness()) {
			return 1;  
		} 
		if(this.individualFitness < individual.getIndividualFitness()) {
			return -1;  
		} 
		else {
			return 0;  
		}
	}

	public void printIndividual() {
		System.out.println("[ " + round(individual[0],2) + ", " + round(individual[1],2) + ", " + round(individual[2],2) + ", " + round(individual[3],2) + ", " + round(individual[4],2) + ", " + round(individual[5],2) + ", " + round(individual[6],2) + ", " + round(individual[7],2) + ", " + round(individual[8],2) + ", " + round(individual[9],2) + " ]");
	}

	public void printIndividualFitness() {
		System.out.println(individualFitness);
	}

	public void mutate(Individual random1, Individual random2, Individual random3, Individual random4 ,Individual random5, double F, int j) {
		individual[j] = random1.individual[j] + F * (random2.individual[j] - random3.individual[j]);
		correctSearchSpace();
	}

	public void crossover(Individual mutatedIndividual, int j, int i, double crossoverProbability, double random) {
		if(j==i) {
			individual[j] = mutatedIndividual.individual[j];
		}
		else if(random < crossoverProbability) {
			individual[j] = mutatedIndividual.individual[j];
		}
	}

	public void correctSearchSpace() {
		for(int i=0; i<dimensions; i++) {
			while(individual[i] > 5 || individual[i] < -5) {
				if(individual[i] > 5) {
					individual[i] = 5 - (individual[i] - 5);	
				}
				if(individual[i] < -5) {
					individual[i] = -5 + (-5 - individual[i]);
				}
			}
		}
	}

	public void reevaluateFitness() {
		individualFitness = (double) evaluation.evaluate(individual);
	}

	public double[] getIndividual() {
		return individual;
	}

	public double getIndividualFitness() {
		return individualFitness;
	}

	// Utils 
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
