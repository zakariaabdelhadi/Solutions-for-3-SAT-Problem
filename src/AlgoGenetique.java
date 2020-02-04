import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

public class AlgoGenetique {

	// ************************************ Initialisation des paramétres // ***********************************************************************

	static Random rand = new Random();
	static final int NB_POPULATION = 105; // nombre de population
	static final double CROISEMENT_P = 0.7; // probabilité de croisement
	static final double MUTATION_P = 0.03; // probabilité de mutation

	// ************************************** Algorithme génétique // *****************************************************
	static public char[] AlgoGenetiqueF1(Formule formule, int generation) {
		// le but c'est de maximiser la fonction fitness
		Chromosome best = null;

		Population population = creePopulation(formule, Fitness.FITNESS_1);
		best = getBest(population, formule);

		if (best.getNbSatisfait() == formule.getClauseSize())
			return best.getInstance();

		for (int i = 0; i < generation; i++) {
			Chromosome parents[] = selectionParent(population, formule);
			croisementEtMutation(population, parents, formule, Fitness.FITNESS_1);

			// garder la taille de la population fixe
			while (population.getSize() > NB_POPULATION) {
				population.removeLeastFit();
			}

			best = getBest(population, formule);
			if (best.getNbSatisfait() == formule.getClauseSize())
				return best.getInstance();
		}

		return best.getInstance();
	}
	// ************************************************ La Méthode de mutation ***********************************************************

	static private void mutation(Formule formule, char[] c) {
		for (int i = 0; i < formule.getInstanceSize(); i++) {
			double random = Math.random();
			if (random <= MUTATION_P) {
				c[i] = flip(c[i]);
			}
		}
	}
	// ***********************************************La méthode de Croisement ************************************************************

	static private void croisementEtMutation(Population population, Chromosome parents[], Formule formule, Fitness fn) {
		int nbSelection = (parents.length % 2 == 0) ? parents.length : parents.length - 1;
		int j = 0;
		for (; j < nbSelection; j += 2) {
			double random = rand.nextDouble();
			if (random <= CROISEMENT_P) {
				Chromosome a = parents[j];
				Chromosome b = parents[j + 1];

				// la generation des fils c1 et c2
				char[] c1 = new char[formule.getInstanceSize()];
				char[] c2 = new char[formule.getInstanceSize()];

				// K est le point de mutation generé aleatoirement
				int k = rand.nextInt(formule.getInstanceSize());
				for (int ii = 0; ii < k; ii++) {
					c1[ii] = a.getInstance()[ii];
					c2[ii] = b.getInstance()[ii];
				}
				for (int ii = k; ii < formule.getInstanceSize(); ii++) {
					c1[ii] = b.getInstance()[ii];
					c2[ii] = a.getInstance()[ii];
				}

				// mutation
				mutation(formule, c1);
				mutation(formule, c2);

				population.add(a, a.getFitness());
				population.add(b, b.getFitness());
				int c1s = formule.check(c1);
				int c2s = formule.check(c2);
				double c1F = fn.get(c1, formule, c1s);
				double c2F = fn.get(c2, formule, c2s);
				population.add(new Chromosome(c1F, c1s, c1), c1F);
				population.add(new Chromosome(c2F, c2s, c2), c2F);
			}
		}
	}
	// ***********************************************************************************************************

	static private char flip(char c) {
		return c == 0 ? (char) 1 : (char) 0;
	}
	// ************************************ une fonction qui retourne the fitter element ***********************************************************************

	static private Chromosome getBest(Population population, Formule formule) {
		return population.getArray().peekFirst();
	}

	// ************************************** la fonction qui retourne les parents *********************************************************************

	static private Chromosome[] selectionParent(Population population, Formule formule) {
		int nbSelection = population.getArray().getSize() / 2 + 1;

		Chromosome out[] = new Chromosome[nbSelection];
		for (int i = 0; i < nbSelection; i++) {
			double random = Math.random();
			double currentValue = 0.0;
			double totalFitness = population.getTotalFitness();
			for (SortedList<Chromosome>.Node iter = population.getArray()
					.getFirstNode(); iter != null; iter = iter.next) {
				Chromosome c = iter.object;
				currentValue += c.getFitness() / totalFitness;
				if (random <= currentValue) {
					out[i] = c;
					break;
				}
			}
		}

		return out;
	}

	// ************************************************ Creation de la population ***********************************************************

	static private Population creePopulation(Formule formule, Fitness fn) {
		double totalFitness = 0.0;
		SortedList<Chromosome> array = new SortedList<Chromosome>();
		// initialiser la population
		for (int i = 0; i < NB_POPULATION; i++) {
			char x[];

			x = new char[formule.getInstanceSize()];
			// generation des chromosomes aléatoirement
			for (int j = 0; j < formule.getInstanceSize(); j++) {
				x[j] = (rand.nextInt() % 2) == 0 ? (char) 0 : (char) 1;
			}

			// evaluer le chromosome
			int nbSatisfait = formule.check(x);
			double fitness = fn.get(x, formule, nbSatisfait);
			totalFitness += fitness;
			Chromosome ch = new Chromosome(fitness, nbSatisfait, x);

			array.add(ch, fitness);
		}
		return new Population(totalFitness, array);
	}
	// ***********************************************************************************************************

	static abstract class Fitness {
		abstract double get(char x[], Formule formule, int nbSatisfait);

		static Fitness FITNESS_1 = new Fitness1();
		static Fitness FITNESS_2 = new Fitness2();
	}

	static class Fitness1 extends Fitness {
		@Override
		double get(char x[], Formule formule, int nbSatisfait) {
			//formule.check(x);
			return nbSatisfait;
		}
	}

	static class Fitness2 extends Fitness {
		@Override
		double get(char x[], Formule formule, int nbSatisfait) {
			return nbSatisfait;
		}
	}
	// ************************************  La class chromosome ***********************************************************************

	static public class Chromosome {
		int nbSatisfait;
		double fitness;
		char[] instance;

		public char[] getInstance() {
			return instance;
		}

		public double getFitness() {
			return fitness;
		}

		public int getNbSatisfait() {
			return nbSatisfait;
		}

		Chromosome(double fitness, int nbSatisfait, char instance[]) {
			this.fitness = fitness;
			this.nbSatisfait = nbSatisfait;
			this.instance = instance;
		}
	}
	// ************************************* La class Population **********************************************************************

	static public class Population {
		double totalFitness;
		SortedList<Chromosome> array;

		Population(double totalFitness, SortedList<Chromosome> array) {
			this.totalFitness = totalFitness;
			this.array = array;
		}

		// Ajouter un chromosome
		public void add(Chromosome ch, double fitness) {
			array.add(ch, fitness);
			totalFitness += fitness;
		}

		// enlever le plus mauvais chromosome
		public void removeLeastFit() {
			Chromosome ch = array.getLast();
			totalFitness -= ch.getFitness();
		}

		SortedList<Chromosome> getArray() {
			return array;
		}

		int getSize() {
			return array.getSize();
		}

		double getTotalFitness() {
			return totalFitness;
		}
	}
	// ***********************************************************************************************************

}
