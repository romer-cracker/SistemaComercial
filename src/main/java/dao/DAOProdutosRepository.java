package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelProduto;

public class DAOProdutosRepository {

	private Connection connection;
	
	public DAOProdutosRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelProduto GravarProduto(ModelProduto objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO produto (nome, valor, estoque, usuario_id) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getNome());
			statement.setDouble(2, objeto.getValor());
			statement.setInt(3, objeto.getEstoque());
			statement.setLong(4, userLogado);

			statement.execute();
			connection.commit();

		} else {

			String sql = "UPDATE produto SET nome=?, valor=?, estoque=? WHERE id =  " + objeto.getIdProduto() + ";";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, objeto.getNome());
			statement.setDouble(2, objeto.getValor());
			statement.setInt(3, objeto.getEstoque());

			statement.executeUpdate();

			connection.commit();
		}

		return this.consultarProduto(objeto.getNome());
	}
	
	public List<ModelProduto> consultarProdutoList(Long userLogado) throws Exception {

		List<ModelProduto> retorno = new ArrayList<ModelProduto>();

		String sql = "select * from produto where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelProduto modelProduto = new ModelProduto();

			modelProduto.setIdProduto(resultado.getLong("id_produto"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setValor(resultado.getDouble("valor"));
			modelProduto.setEstoque(resultado.getInt("estoque"));

			retorno.add(modelProduto);
		}

		return retorno;
	}
	
	public int consultarProdutoListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from produto  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	
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
	
	public List<ModelProduto> consultarProdutoListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelProduto> retorno = new ArrayList<ModelProduto>();
		
		String sql = "select * from produto  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelProduto modelProduto = new ModelProduto();

			modelProduto.setIdProduto(resultado.getLong("id_produto"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setValor(resultado.getDouble("valor"));
			modelProduto.setEstoque(resultado.getInt("estoque"));

			retorno.add(modelProduto);
		}
		
		
		return retorno;
	}
	
	public List<ModelProduto> consultarProdutoListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelProduto> retorno = new ArrayList<ModelProduto>();
		
		String sql = "select * from produto where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelProduto modelProduto = new ModelProduto();

			modelProduto.setIdProduto(resultado.getLong("id_produto"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setValor(resultado.getDouble("valor"));
			modelProduto.setEstoque(resultado.getInt("estoque"));

			retorno.add(modelProduto);
		}
		
		
		return retorno;
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "SELECT count(1) as total FROM produto WHERE usuario_id = " + userLogado;
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

	public List<ModelProduto> consultarProdutoList(String nome, Long userLogado) throws Exception {

		List<ModelProduto> retorno = new ArrayList<ModelProduto>();

		String sql = "select * from produto  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelProduto modelProduto = new ModelProduto();

			modelProduto.setIdProduto(resultado.getLong("id_produto"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setValor(resultado.getDouble("valor"));
			modelProduto.setEstoque(resultado.getInt("estoque"));

			retorno.add(modelProduto);
		}

		return retorno;
	}

	public ModelProduto consultarProduto(String nome) throws Exception {

		ModelProduto modelProduto = new ModelProduto();

		String sql = "select * from produto where upper(nome) = upper('" + nome + "') and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelProduto.setIdProduto(resultado.getLong("id_produto"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setValor(resultado.getDouble("valor"));
			modelProduto.setEstoque(resultado.getInt("estoque"));
		}

		return modelProduto;

	}

	public ModelProduto consultarProdutoID(String id, Long userLogado) throws Exception {

		ModelProduto modelProduto = new ModelProduto();

		String sql = "select * from produto where id_produto = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelProduto.setIdProduto(resultado.getLong("id_produto"));
			modelProduto.setNome(resultado.getString("nome"));
			modelProduto.setValor(resultado.getDouble("valor"));
			modelProduto.setEstoque(resultado.getInt("estoque"));
		}

		return modelProduto;

	}



	public void deletarProduto(String idProdutoUser) throws Exception {
		String sql = "DELETE FROM produto WHERE id_produto = ? and useradmin is false;";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idProdutoUser));

		prepareSql.executeUpdate();

		connection.commit();

	}
}
