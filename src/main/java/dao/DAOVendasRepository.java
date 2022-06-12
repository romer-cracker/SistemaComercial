package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelVendas;

public class DAOVendasRepository {

	private Connection connection;
	
	public DAOVendasRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelVendas GravarVenda(ModelVendas objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO vendas (fk_cliente, data_venda, valor, valor_total, desconto, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setLong(1, objeto.getFk_cliente());
			statement.setDate(2, objeto.getDataVenda());
			statement.setDouble(3, objeto.getValor());
			statement.setDouble(4, objeto.getValorTotal());
			statement.setDouble(5, objeto.getDesconto());
			statement.setLong(6, userLogado);

			statement.execute();
			connection.commit();

		} else {

			String sql = "UPDATE vendas SET fk_cliente=?, data_venda=?, valor=?, valor_total=?, desconto=? WHERE id_vendas =  " + objeto.getIdVendas() + ";";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setLong(1, objeto.getFk_cliente());
			statement.setDate(2, objeto.getDataVenda());
			statement.setDouble(3, objeto.getValor());
			statement.setDouble(4, objeto.getValorTotal());
			statement.setDouble(5, objeto.getDesconto());

			statement.executeUpdate();

			connection.commit();
		}

		return this.consultarVendas(objeto.getFk_cliente());
	}
	
	public List<ModelVendas> consultarVendasList(Long userLogado) throws Exception {

		List<ModelVendas> retorno = new ArrayList<ModelVendas>();

		String sql = "select * from vendas where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelVendas modelVendas = new ModelVendas();

			modelVendas.setIdVendas(resultado.getLong("id_vendas"));
			modelVendas.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendas.setDataVenda(resultado.getDate("data_venda"));
			modelVendas.setValor(resultado.getDouble("valor"));
			modelVendas.setValorTotal(resultado.getDouble("valor_total"));
			modelVendas.setDesconto(resultado.getDouble("desconto"));
			
			retorno.add(modelVendas);
		}

		return retorno;
	}
	
	public int consultarVendasListTotalPaginaPaginacao(Long userLogado) throws Exception {
		
		
		String sql = "select count(1) as total from vendas  where useradmin is false and usuario_id = ? ";
	
		PreparedStatement statement = connection.prepareStatement(sql);
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
	
	public List<ModelVendas> consultarVendasListOffSet(Long userLogado, int offset) throws Exception {
		
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		
		String sql = "select * from vendas where useradmin is false and usuario_id = ? offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelVendas modelVendas = new ModelVendas();

			modelVendas.setIdVendas(resultado.getLong("id_vendas"));
			modelVendas.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendas.setDataVenda(resultado.getDate("data_venda"));
			modelVendas.setValor(resultado.getDouble("valor"));
			modelVendas.setValorTotal(resultado.getDouble("valor_total"));
			modelVendas.setDesconto(resultado.getDouble("desconto"));
			
			retorno.add(modelVendas);
		}
		
		
		return retorno;
	}
	
	public List<ModelVendas> consultarVendasListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelVendas> retorno = new ArrayList<ModelVendas>();
		
		String sql = "select * from vendas where useradmin is false and usuario_id = " + userLogado + " order by fk_cliente offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			ModelVendas modelVendas = new ModelVendas();

			modelVendas.setIdVendas(resultado.getLong("id_vendas"));
			modelVendas.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendas.setDataVenda(resultado.getDate("data_venda"));
			modelVendas.setValor(resultado.getDouble("valor"));
			modelVendas.setValorTotal(resultado.getDouble("valor_total"));
			modelVendas.setDesconto(resultado.getDouble("desconto"));
			
			retorno.add(modelVendas);
		}
		
		
		return retorno;
	}
	
	public int totalPagina(Long userLogado)throws Exception {
		
		String sql = "SELECT count(1) as total FROM vendas WHERE usuario_id = " + userLogado;
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

	public List<ModelVendas> consultarVendasList(String fk_cliente, Long userLogado) throws Exception {

		List<ModelVendas> retorno = new ArrayList<ModelVendas>();

		String sql = "select * from vendas  where useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + fk_cliente + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

			ModelVendas modelVendas = new ModelVendas();

			modelVendas.setIdVendas(resultado.getLong("id_vendas"));
			modelVendas.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendas.setDataVenda(resultado.getDate("data_venda"));
			modelVendas.setValor(resultado.getDouble("valor"));
			modelVendas.setValorTotal(resultado.getDouble("valor_total"));
			modelVendas.setDesconto(resultado.getDouble("desconto"));
			
			retorno.add(modelVendas);
		}

		return retorno;
	}

	public ModelVendas consultarVendas(Long fk_cliente) throws Exception {

		ModelVendas modelVendas = new ModelVendas();

		String sql = "select * from vendas where fk_cliente = '" + fk_cliente + "' and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelVendas.setIdVendas(resultado.getLong("id_vendas"));
			modelVendas.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendas.setDataVenda(resultado.getDate("data_venda"));
			modelVendas.setValor(resultado.getDouble("valor"));
			modelVendas.setValorTotal(resultado.getDouble("valor_total"));
			modelVendas.setDesconto(resultado.getDouble("desconto"));
		}

		return modelVendas;

	}

	public ModelVendas consultarVendasID(String idVendas, Long userLogado) throws Exception {

		ModelVendas modelVendas = new ModelVendas();

		String sql = "select * from vendas where id_vendas = ? and useradmin is false and usuario_id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idVendas));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* Se tem resultado */ {

			modelVendas.setIdVendas(resultado.getLong("id_vendas"));
			modelVendas.setFk_cliente(resultado.getLong("fk_cliente"));
			modelVendas.setDataVenda(resultado.getDate("data_venda"));
			modelVendas.setValor(resultado.getDouble("valor"));
			modelVendas.setValorTotal(resultado.getDouble("valor_total"));
			modelVendas.setDesconto(resultado.getDouble("desconto"));
		}

		return modelVendas;

	}
	


	public void deletarVendas(String idVen) throws Exception {
		String sql = "DELETE FROM vendas WHERE id_cliente = ? and useradmin is false;";

		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idVen));

		prepareSql.executeUpdate();

		connection.commit();

	}
}
