
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Planilha {
	
		  public static ArrayList run() {
			 ArrayList<String> cod = new ArrayList<String>();
		    BufferedReader br = null;
		    String linha = "";
		    String csvDivisor = ";";
		    try {
		        br = new BufferedReader(new FileReader("/home/jully-dev/Documentos/Projetos/LDMAD_TP_BOVESPA_RABBITMQ/src/csvs/1688438_acoes_bovespa.csv"));
		        for (int i=0;(linha = br.readLine()) != null; i++) {
		        	if(i!=0) {
		            String[] codigo = linha.split(csvDivisor);
		            cod.add(codigo[0]);
		        	}
		        }
		        return cod;

		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		    	
		        if (br != null) {
		            try {
		                br.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
			return null;
		  }
		  
		  public static ArrayList lerLivroOferta() {
				ArrayList<String> cod = new ArrayList<String>();
			    BufferedReader br = null;
			    String linha = "";
			    String csvDivisor = ",";
			    try {
			    	
			        br = new BufferedReader(new FileReader("/home/jully-dev/Documentos/Projetos/LDMAD_TP_BOVESPA_RABBITMQ/src/csvs/LivroDeOfertas.csv"));
			        for (int i=0;(linha = br.readLine()) != null; i++) {
			            String[] codigo = linha.split(csvDivisor);
			            cod.add(linha);
			        }
			        return cod;

			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    } finally {
			    	
			        if (br != null) {
			            try {
			                br.close();
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }
			    }
				return null;
			  }
}
