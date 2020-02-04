import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CnfReader {
   public static Formule parse(String path){
	   Formule formula = new Formule();
	   try {
 	      File myObj = new File(path);
 	      Scanner myReader = new Scanner(myObj);
 	      int nbVariables = 0;
 	      int nbClause = 0;
 	      int clauseId = 0;
 	      while (myReader.hasNextLine()) {
 	        String data = myReader.nextLine();
 	        if (data.length() > 0 && data.charAt(0) == 'c'){
 	        	// empty
 	        } else if (data.length() > 0 && data.charAt(0) == 'p'){
 	        	int ci = 1;
 	        	while(ci < data.length()){
 	        		if (isDigit(data.charAt(ci))){
 	    	        	int ei = ci;
 	    	        	ci++;
 	    	        	while(ci < data.length() && isDigit(data.charAt(ci))){
 	    	        		ci++;
 	    	        	}	        	
 	    	        	nbVariables = Integer.parseUnsignedInt(data.substring(ei, ci));
 	    	        	break;
 	        		}
 	        		ci++;
 	        	}
 	        	
 	        	while(ci < data.length()){
 	        		if (isDigit(data.charAt(ci))){
 	    	        	int ei = ci;
 	    	        	ci++;
 	    	        	while(ci < data.length() && isDigit(data.charAt(ci))){
 	    	        		ci++;
 	    	        	}
 	    	        	nbClause = Integer.parseUnsignedInt(data.substring(ei, ci));
 	    	        	break;
 	        		}
 	        		ci++;
 	        	}
 	        	formula.resize(nbClause);
 	        	formula.setInstanceSize(nbVariables);
 	        } else if (data.length() > 0){
 	        	ArrayList < Integer > variables = new ArrayList < Integer > (3);
 	        	
 	        	int ci = 0;
 	        	while(ci < data.length() && data.charAt(0) != '0'){
 	        		while(ci < data.length() && !isDigit(data.charAt(ci)) && data.charAt(ci) != '-')ci++;
 	        		if (ci >= data.length() || data.charAt(ci) == '0') break;
 	        		int ei = ci;
 	        		ci++;
 	    	        while(ci < data.length() && isDigit(data.charAt(ci))){
 	    	        	ci++;
 	    	        }	        	
 	    	        variables.add(Integer.parseInt(data.substring(ei, ci)));
 	 	        	ci++;
 	        	}
 	        	if (variables.size() > 0){
 	        	Clause clause = new Clause();
 	        	for (int v : variables){
 	        		if (v > 0){
 	        			clause.addPositiveVariable(v);
 	        		} else {
 	        			clause.addNegativeVariable(-v);
 	        		}
 	        	}
 	        	formula.addClause(clause, clauseId++);
 	        	}
 	        }
 	      }
 	      myReader.close();
 	    } catch (FileNotFoundException e) {
 	      System.out.println("path " + path + " not found.");
 	      e.printStackTrace();
 	    }
	   
	   formula.buildH2();
	   return formula;
   }
	
   static boolean isDigit(char c){
	   return c >= '0' && c <= '9';
   }
}

