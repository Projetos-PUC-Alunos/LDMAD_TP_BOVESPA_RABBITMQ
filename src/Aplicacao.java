import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Aplicacao extends Planilha{
	
	  public static void main(String[] argv)  throws Exception {
		
		  while (true) {
	            Object[] possibilities = run().toArray();
	            Object[] operacaoList = {"Compra", "Venda"};
	            int operacao = JOptionPane.showOptionDialog(null, "O que deseja fazer?", "Bolsa de Valores", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, operacaoList, null);
	            String acao = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Ações",
	                    "Bolsa de Valores",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    possibilities,
	                    possibilities[0]);
	            int quant = Integer.parseInt((String) JOptionPane.showInputDialog(null,
	                    "Digite a quantidade:",
	                    "Bolsa de Valores", JOptionPane.INFORMATION_MESSAGE,
	                    null,
	                    null,
	                    ""));
	            double val = Double.parseDouble((String) JOptionPane.showInputDialog(null,
	                    "Digite o valor:",
	                    "Bolsa de Valores", JOptionPane.INFORMATION_MESSAGE,
	                    null,
	                    null,
	                    ""));
	            Corretora c = new Corretora();
	            String a = c.publish(acao, quant, val, operacao == 1 ? "Venda" : "Compra");
	            criarArquivo(a);
	            livroDeOfertas();

	            int option = JOptionPane.showConfirmDialog(null, "Deseja realizar outra operação?", "Continuar", JOptionPane.YES_NO_OPTION);
	            if (option == JOptionPane.NO_OPTION) {
	                break;
	            }
	        }
	  }
	  
	  public static void livroDeOfertas() {
		   ArrayList livroOfertas = lerLivroOferta();
		    String saida = "";
		    saida = "<html>"
		            + "<table border = '1'>"
		            + "<tr><th>Quantidade</th><th>Valor</th></tr>";


		    for (int i = 0; i < livroOfertas.size(); i++) {
		    	String[] livroOfertasSplit = livroOfertas.get(i).toString().split(",");
		    	saida = saida + "<tr><td>" + livroOfertasSplit[0]+ "</td><td>" + livroOfertasSplit[1] + "</td>";
		    }

		    saida = saida + "</table></html>";
		   
		    JOptionPane.showMessageDialog(null,saida);
	  }
	  
	  public static void criarArquivo(String message) {
		  try {
			 System.out.println(message);
		  	String[] messageSplit = message.split(",");
		  	File file = new File("/output/LivroDeOfertas.csv");
	        FileWriter writer;
			
				writer = new FileWriter(file, true);
			
	        BufferedWriter bw = new BufferedWriter(writer);
	        
	        String messageConcat = ( messageSplit[0]+ "," + messageSplit[1]);
	        bw.write(messageConcat);
	        bw.newLine();
	        bw.flush();
	        bw.close();
		  } catch (IOException e) {
				e.printStackTrace();
			}
	  }
	  
}

