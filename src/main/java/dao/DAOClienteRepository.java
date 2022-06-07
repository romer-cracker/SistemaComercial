package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelCliente;

public class DAOClienteRepository {

	private Connection connection;
	
	public DAOClienteRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelCliente GravarCliente(ModelCliente objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO cliente (nome, endereco, bairro, cidade, cep, uf, telefone, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getNome());
			statement.setString(2, objeto.getEndereco());
			statement.setString(3, objeto.getBairro());
			statement.setString(4, objeto.getCidade());
			statement.setString(5, objeto.getCep());
			statement.setString(6, objeto.getUf());
			statement.setString(7, objeto.getTelefone());
			statement.setLong(8, userLogado);

			statement.execute();
			connection.commit();

		} else {

			String sql = "UPDATE cliente SET nome=?, endereco=?, bairro=?, cidade=?, cep=?, uf=?, telefone=? WHERE id_cliente =  " + objeto.getIdCliente() + ";";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getNome());
			statement.setString(2, objeto.getEndereco());
			statement.setString(3, objeto.getBairro());
			statement.setString(4, objeto.getCidade());
			statement.setString(5, objeto.getCep());
			statement.setString(6, objeto.getUf());
			statement.setString(7, objeto.getTelefone());

			statement.executeUpdate();

			connection.commit();
		}

		return this.consultarCliente(objeto.getNome());
	}
	
	public List<ModelCliente> consultarClienteList(Long userLogado) throws Exception {

		List<ModelCliente> retorno = new ArrayList<ModelCliente>();

		String sql = "select * from cliente where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelCliente modelCliente  = new ModelCliente();

			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));

			retorno.add(modelCliente);
		}

		return retorno;
	}
	
	public int consultarClienteListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from cliente  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
		
	}
	
	public List<ModelCliente> consultarClienteListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelCliente> retorno = new ArrayList<ModelCliente>();
		
		String sql = "select * from cliente  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelCliente modelCliente  = new ModelCliente();

			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));

			retorno.add(modelCliente);
		}
		
		
		return retorno;
	}
	
	public List<ModelCliente> consultarClienteListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelCliente> retorno = new ArrayList<ModelCliente>();
		
		String sql = "select * from cliente where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelCliente modelCliente  = new ModelCliente();

			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));

			retorno.add(modelCliente);
		}
		
		
		return retorno;
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "SELECT count(1) as total FROM cliente WHERE usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		resultSet.next();
		
		Double cadastros = resultSet.getDouble("total");
		
		Double porPagina = 5.0;
		
		Double pagina= cadastros / porPagina;
		
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
	}

	public List<ModelCliente> consultarClienteList(String nome, Long userLogado) throws Exception {

		List<ModelCliente> retorno = new ArrayList<ModelCliente>();

		String sql = "select * from cliente  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelCliente modelCliente  = new ModelCliente();

			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));

			retorno.add(modelCliente);
		}

		return retorno;
	}

	public ModelCliente consultarCliente(String nome) throws Exception {

		ModelCliente modelCliente = new ModelCliente();

		String sql = "select * from cliente where upper(nome) = upper('" + nome + "') and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));
		}

		return modelCliente;

	}

	public ModelCliente consultarClienteID(String idCliente, Long userLogado) throws Exception {

		ModelCliente modelCliente = new ModelCliente();

		String sql = "select * from cliente where id_cliente = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idCliente));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));
		}

		return modelCliente;

	}
	
	public ModelCliente consultarClienteLogado(String nome) throws Exception {

		ModelCliente modelCliente = new ModelCliente();

		String sql = "select * from cliente where upper(nome) = upper('" + nome + "')";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		if (resultado.next()) {
			
			modelCliente.setIdCliente(resultado.getLong("id_cliente"));
			modelCliente.setNome(resultado.getString("nome"));
			modelCliente.setEndereco(resultado.getString("endereco"));
			modelCliente.setBairro(resultado.getString("bairro"));
			modelCliente.setCidade(resultado.getString("cidade"));
			modelCliente.setCep(resultado.getString("cep"));
			modelCliente.setUf(resultado.getString("uf"));
			modelCliente.setTelefone(resultado.getString("telefone"));
		}

		return modelCliente;
	}


	public void deletarCli(String idCli) throws Exception {
		String sql = "DELETE FROM cliente WHERE id_cliente = ? and useradmin is false;";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idCli));

		prepareSql.executeUpdate();

		connection.commit();

	}
}
