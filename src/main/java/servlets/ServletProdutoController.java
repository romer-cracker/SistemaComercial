package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOProdutosRepository;
import model.ModelProduto;
import util.ServletGenericUtil;


@WebServlet("/ServletProdutoController")
public class ServletProdutoController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOProdutosRepository daoProdutosRepository = new DAOProdutosRepository();
    
    public ServletProdutoController() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idprodutoUser = request.getParameter("idProduto");

				List<ModelProduto> modelProdutos = daoProdutosRepository.consultarProdutoList(super.getUserLogado(request));
				request.setAttribute("modelProdutos", modelProdutos);

				daoProdutosRepository.deletarProduto(idprodutoUser);

				request.setAttribute("msg", "Excluído com sucesso!");
				request.setAttribute("totalPagina", daoProdutosRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/produto.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idprodutoUser = request.getParameter("idProduto");

				daoProdutosRepository.deletarProduto(idprodutoUser);

				response.getWriter().write("Excluído com sucesso!");
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelProduto> dadosJsonUser = daoProdutosRepository.consultarProdutoList(nomeBusca, super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoProdutosRepository.consultarProdutoListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelProduto> dadosJsonUser = daoProdutosRepository.consultarProdutoListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoProdutosRepository.consultarProdutoListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String idProduto = request.getParameter("idProduto");

				ModelProduto modelProduto = daoProdutosRepository.consultarProdutoID(idProduto, super.getUserLogado(request));

				List<ModelProduto> modelProdutos = daoProdutosRepository.consultarProdutoList(super.getUserLogado(request));
				request.setAttribute("modelProdutos", modelProdutos);

				request.setAttribute("msg", "Produto em edição");
				request.setAttribute("modelProduto", modelProduto);
				request.setAttribute("totalPagina", daoProdutosRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/produto.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelProduto> modelProdutos = daoProdutosRepository
						.consultarProdutoList(super.getUserLogado(request));

				request.setAttribute("modelProdutos", modelProdutos);
				request.setAttribute("totalPagina", daoProdutosRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/produto.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelProduto> modelProdutos = daoProdutosRepository.consultarProdutoListPaginada(this.getUserLogado(request), offset);

				request.setAttribute("modelProdutos", modelProdutos);
				request.setAttribute("totalPagina", daoProdutosRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/produto.jsp").forward(request, response);

			} else {

				List<ModelProduto> modelProdutos = daoProdutosRepository.consultarProdutoList(super.getUserLogado(request));
				request.setAttribute("modelProdutos", modelProdutos);
				request.setAttribute("totalPagina", daoProdutosRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
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

			String idProduto = request.getParameter("idProduto");
			String nome = request.getParameter("nome");
			String valor = request.getParameter("valor");
			String estoque = request.getParameter("estoque");

			ModelProduto modelProduto = new ModelProduto();

			modelProduto.setIdProduto(idProduto != null && !idProduto.isEmpty() ? Long.parseLong(idProduto) : null);
			modelProduto.setNome(nome);
			modelProduto.setValor(Double.parseDouble(valor));
			modelProduto.setEstoque(Integer.parseInt(estoque));
			

			if (modelProduto.isNovo()) {
				msg = "Gravado com sucesso!";
			} else {
				msg = "Atualizado com sucesso!";
			}

			modelProduto = daoProdutosRepository.GravarProduto(modelProduto, super.getUserLogado(request));

			List<ModelProduto> modelProdutos = daoProdutosRepository.consultarProdutoList(super.getUserLogado(request));
			request.setAttribute("modelProdutos", modelProdutos);

			request.setAttribute("msg", msg);
			request.setAttribute("modelProduto", modelProduto);
			request.setAttribute("totalPagina", daoProdutosRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/produto.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
