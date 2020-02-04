import javax.swing.text.Position;

public class Formule {

	private Clause clauseList[]; // ensemble des clauses : tableau de clause
	private int instanceSize; // taille de l'instance // combien de clauses 

	private int nbOccurenceP[]; // le nombre de variables positifs
	private int nbOccurenceN[]; // le nombre de variables négatifs

	public void setInstanceSize(int size) {
		instanceSize = size;
		nbOccurenceP = new int[size];
		nbOccurenceN = new int[size];
	}

	public void resize(int size) {
		clauseList = new Clause[size];
	}

		//*****************************/ la fonction qui permet d'ajouter une clause a la formule********************* 
	public void addClause(Clause clause, int id) {
		clauseList[id] = clause;
	}

	public void buildH2() {
		for (Clause c : clauseList) {
			for (int p : c.getPositives()) {
				nbOccurenceP[p - 1]++;
			}
			for (int n : c.getNegatives()) {
				nbOccurenceN[n - 1]++;
			}
		}
		for (int i = 0; i < instanceSize; i++) {
			System.out.print(nbOccurenceP[i] + " ");
		}

	}

	public int check(char instance[]) {
		int cpt = 0;
		for (Clause c : clauseList) {
			if (c.check(instance) == true) {
				cpt++;
			}
		}
		return cpt;
	}

	public class Int2 {
		public int nbSatisfait;
		public double Heuristique;

		public Int2(int nbSatisfait, double Heuristique) {
			this.nbSatisfait = nbSatisfait;
			this.Heuristique = Heuristique;
		}
	}

	public Int2 getH1(char instance[]) {
		int nbSatisfait = check(instance);
		double heuristique = clauseList.length - nbSatisfait;
		return new Int2(nbSatisfait, heuristique);
	}

	public Int2 getH2(char instance[]) {
		int nbSatisfait = check(instance);
		double score2 = (clauseList.length - nbSatisfait) / (double) clauseList.length;
		double maxOcc = (double) clauseList.length;
		double score1 = 0.0;
		for (int i = 0; i < instanceSize; i++) {
			if (instance[i] == 1) {
				score1 += nbOccurenceP[i];
			} else if (instance[i] == 0) {
				score1 += nbOccurenceN[i];
			}
		}
		score1 = score1 / maxOcc;
		double heuristique = (1.0 / score1) * 0.4 + score2 * 0.6;
		return new Int2(nbSatisfait, heuristique);
	}

	public Int2 getH3(char instance[]) {
		int nbSatisfait = check(instance);
		double heuristique = 0.0;
		for (int i = 0; i < instanceSize; i++) {
			if (instance[i] == 1) {
				heuristique += nbOccurenceP[i];
			} else if (instance[i] == 0) {
				heuristique += nbOccurenceN[i];
			}
		}
		return new Int2(nbSatisfait, 1.0 / heuristique);
	}

	int getInstanceSize() {
		return instanceSize;
	}

	int getClauseSize() {
		return clauseList.length;
	}

	int[] getNbOccP() {
		return nbOccurenceP;
	}

	int[] getNbOccN() {
		return nbOccurenceN;
	}

	@Override
	public String toString() {
		if (clauseList == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		for (Clause c : clauseList) {
			buffer.append(c + "\n");
		}
		return buffer.toString();
	}
}
