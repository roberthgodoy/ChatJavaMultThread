import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cliente extends Thread
{
	Socket cliente;

	public Cliente(Socket s)
	{
		cliente = s;

	}

	public static void main(String[] args) throws IOException
	{
		System.out.println("Cliente Ativo!");

		String msg_digitada; // mensagem digitada
		String msg_recebida; // mensagem recebida
		String nome_cliente; // nome do cliente
		String assunto_cliente = null; // assunto do cliente

		// cria o stream do teclado			
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

		// cria o socket de acesso ao server hostname na porta 8657
		Socket cliente = new Socket("localhost", 8657);
		System.out.println("Você entrou no chat!"); //nome_cliente +

		// cria os streams de entrada e saida com o servidor
		DataOutputStream saida_servidor = new DataOutputStream(cliente.getOutputStream());

		Thread t = new Cliente(cliente);
		t.start();   


		while (true)
		{
			msg_digitada = teclado.readLine(); // le uma linha do teclado

			if (msg_digitada.startsWith("fim") == true) // testa se o chat deve ser finalizado
				break;

			saida_servidor.writeBytes(msg_digitada + '\n'); // envia a linha para o servidor


		}
		// fecha o cliente
		cliente.close();
		System.out.println("Você saiu do chat!");
	}

	public void run()
	{
		String msg_recebida;
		while (true)
		{		
			try
			{
				BufferedReader entrada_servidor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				msg_recebida = entrada_servidor.readLine();
				System.out.println(msg_recebida); 
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}