package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOProdutoVendasRepository;
import model.ModelVendasProdutos;
import util.ServletGenericUtil;


@WebServlet("/ServletVendaProdutosController")
public class ServletVendaProdutosController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOProdutoVendasRepository daoProdutoVendasRepository = new DAOProdutoVendasRepository();
       
   
    public ServletVendaProdutosController() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idProVen = request.getParameter("idVenda_produto");

				List<ModelVendasProdutos> modellVendasProdutos = daoProdutoVendasRepository.consultarVendaProdutosList(super.getUserLogado(request));
				request.setAttribute("modellVendasProdutos", modellVendasProdutos);

				daoProdutoVendasRepository.deletarVendaProdutos(idProVen);

				request.setAttribute("msg", "Excluído com sucesso!");
				request.setAttribute("totalPagina", daoProdutoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/venda-produtos.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idProVen = request.getParameter("idVenda_produto");

				daoProdutoVendasRepository.deletarVendaProdutos(idProVen);

				response.getWriter().write("Excluído com sucesso!");
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelVendasProdutos> dadosJsonUser = daoProdutoVendasRepository.consultarVendaProdutosList(super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoProdutoVendasRepository.consultarVendaProdutosListTotalPaginaPaginacao(super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelVendasProdutos> dadosJsonUser = daoProdutoVendasRepository.consultarVendaProdutosListOffSet(super.getUserLogado(request), Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoProdutoVendasRepository.consultarVendaProdutosListTotalPaginaPaginacao(super.getUserLogado(request)));
				response.getWriter().write(json);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String idVenda_produto = request.getParameter("idVenda_produto");

				ModelVendasProdutos modelVendasProdutos = daoProdutoVendasRepository.consultarVendaProdutosID(idVenda_produto, super.getUserLogado(request));

				List<ModelVendasProdutos> modellVendaProdutos = daoProdutoVendasRepository.consultarVendaProdutosList(super.getUserLogado(request));
				request.setAttribute("modelVendasProdutos", modellVendaProdutos);

				request.setAttribute("msg", "Venda de produtos em edição");
				request.setAttribute("modelVendasProdutos", modelVendasProdutos);
				request.setAttribute("totalPagina", daoProdutoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/venda-produtos.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelVendasProdutos> modellVendaProdutos = daoProdutoVendasRepository.consultarVendaProdutosList(super.getUserLogado(request));

				request.setAttribute("modellVendaProdutos", modellVendaProdutos);
				request.setAttribute("totalPagina", daoProdutoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/venda-produtos.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelVendasProdutos> modellVendaProdutos = daoProdutoVendasRepository
						.consultarVendaProdutosListPaginada(this.getUserLogado(request), offset);

				request.setAttribute("modellVendaProdutos", modellVendaProdutos);
				request.setAttribute("totalPagina", daoProdutoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/venda-produtos.jsp").forward(request, response);

			} else {

				List<ModelVendasProdutos> modellVendaProdutos = daoProdutoVendasRepository.consultarVendaProdutosList(super.getUserLogado(request));
				
				request.setAttribute("modellVendaProdutos", modellVendaProdutos);
				request.setAttribute("totalPagina", daoProdutoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/venda-produtos.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String msg = "Operação realizada com sucesso!";

			String idVenda_produto = request.getParameter("idVenda_produto");
			String fk_cliente = request.getParameter("fk_cliente");
			String fk_produto = request.getParameter("fk_produto");
			String fk_venda = request.getParameter("fk_venda");
			String valor = request.getParameter("valor");
			String quantidade = request.getParameter("quantidade");
			
			ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();

			modelVendasProdutos.setIdVenda_produto(idVenda_produto != null && !idVenda_produto.isEmpty() ? Long.parseLong(idVenda_produto) : null);
			modelVendasProdutos.setFk_cliente(fk_cliente != null && !fk_cliente.isEmpty() ? Long.parseLong(fk_cliente) : null);
			modelVendasProdutos.setFk_produto(fk_produto != null && !fk_produto.isEmpty() ? Long.parseLong(fk_produto) : null);
			modelVendasProdutos.setFk_venda(fk_venda != null && !fk_venda.isEmpty() ? Long.parseLong(fk_venda) : null);
			modelVendasProdutos.setValor(Double.parseDouble(valor));
			modelVendasProdutos.setQuantidade(Integer.parseInt(quantidade));
			

			if (modelVendasProdutos.isNovo()) {
				msg = "Gravado com sucesso!";
			} else {
				msg = "Atualizado com sucesso!";
			}

			modelVendasProdutos = daoProdutoVendasRepository.GravarVendaProdutos(modelVendasProdutos, super.getUserLogado(request));

			List<ModelVendasProdutos> modellVendaProdutos = daoProdutoVendasRepository.consultarVendaProdutosList(super.getUserLogado(request));
			request.setAttribute("modellVendaProdutos", modellVendaProdutos);

			request.setAttribute("msg", msg);
			request.setAttribute("modelVendasProdutos", modelVendasProdutos);
			request.setAttribute("totalPagina", daoProdutoVendasRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/venda-produtos.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
