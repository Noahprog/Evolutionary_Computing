# Evolutionary_Computing

Group project for Evolutionary Computing for group 33. In this assignment we optimized three functions: Bentsigar, Schaffer F7 and Katsuura.

## Authors

* Robbert Hendriks
* Sophie Klumper
* Thomas Weelinck
* Noah van Grinsven 

### Structure

important subfolders in main folder:

* Eclipse
  * Scr - contains java files used to run the script
  * Bin - contains the class files of scripts
* Report - contains the report that is part of the assignment

### Initialisation of programm

In the subfolder Scr are al the scripts needed to run the programm. The main script is player33.java. 

#### Parameters for tuning
In order to run this programm for a specific function first the parameters have to be changed. The optimal parameters can be found in the report in subfolder "report". The parameters that have to be changed are located in the DifferentialEvolution.java file in row 6-11.

#### Parameters for control
To use the parameter control option:
* Go to DifferentialEvolution.java file row 56
* Change: DIFFERENTIAL_WEIGHT --> adjustedWeight

### Running

To run the programm use the following command-line
```
cd Evolutionary_Computing
java -jar testrun.jar -submission=player33 -evaluation=EVALUATION  -seed=1
```
With EVALUTION = BentCigarFunction, SchaffersEvaluation, KatsuuraEvaluation
