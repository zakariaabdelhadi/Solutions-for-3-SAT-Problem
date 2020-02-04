
public class Main {
 public static void main(String args[]){
     // UF75.325.100\\uf75- 1 2 9 8? 010 013
    Formule formule = CnfReader.parse("UF75.325.100\\uf75-01.cnf");
     
   /* System.out.println();
    
    b = System.currentTimeMillis();	 
    instance = Algorithmes.AlgoAEtoileH2(formule, 8000);     
    System.out.println("A etoile termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
    System.out.println("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
    Algorithmes.printInstance(instance);
     */
    System.out.println();
    
    System.out.println((Algorithmes.a1 / 10000) + ", " + (Algorithmes.a2 / 10000) + ", " + (Algorithmes.a3/ 10000));
    
    System.out.println();
    long b = System.currentTimeMillis();
//    char[] instance = AlgoACS.AlgoACS(formule, 60);
//    if (instance != null){
//       System.out.println("Algo Genetique à termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
//       System.out.println("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
//       Algorithmes.printInstance(instance);
//    }
    
    
    
    new Fenetre();
 }
}
