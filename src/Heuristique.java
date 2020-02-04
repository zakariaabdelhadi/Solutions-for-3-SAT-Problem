

abstract class Heuristique{
	public abstract Formule.Int2 get(Formule formule, char x[]);
}
class Heuristique1 extends Heuristique{
	public Formule.Int2 get(Formule formule, char x[]){
		return formule.getH1(x);
 }
}
class Heuristique2 extends Heuristique{
	public Formule.Int2 get(Formule formule, char x[]){
		return formule.getH2(x);
	}
}
class Heuristique3 extends Heuristique{
	public Formule.Int2 get(Formule formule, char x[]){
		return formule.getH3(x);
	}
}
