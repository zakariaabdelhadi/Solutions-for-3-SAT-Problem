import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.SortedMap;


public class AlgoAEtoile {
	static Random rand = new Random();
	  
	public static char[] AlgoAEtoile(Formule formule, int maxTime, Heuristique heur){
		final int MAX_OPEN = 2000; // taille de la file 'open'

		long b = System.currentTimeMillis();
		
        // structures de données utilisées
          SortedList < AStarHolder > openQ = new SortedList < AStarHolder >();
		  HashSet < AStarHolder > close = new HashSet < AStarHolder > ();
		    
		  // instance Alératoire au debut
		  char x1[] = new char[formule.getInstanceSize()];
		  /*for(int i = 0; i < x1.length; i++){
			  x1[i] = (rand.nextInt() % 2) == 0 ? (char) 0 : (char) 1;
		  }*/
		  
		  // calcule d'heuristique et du nombre des clauses satisfaites 
		  int satisfNb = formule.check(x1);
		  if (satisfNb == formule.getClauseSize()) return x1;
		  Formule.Int2 r = heur.get(formule,  x1);
		  
		  // ajout a la file 'open'
		  AStarHolder first = new AStarHolder(r.Heuristique, 0, x1, satisfNb); 
		  openQ.add(first, first.getHeuristic());

		  
		  // meilleur resultat trouver
		  char best[] = x1;
		  double bestHeuristic = first.getHeuristic();
		  
		  
		  
		  while(!openQ.isEmpty()){	  
			  // on cherche a minimiser h(x) donc on prend l'element avec
			  // le moins d'h(x) dans la file
			  AStarHolder current = openQ.getLast();		  
			  // on va ici générer l'ensemble de toutes les combinaisons possible
			  // de voisins
			  for(int i = 0; i < formule.getInstanceSize(); i++){
				  char[] x = copy(current.getInstance());
				  x[i] = flip(x[i]);			  
				  // ignorer l'element s'il existe deja dans 'close' l'ensemble
				  // d'instance deja explorer
				  if (!existInside(x, close)) {
					  // calcule d'heuristique 
					  Formule.Int2 resultat = heur.get(formule,  x);
					  int nbSatisfait = resultat.nbSatisfait;
					  double heuristic = resultat.Heuristique ;//+ current.getCost() + 1;
					  if (heuristic < bestHeuristic){
						  best = x;
						  bestHeuristic = heuristic;
					  }
					  // verification du resultat 
					  if (nbSatisfait == formule.getClauseSize()) return x; 
					  // calcule d'heuristique 
					  AStarHolder holder = new AStarHolder(heuristic, current.getCost() + 1, x, nbSatisfait); 
					  // ajout à la file 'open'
					  if (openQ.getSize() >= MAX_OPEN){
						  openQ.getFirst();
					  }
					  openQ.add(holder, holder.getHeuristic());
				  }
			
			  }
			  // ajouter l'element déjà développer dans l'ensemble 'close'		  
			  close.add(current);
			  if (System.currentTimeMillis() - b >  maxTime) return best;
		  }	  
		  return best;
	  }
 
	static public class AStarHolder {
		  double heur;
		  int cost;
		  int nbSatisfait;
		  AEtoileInstance instance;
		  
		  AStarHolder(double heur, int cost, char[] instance, int nbSatisfait){
			  this.heur = heur;
			  this.cost = cost;
			  this.instance = new AEtoileInstance(instance);
			  this.nbSatisfait = nbSatisfait;
		  }
		  
		  @Override
		  public boolean equals(Object obj) {
			  if (this == obj) return true;	  
			  return getInstance().equals(obj);
		  }
		  
		  @Override
		  public int hashCode() {
			  return instance.hashCode();
		  }
		  public int getCost() {return cost;}
		  public double getHeuristic() { return heur; }
		  public char[] getInstance() { return instance.getInstance(); }
		  public int getNbSatisfait() {return nbSatisfait ;}
	  }
	static public class AEtoileInstance {
		  char instance[];
		  
		  AEtoileInstance(char instance[]){
			  this.instance = instance;
		  }
		  @Override
		  public boolean equals(Object obj) {
			  
			  if (this == obj) return true;
			  char[] instance = null;
			  if (obj.getClass() == AEtoileInstance.class) {
				  instance = ((AEtoileInstance)obj).getInstance();
			  } else if (obj.getClass() == AStarHolder.class){
				  instance = ((AStarHolder)obj).getInstance();
			  } else {
				  instance = (char[])obj;
			  }
			  if (instance.length != getInstance().length) return false;
			  
			  for(int i = 0; i < getInstance().length; i++){
				  if (instance[i] != getInstance()[i]) return false;
			  }
			  return true;
		  }
		  @Override
		  public int hashCode() {
			  int result = 0;
			  int cl = 0;
			  int ctr = 0;
			  for(int i = 0; i < instance.length; i++, ctr++){
				  cl += instance[i] * (i + 1);
				  if (ctr >= 7){
				      result = 7 * result + cl;
				      ctr = 0;
				  }
			  }
			  if (ctr > 0){
				  result = 7 * result + cl;
			  }
		      return result;
		  }
		  
		  char[] getInstance() { return instance; }
		}
	static public char[] copy(char v[]){
		  char out[] = new char[v.length];
		  for(int i = 0; i < v.length; i++){
			  out[i] = v[i];
		  }	  
		  return out;
	  }
	  static public char flip(char v){
		  return v == 0 ? (char)1 : (char)0;
	  }
	  static public boolean existInside(char[] instance, HashSet < AStarHolder > set){  
		  AEtoileInstance ins = new AEtoileInstance(instance);
		  return set.contains(ins);
	  }
	  static public boolean existInside(char[] instance, PriorityQueue < AStarHolder > q){   
		  Iterator < AStarHolder > it = q.iterator();
		  while(it.hasNext()){
			  AStarHolder tmp = it.next();
			  if (tmp.equals(instance)) return true;
		  }  
		  return false;
	  }
	  
}
