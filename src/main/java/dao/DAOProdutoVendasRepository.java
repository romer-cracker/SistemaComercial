package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelVendasProdutos;

public class DAOProdutoVendasRepository {

	private Connection connection;
	
	public DAOProdutoVendasRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelVendasProdutos GravarVendaProdutos(ModelVendasProdutos objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO venda_produto (fk_produto, fk_cliente, fk_venda, valor, quantidade, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setLong(1, objeto.getFk_produto());
			statement.setLong(2, objeto.getFk_cliente());
			statement.setLong(3, objeto.getFk_venda());
			statement.setDouble(4, objeto.getValor());
			statement.setInt(5, objeto.getQuantidade());
			statement.setLong(6, userLogado);

			statement.execute();
			connection.commit();

		} else {

			String sql = "UPDATE venda_produto SET fk_produto=?, fk_cliente=?, fk_venda=?, valor=?, quantidade=?, cep=? WHERE id_venda_produto =  " + objeto.getIdVenda_produto() + ";";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setLong(1, objeto.getFk_produto());
			statement.setLong(2, objeto.getFk_cliente());
			statement.setLong(3, objeto.getFk_venda());
			statement.setDouble(4, objeto.getValor());
			statement.setInt(5, objeto.getQuantidade());

			statement.executeUpdate();

			connection.commit();
		}

		return this.consultarVendasProdutos(objeto.getFk_produto());
	}
	
	public List<ModelVendasProdutos> consultarVendaProdutosList(Long userLogado) throws Exception {

		List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();

		String sql = "select * from venda_produto where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

			modelVendasProdutos.setIdVenda_produto(resultado.getLong("id_venda_produto"));
			modelVendasProdutos.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendasProdutos.setFk_produto(resultado.getLong("fk_produto"));
			modelVendasProdutos.setFk_venda(resultado.getLong("fk_venda"));
			modelVendasProdutos.setValor(resultado.getDouble("valor"));
			modelVendasProdutos.setQuantidade(resultado.getInt("quantidade"));

			retorno.add(modelVendasProdutos);
		}

		return retorno;
	}
	
	public int consultarVendaProdutosListTotalPaginaPaginacao(Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from venda_produto  where useradmin is false and usuario_id = ? ";
	
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, userLogado);
		
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
	
	public List<ModelVendasProdutos> consultarVendaProdutosListOffSet(Long userLogado, int offset) throws Exception {
		
		List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();
		
		String sql = "select * from venda_produto  where useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

			modelVendasProdutos.setIdVenda_produto(resultado.getLong("id_venda_produto"));
			modelVendasProdutos.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendasProdutos.setFk_produto(resultado.getLong("fk_produto"));
			modelVendasProdutos.setFk_venda(resultado.getLong("fk_venda"));
			modelVendasProdutos.setValor(resultado.getDouble("valor"));
			modelVendasProdutos.setQuantidade(resultado.getInt("quantidade"));

			retorno.add(modelVendasProdutos);
		}
		
		
		return retorno;
	}
	
	public List<ModelVendasProdutos> consultarVendaProdutosListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelVendasProdutos> retorno = new ArrayList<ModelVendasProdutos>();
		
		String sql = "select * from venda_produto where useradmin is false and usuario_id = " + userLogado + " order by fk_produto offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

			modelVendasProdutos.setIdVenda_produto(resultado.getLong("id_venda_produto"));
			modelVendasProdutos.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendasProdutos.setFk_produto(resultado.getLong("fk_produto"));
			modelVendasProdutos.setFk_venda(resultado.getLong("fk_venda"));
			modelVendasProdutos.setValor(resultado.getDouble("valor"));
			modelVendasProdutos.setQuantidade(resultado.getInt("quantidade"));

			retorno.add(modelVendasProdutos);
		}
		
		
		return retorno;
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "SELECT count(1) as total FROM venda_produto WHERE usuario_id = " + userLogado;
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

	public ModelVendasProdutos consultarVendasProdutos(Long fk_produto) throws Exception {

		ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

		String sql = "select * from venda_produto where fk_produto = '" + fk_produto + "' and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelVendasProdutos.setIdVenda_produto(resultado.getLong("id_venda_produto"));
			modelVendasProdutos.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendasProdutos.setFk_produto(resultado.getLong("fk_produto"));
			modelVendasProdutos.setFk_venda(resultado.getLong("fk_venda"));
			modelVendasProdutos.setValor(resultado.getDouble("valor"));
			modelVendasProdutos.setQuantidade(resultado.getInt("quantidade"));
		}

		return modelVendasProdutos;

	}

	public ModelVendasProdutos consultarVendaProdutosID(String idVenda_produto, Long userLogado) throws Exception {

		ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

		String sql = "select * from venda_produto where idVenda_produto = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idVenda_produto));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelVendasProdutos.setIdVenda_produto(resultado.getLong("id_venda_produto"));
			modelVendasProdutos.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendasProdutos.setFk_produto(resultado.getLong("fk_produto"));
			modelVendasProdutos.setFk_venda(resultado.getLong("fk_venda"));
			modelVendasProdutos.setValor(resultado.getDouble("valor"));
			modelVendasProdutos.setQuantidade(resultado.getInt("quantidade"));
		}

		return modelVendasProdutos;

	}


	public void deletarVendaProdutos(String idProVen) throws Exception {
		String sql = "DELETE FROM cliente WHERE idVenda_produto = ? and useradmin is false;";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idProVen));

		prepareSql.executeUpdate();

		connection.commit();

	}
}
