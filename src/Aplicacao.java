import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.rabbitmq.client.Connection;


public class Aplicacao extends Planilha{
	
	  public static void main(String[] argv)  throws Exception {
		
		// Obtém a escolha do usuário
		boolean escolhaCompra = obterEscolhaDoUsuario();

		// Obtém a conexão com o RabbitMQ conforme a escolha do usuário
		Connection conn = ServicoMensageria.getConnection(escolhaCompra);

		Object[] possibilities = run().toArray();
		String acao = (String) JOptionPane.showInputDialog(null, "Ações", "Bolsa de Valores", JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
		int quant = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Digite a quantidade:", "Bolsa de Valores", JOptionPane.INFORMATION_MESSAGE, null, null, ""));
		double val = Double.parseDouble((String) JOptionPane.showInputDialog(null, "Digite o valor:", "Bolsa de Valores", JOptionPane.INFORMATION_MESSAGE, null, null, ""));

		Thread corretoraThread = new Thread(() -> {
			Corretora c = new Corretora(conn);
			String a = c.publish(acao, quant, val, (!escolhaCompra ? "Venda" : "Compra"));
			criarArquivo(a);
			livroDeOfertas();
		});
		corretoraThread.start();
	}

	public static boolean obterEscolhaDoUsuario() {
		Object[] operacaoList = {"Compra", "Venda"};
		int operacao = JOptionPane.showOptionDialog(null, "O que deseja fazer?", "Bolsa de Valores", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, operacaoList, null);
		return operacao == 0; // Retorna true se a escolha for compra, false se for venda
    }
	  
	public static void livroDeOfertas() {
		ArrayList<String> livroOfertas = lerLivroOferta();
		StringBuilder saida = new StringBuilder();
		saida.append("<html><table border='1'><tr><th>Quantidade</th><th>Valor</th></tr>");
	
		for (String oferta : livroOfertas) {
			String[] ofertaSplit = oferta.split(",");
			saida.append("<tr><td>").append(ofertaSplit[0]).append("</td><td>").append(ofertaSplit[1]).append("</td></tr>");
		}
	
		saida.append("</table></html>");
	
		// Atualiza a interface gráfica usando SwingUtilities.invokeLater
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(null, saida.toString());
		});
	}
	  
	  public static void criarArquivo(String message) {
		try {
			File file = new File("src/csvs/LivroDeOfertas.csv");  // Corrigido o caminho do arquivo
			FileWriter writer = new FileWriter(file, true);  // O parâmetro "true" indica que será feito um append ao arquivo existente
	
			BufferedWriter bw = new BufferedWriter(writer);
	
			String[] messageSplit = message.split(",");
			String messageConcat = messageSplit[0] + "," + messageSplit[1];
	
			bw.write(messageConcat);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	  
}

