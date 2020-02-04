import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Fenetre extends JFrame implements ActionListener {
	JButton openButton;
	JButton AlgoProfondeur;
	JButton AlgoLargeur;
	JButton AlgoAEoileH3;
	JButton AlgoAEoileH2;
	JButton AlgoAEoileH1;
	JButton AlgoGenetiqueF1;
	JButton Fourmi;
	
	JLabel fichier;
	JLabel fichierInfo;
    JLabel label;
    JLabel resultat;
    JLabel resultat1;
    JLabel resultat2;
    JLabel resultat3;
    JFileChooser fc;
         
    JSpinner spinner;
	
	Fenetre(){
		super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		
        SpinnerModel model = new SpinnerNumberModel(1.0, 1.0, 30.0, 1.0);  

		fichier = new JLabel("fichier:");
		fichier.setFont(new Font("SansSerif", Font.PLAIN, 20));
		fichierInfo = new JLabel("");
		fichierInfo.setFont(new Font("SansSerif", Font.PLAIN, 20));
		label = new JLabel("");
		label.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    resultat = new JLabel("resultat:");
	    resultat.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    resultat1 = new JLabel("");
	    resultat1.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    resultat1.setForeground(Color.red);
	    resultat2 = new JLabel("");
	    resultat2.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    resultat3 = new JLabel("");
	    resultat3.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    
		openButton = new JButton("Open a File...");
        openButton.addActionListener(this);
         
    	AlgoProfondeur = new JButton("Profondeur");
    	AlgoProfondeur.addActionListener(this);
    	AlgoLargeur = new JButton("Largeur");
    	AlgoLargeur.addActionListener(this);
    	AlgoAEoileH3 = new JButton("AEtoile H3");
    	AlgoAEoileH3.addActionListener(this);
    	AlgoAEoileH2 = new JButton("AEtoile H2");
    	AlgoAEoileH2.addActionListener(this);
        AlgoAEoileH1 = new JButton("AEtoile H1");
        AlgoAEoileH1.addActionListener(this);
        AlgoGenetiqueF1 = new JButton("Genetique");
        AlgoGenetiqueF1.addActionListener(this);
        
        Fourmi = new JButton("ACO");
        Fourmi.addActionListener(this);
     
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(AlgoProfondeur);
        buttonPanel.add(AlgoLargeur);
        buttonPanel.add(AlgoAEoileH3);
        buttonPanel.add(AlgoAEoileH2);
        buttonPanel.add(AlgoAEoileH1);
        buttonPanel.add(AlgoGenetiqueF1);
        buttonPanel.add(Fourmi);
        
        JPanel LabelPanel = new JPanel();
        LabelPanel.setLayout(new BoxLayout(LabelPanel, BoxLayout.Y_AXIS)); //use FlowLayout
        LabelPanel.add(fichier);
        LabelPanel.add(label);
        LabelPanel.add(fichierInfo);
        LabelPanel.add(resultat);
        LabelPanel.add(resultat1);
        LabelPanel.add(resultat2);
        LabelPanel.add(resultat3);
        
        
        add(buttonPanel, BorderLayout.PAGE_START);
        add(LabelPanel, BorderLayout.CENTER);
        setSize(1000, 450);
        setLocationRelativeTo(null);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	
                File file = fc.getSelectedFile();
                label.setText(file.getAbsolutePath());
                Formule form = CnfReader.parse(file.getAbsolutePath());
                fichierInfo.setText("taille instance : " + form.getInstanceSize() + ", nombre de clauses :" + form.getClauseSize());
            }
            
        }  else if (e.getSource() == AlgoProfondeur){
       	 Formule formule = CnfReader.parse(label.getText());                 
       	 long b = System.currentTimeMillis();	 
       	 char[] instance = Algorithmes.AlgoProfondeur(formule); 
       	 resultat1.setText("Profondeur termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
       	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
       	 StringBuffer buffer = new StringBuffer();
       	 if (instance != null) {
      	      for(int i = 0; i < instance.length; i++)
      	    	buffer.append((char)(instance[i] + '0'));
      	  }
       	 resultat3.setText(buffer.toString());
      
      }else if (e.getSource() == AlgoLargeur){
        	 Formule formule = CnfReader.parse(label.getText());                 
           	 long b = System.currentTimeMillis();	 
           	 char[] instance = Algorithmes.AlgoLargeur(formule); 
           	 resultat1.setText("Largeur termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
           	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
           	 StringBuffer buffer = new StringBuffer();
           	 if (instance != null) {
          	      for(int i = 0; i < instance.length; i++)
          	    	buffer.append((char)(instance[i] + '0'));
          	  }
           	 resultat3.setText(buffer.toString());
          
          }else if (e.getSource() == AlgoAEoileH1){
        	 Formule formule = CnfReader.parse(label.getText());                 
        	 long b = System.currentTimeMillis();	 
        	 char[] instance = Algorithmes.AlgoAEtoileH1(formule, 4000); 
        	 resultat1.setText("A etoile termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
        	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
        	 StringBuffer buffer = new StringBuffer();
        	 if (instance != null) {
       	      for(int i = 0; i < instance.length; i++)
       	    	buffer.append((char)(instance[i] + '0'));
       	  }
        	 resultat3.setText(buffer.toString());
        } else if (e.getSource() == AlgoAEoileH2){
       	 Formule formule = CnfReader.parse(label.getText());                 
       	 long b = System.currentTimeMillis();	 
       	 char[] instance = Algorithmes.AlgoAEtoileH2(formule, 4000); 
       	 resultat1.setText("A etoile termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
       	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
       	 StringBuffer buffer = new StringBuffer();
       	 if (instance != null) {
      	      for(int i = 0; i < instance.length; i++)
      	    	buffer.append((char)(instance[i] + '0'));
      	  }
       	 resultat3.setText(buffer.toString());
       }else if (e.getSource() == AlgoAEoileH3){
         	 Formule formule = CnfReader.parse(label.getText());                 
           	 long b = System.currentTimeMillis();	 
           	 char[] instance = Algorithmes.AlgoAEtoileH3(formule, 4000); 
           	 resultat1.setText("A etoile termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
           	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
           	 StringBuffer buffer = new StringBuffer();
           	 if (instance != null) {
          	      for(int i = 0; i < instance.length; i++)
          	    	buffer.append((char)(instance[i] + '0'));
          	  }
           	 resultat3.setText(buffer.toString());
           }  else if (e.getSource() == AlgoGenetiqueF1){
      	 Formule formule = CnfReader.parse(label.getText());                 
      	 long b = System.currentTimeMillis();	 
      	 char[] instance = Algorithmes.AlgoGenetiqueF1(formule, 4000); 
      	 resultat1.setText("A etoile termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
      	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
      	 StringBuffer buffer = new StringBuffer();
      	 if (instance != null) {
     	      for(int i = 0; i < instance.length; i++)
     	    	buffer.append((char)(instance[i] + '0'));
     	  }
      	 resultat3.setText(buffer.toString());
      }else if (e.getSource() == Fourmi){
      	 Formule formule = CnfReader.parse(label.getText());                 
       	 long b = System.currentTimeMillis();	 
       	 char[] instance = AlgoACS.AlgoACS(formule, 1000); 
       	 resultat1.setText("ACO termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
       	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
       	 StringBuffer buffer = new StringBuffer();
       	 if (instance != null) {
      	      for(int i = 0; i < instance.length; i++)
      	    	buffer.append((char)(instance[i] + '0'));
      	  }
       	 resultat3.setText(buffer.toString());
       }  else if (e.getSource() == AlgoGenetiqueF1){
  	 Formule formule = CnfReader.parse(label.getText());                 
  	 long b = System.currentTimeMillis();	 
  	 char[] instance = Algorithmes.AlgoGenetiqueF1(formule, 4000); 
  	 resultat1.setText("A etoile termine le calcule dans un temps : " + (System.currentTimeMillis() - b) + " mili seconde");
  	 resultat2.setText("nbr de clause satisfaite = " + formule.check(instance) + "/" + formule.getClauseSize());
  	 StringBuffer buffer = new StringBuffer();
  	 if (instance != null) {
 	      for(int i = 0; i < instance.length; i++)
 	    	buffer.append((char)(instance[i] + '0'));
 	  }
  	 resultat3.setText(buffer.toString());
  }
    }
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Fenetre.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
