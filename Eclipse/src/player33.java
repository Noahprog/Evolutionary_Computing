import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.Random;
import java.lang.Math;
import java.util.Properties;

public class player33 implements ContestSubmission {
	Random rnd_;
	ContestEvaluation evaluation_;
	private int evaluations_limit_;

	public player33() {
		rnd_ = new Random();
	}

	public static void main(String[] args) {
		System.out.println("Start"); // Display the string
	}

	public void setSeed(long seed) { // Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation) { // Set evaluation problem used in the run
		evaluation_ = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();
		// Get evaluation limit
		evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
		boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
		//if (isMultimodal)
		//{ 
		//	System.out.println("Function is Multimodal."); 
		//}
		//if (hasStructure)
		//{ 
		//	System.out.println("Function has structure."); 
		//}
		//if (isSeparable)
		//{ 
		//	System.out.println("Function is separable."); 
		//}
	}

	public void run() { // Run your algorithm here
		DifferentialEvolution DE = new DifferentialEvolution(evaluation_);
		DE.init();
		int evaluationLimit = (int) Math.floor(evaluations_limit_ / DE.getPopulationSize());
		int bestIteration = 0; 
		double bestFitness = 0.0;

		for(int i=0; i < (evaluationLimit-1); i++) {	
			DE.iterate(i);
			Population currentPopulation = DE.getPopulation();
			currentPopulation.sort();
			double bestFitnessCurrentPopulation = currentPopulation.getIndividual(0).getIndividualFitness();
			if (bestFitnessCurrentPopulation > bestFitness) {
				bestFitness = bestFitnessCurrentPopulation;
				bestIteration = i; 
			}
			if(i == 0 || i == (evaluationLimit-2)) { 
				System.out.println("Best fitness iteration " + i + " : " + bestFitnessCurrentPopulation);
			}
		}
		System.out.println("Last improvement in iteration: " + bestIteration);
	}

	public void findStart() {
		int evals = 0;
		int dimensions = 10;
		int lowerbound = -5;
		int upperbound = 5; 
		double lowerbounds[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; 
		double upperbounds[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; 
		Random random = new Random(); 
		while(evals < evaluations_limit_){  
			double individual[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			for(int i = 0; i < dimensions; i++){
				if(random.nextDouble() > 0.5) {
					individual[i] = lowerbounds[i] + 5 * random.nextDouble();

				} else {
					individual[i] = lowerbounds[i] - 5 * random.nextDouble();
				}
			}	
			Double fitness = (double) evaluation_.evaluate(individual);
			if (fitness > 0) {
				System.out.println("Eval: " + evals + " [ " + individual[0] + ", " + individual[1] + ", " + individual[2] + ", " + individual[3] + ", " + individual[4] + ", " + individual[5] + ", " + individual[6] + ", " + individual[7] + ", " + individual[8] + ", " + individual[9] + " ]");
				System.out.println("Fitness:" + fitness);
			}
			evals++;
		}	
	}

}
