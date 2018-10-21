
public interface IndividualInterface {

	void init();
	
	Individual clone();
	
	int compareTo(Individual individual);
	
	void printIndividual();
	
	void printIndividualFitness();
	
	void mutate(Individual random1, Individual random2, Individual random3, double F, int i);

	void correctSearchSpace();

	double[] getIndividual();

	double getIndividualFitness();
	
	void reevaluateFitness();
	
}
