import java.util.HashSet;
import java.util.Random;


public class AlgoACS {
	static final int NB_POPULATION = 60;
	static final double PHEROMONE_INITIAL = 0.1;
	static final double ALPHA = 0.1;
	static final double BETA = 0.8;
	static final double RO = 0.6;
	static Random rand = new Random();
	public static char[] AlgoACS(Formule formule, int generation) {
		double feromoneVec[] = new double[formule.getInstanceSize() * 2];
		
		char best[] = null;
		int nbStaisfaitBest = 0;
		
		for(int i = 0; i < formule.getInstanceSize(); i++){
			feromoneVec[i * 2] = PHEROMONE_INITIAL;
			feromoneVec[i * 2 + 1] = PHEROMONE_INITIAL;
		}
		
		for (int i = 0; i < generation; i++) {
			char[] bestLocale = null;
			int nbStaisfaitBestLocale = 0;
			for (int j = 0; j < NB_POPULATION; j++) {
				int aDevloper[] = new int[formule.getInstanceSize()];
				shuffle(aDevloper, formule);
				char[] solution = SolutionFourmi(formule, feromoneVec, aDevloper);
				int nbSatisfait = formule.check(solution);
				
				if (nbSatisfait > nbStaisfaitBestLocale){
					nbStaisfaitBestLocale = nbSatisfait;
					bestLocale = solution;
				}
			}
			
		
			for(int j = 0; j < formule.getInstanceSize(); j++){
				if (bestLocale[j] == 1){
					feromoneVec[j * 2 + 1] = (1.0 - RO) * feromoneVec[j * 2 + 1] +
							RO * nbStaisfaitBestLocale;
					feromoneVec[j * 2] = (1.0 - RO) * feromoneVec[j * 2];
				} else {
					feromoneVec[j * 2] = (1.0 - RO) * feromoneVec[j * 2] + 
							RO * nbStaisfaitBestLocale;
					feromoneVec[j * 2 + 1] = (1.0 - RO) * feromoneVec[j * 2 + 1];
				}
			}
			
			if (nbStaisfaitBestLocale > nbStaisfaitBest){
				nbStaisfaitBest = nbStaisfaitBestLocale;
				best = bestLocale;
			}
		}
		
		
		return best;
	}
	public static char[] SolutionFourmi(Formule formule, double feromone[], int aDevloper[]){
            	
          char out[] = new char[formule.getInstanceSize()];
		    
		  for(int i = 0; i < formule.getInstanceSize(); i++){
			  double randomNb = rand.nextDouble();
			  int aDeveloper = aDevloper[i];
			  int nbOccP = formule.getNbOccP()[aDeveloper];
			  int nbOccN = formule.getNbOccP()[aDeveloper];
			  double valN = Math.pow(feromone[i * 2], ALPHA) * Math.pow(nbOccN, BETA);
			  double valP = Math.pow(feromone[i * 2 + 1], ALPHA) * Math.pow(nbOccP, BETA);
			  
			  double probN = valN / (valN + valP);
			  double probP = valP / (valN + valP);
			  
			  if (randomNb <= probN){
				  out[i] = 0;
			  } else {
				  out[i] = 1;
			  }
			  
		  }
		 return out;
	  }
	
	
	static void shuffle(int aDevloper[], Formule formule){
		for(int i = 0; i < formule.getInstanceSize(); i++){
			  aDevloper[i] = i;
		  }
		  //shuffle
		  for(int i = 0; i < 200; i++){
			  int indexA = rand.nextInt(formule.getInstanceSize());
			  int indexB = rand.nextInt(formule.getInstanceSize());
			  
			  int temp = aDevloper[indexA];
			  aDevloper[indexA] = aDevloper[indexB];
			  aDevloper[indexB] = temp;
		  }
	}
	static class Node {
		int pos;
		int variableADevloper;
		
		Node(int pos, int variableADevloper){
			this.pos = pos;
			this.variableADevloper = variableADevloper;
		}
		
		int getPosition() { return pos; }
		int getADevloper() { return variableADevloper; }
		
	}
	
}
