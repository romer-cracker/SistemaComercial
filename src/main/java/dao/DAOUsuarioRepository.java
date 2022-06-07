package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin GravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO usuario (nome, email, perfil, login, senha, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getNome());
			statement.setString(2, objeto.getEmail());
			statement.setString(3, objeto.getPerfil());
			statement.setString(4, objeto.getLogin());
			statement.setString(5, objeto.getSenha());
			statement.setLong(6, userLogado);

			statement.execute();
			connection.commit();

		} else {

			String sql = "UPDATE usuario SET nome=?, email=?, perfil=?, login=?, senha=? WHERE id =  " + objeto.getId() + ";";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getNome());
			statement.setString(2, objeto.getEmail());
			statement.setString(3, objeto.getPerfil());
			statement.setString(4, objeto.getLogin());
			statement.setString(5, objeto.getSenha());

			statement.executeUpdate();

			connection.commit();
		}

		return this.consultaUsuario(objeto.getLogin());
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from usuario where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);
		}

		return retorno;
	}
	
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from usuario  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	
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
	
	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from usuario  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
	}
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from usuario where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "SELECT count(1) as total FROM usuario WHERE usuario_id = " + userLogado;
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

	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from usuario  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			// modelLogin.setSenha(resultado.getString("senha"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from usuario where upper(login) = upper('" + login + "') and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resutlado = statement.executeQuery();

		while (resutlado.next()) /* Se tem resultado */ {

			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
		}

		return modelLogin;

	}

	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from usuario where id = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resutlado = statement.executeQuery();

		while (resutlado.next()) /* Se tem resultado */ {

			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
		}

		return modelLogin;

	}
	
	public ModelLogin consultarUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from usuario where upper(login) = upper('" + login + "')";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		if (resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}

		return modelLogin;
	}


	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) > 0 as existe from usuario where upper(login) = upper('" + login + "');";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resutlado = statement.executeQuery();

		resutlado.next();/* Pra ele entrar nos resultados do sql */
		return resutlado.getBoolean("existe");

	}

	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM usuario WHERE id = ? and useradmin is false;";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idUser));

		prepareSql.executeUpdate();

		connection.commit();

	}

}
