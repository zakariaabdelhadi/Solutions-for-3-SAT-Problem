import java.util.ArrayList;

public class Clause {

	private ArrayList<Integer> positives = new ArrayList<Integer>(); //les variable positifs d'une clause
	private ArrayList<Integer> negatives = new ArrayList<Integer>(); //les variable négatifs d'une clause

	public ArrayList<Integer> getPositives() {
		return positives;
	}

	public ArrayList<Integer> getNegatives() {
		return negatives;
	}

	public void addPositiveVariable(int var) {
		positives.add(var);
	}

	public void addNegativeVariable(int var) {
		negatives.add(var);
	}

	//******************************cette fonction verifier si une variable positie==1 et la négative == 0
	public boolean check(char instance[]) {
		for (int v : positives) {
			if (instance[v - 1] == 1) {
				return true;
			}
		}
		for (int v : negatives) {
			if (instance[v - 1] == 0) {
				return true;
			}
		}

		return false;
	}
	//**********************cette fonction retourne le nombre de clause satisfaites*********************************************************************************

	public int satisfait(char instance[]) {
		int ctr = 0;
		for (int v : positives) {
			if (instance[v - 1] == 1) {
				ctr++;
			}
		}
		for (int v : negatives) {
			if (instance[v - 1] == 0) {
				ctr++;
			}
		}

		return ctr;
	}
	
//*************************************************************************************************************
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i : positives) {
			buffer.append(" " + i + " ");
		}
		for (int i : negatives) {
			buffer.append(" " + -i + " ");
		}
		return buffer.toString();
	}

}
