import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.SortedSet;

public class Algorithmes {
	  static public long a1 = 0;
	  static public long a2 = 0;
	  static public long a3 = 0;
	  static Random rand = new Random();
	  
	  //************************************** Largeur *****************************************************
  public static char[] AlgoLargeur(Formule formule){
	  // liste 'open' des noeud pas encore explorer
	  LinkedList < Holder > list = new LinkedList < Holder > ();	
	  // ajout de la racine 
	  list.add(new Holder(0, null));
	  // on boucle jusqua ce que la file est vide
	  while (!list.isEmpty()) {
		  // on develope le prmiéer element dans la liste
		  Holder holder = list.getFirst();
		  list.removeFirst();
		  
		  // developpeement de tt l'arbre
		  if (holder.getPosition() < formule.getInstanceSize()){
			  // dans le cas ou le noeud pas une feuille on ajoute ces 2 fils
			  char nx1[] = new char[holder.getPosition() + 1];
			  char nx2[] = new char[holder.getPosition() + 1];
			  
			  for(int i = 0; i < holder.getPosition(); i++){
				  nx1[i] = holder.getInstance()[i];
				  nx2[i] = holder.getInstance()[i];
			  }
			  nx1[holder.getPosition()] = 0;
			  nx2[holder.getPosition()] = 1;
			  // ajout des fils
			  list.add(new Holder(holder.getPosition() + 1, nx1));
			  list.add(new Holder(holder.getPosition() + 1, nx2));
			  
		  } else {
			  // ici on retourne l'instance qui satisfait tt les clauses
			  if (formule.check(holder.getInstance()) == formule.getClauseSize()){
				  return holder.getInstance();
			  }
		  }		  
	  }
	return null;
  }
  
  //************************************** Profondeur  *****************************************************

  
  public static char[] AlgoProfondeur(Formule formule){
	// pile des noeud pas encore explorer
	  Stack < Holder > pile = new Stack < Holder > ();  
	// ajout de la racine 
	  pile.push(new Holder(0, null));
	// on boucle jusqua ce que la file est vide
	  while (!pile.isEmpty()){
		// on develope l'element depilé
		  Holder holder = pile.pop();		  
		  if (holder.getPosition() < formule.getInstanceSize()){
			// dans le cas ou le noeud pas une feuille on ajoute ces 2 dans la pile
			  char nx1[] = new char[holder.getPosition() + 1];
			  char nx2[] = new char[holder.getPosition() + 1];
			  
			  for(int i = 0; i < holder.getPosition(); i++){
				  nx1[i] = holder.getInstance()[i];
				  nx2[i] = holder.getInstance()[i];
			  }
			  nx1[holder.getPosition()] = 0;
			  nx2[holder.getPosition()] = 1;
			  // ajout dans la pile
			  pile.push(new Holder(holder.getPosition() + 1, nx1));
			  pile.push(new Holder(holder.getPosition() + 1, nx2));
			  
		  } else {
			  if (formule.check(holder.getInstance()) == formule.getClauseSize()) {
				  return holder.getInstance();
			  }
		  }		  
	  }
	return null;
  }
  //************************************** affichage a la console *****************************************************

  static public void printInstance(char instance[]){
	  if (instance != null) {
	      for(int i = 0; i < instance.length; i++)
	      System.out.print((int)instance[i]);
	  }
  }
  
  public static char[] AlgoAEtoileH1(Formule formule, int maxTime){
      return AlgoAEtoile.AlgoAEtoile(formule, maxTime, new Heuristique1());
  }
  
  
  public static char[] AlgoAEtoileH2(Formule formule, int maxTime){
	  return AlgoAEtoile.AlgoAEtoile(formule, maxTime, new Heuristique2());
  }
  
  public static char[] AlgoAEtoileH3(Formule formule, int maxTime){
	  return AlgoAEtoile.AlgoAEtoile(formule, maxTime, new Heuristique3());
  }

  static public char[] AlgoGenetiqueF1(Formule formule, int generation){
	  return AlgoGenetique.AlgoGenetiqueF1(formule, generation);
  }
 /* static public char[] AlgoGenetiqueF2(Formule formule, int generation){
	  return AlgoGenetique.AlgoGenetiqueF2(formule, generation);
  }*/
	  
  ////////////////////////////////// La Class Holder qui est une instance de solution ////////////////////////////////
  static class Holder {
	  int pos;
	  char[] instance;
	  
	  
	  public Holder(int pos, char instance[]){
		  this.pos = pos;
		  this.instance = instance;
	  }
	  
 
	  public int getPosition() { return pos; }
	  public char[] getInstance() {return instance; }
  }
}
