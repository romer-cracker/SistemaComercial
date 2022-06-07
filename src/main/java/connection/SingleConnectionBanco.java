package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://localhost:5433/vendasRPB?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null; /* OBJETO DE CONEXÃO */
	
	public static Connection getConnection() { 
		return connection;
	}

	static {
		conectar(); /*QUE CHAMA A CLASSE VAI DIRETO PARA A CONEXÃO*/
	}

	public SingleConnectionBanco() { /* QUE TEM UMA INSTANCIA VAI CONECTAR, CONSTRUTOR*/
		conectar();
	}
	

	public static void conectar() {

		try {

			if (connection == null) {
				
				Class.forName("org.postgresql.Driver"); /* CARREGA O DRIVER DE CONEXÃO DO BANCO */
				connection = DriverManager.getConnection(banco, user, senha); /* RETORNA A CONEXÃO */
				connection.setAutoCommit(false); /* PARA NÃO SALVAR AUTOMATICAMENTE */

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
