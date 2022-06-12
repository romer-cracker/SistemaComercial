package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOVendasRepository;
import model.ModelVendas;
import util.ServletGenericUtil;

@WebServlet("/ServletVendaController")
public class ServletVendaController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOVendasRepository daoVendasRepository = new DAOVendasRepository();
       
    
    public ServletVendaController() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idVen = request.getParameter("idVendas");

				List<ModelVendas> modellVendas = daoVendasRepository.consultarVendasList(super.getUserLogado(request));
				request.setAttribute("modellVendas", modellVendas);

				daoVendasRepository.deletarVendas(idVen);

				request.setAttribute("msg", "Excluído com sucesso!");
				request.setAttribute("totalPagina", daoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idVen = request.getParameter("idVendas");

				daoVendasRepository.deletarVendas(idVen);

				response.getWriter().write("Excluído com sucesso!");
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelVendas> dadosJsonUser = daoVendasRepository.consultarVendasList(nomeBusca,
						super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoVendasRepository.consultarVendasListTotalPaginaPaginacao(super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelVendas> dadosJsonUser = daoVendasRepository.consultarVendasListOffSet(super.getUserLogado(request), Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoVendasRepository.consultarVendasListTotalPaginaPaginacao(super.getUserLogado(request)));
				response.getWriter().write(json);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String idVendas = request.getParameter("idVendas");

				ModelVendas modelVendas = daoVendasRepository.consultarVendasID(idVendas, super.getUserLogado(request));

				List<ModelVendas> modellVendas = daoVendasRepository.consultarVendasList(super.getUserLogado(request));
				request.setAttribute("modellVendas", modellVendas);

				request.setAttribute("msg", "Venda em edição");
				request.setAttribute("modelVendas", modelVendas);
				request.setAttribute("totalPagina", daoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelVendas> modellVendas = daoVendasRepository
						.consultarVendasList(super.getUserLogado(request));

				request.setAttribute("modellVendas", modellVendas);
				request.setAttribute("totalPagina", daoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelVendas> modellVendas = daoVendasRepository
						.consultarVendasListPaginada(this.getUserLogado(request), offset);

				request.setAttribute("modellVendas", modellVendas);
				request.setAttribute("totalPagina", daoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);

			} else {

				List<ModelVendas> modellVendas = daoVendasRepository.consultarVendasList(super.getUserLogado(request));
				request.setAttribute("modellVendas", modellVendas);
				request.setAttribute("totalPagina", daoVendasRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
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

			String idVendas = request.getParameter("idVendas");
			String fk_cliente = request.getParameter("fk_cliente");
			String dataVenda = request.getParameter("dataVenda");
			String valor = request.getParameter("valor");
			String valorTotal = request.getParameter("valorTotal");
			String desconto = request.getParameter("desconto");

			ModelVendas modelVendas = new ModelVendas();

			modelVendas.setIdVendas(idVendas != null && !idVendas.isEmpty() ? Long.parseLong(idVendas) : null);
			modelVendas.setFk_cliente(fk_cliente != null && !fk_cliente.isEmpty() ? Long.parseLong(fk_cliente) : null);
			modelVendas.setDataVenda(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataVenda))));
			modelVendas.setValor(Double.parseDouble(valor));
			modelVendas.setValorTotal(Double.parseDouble(valorTotal));
			modelVendas.setDesconto(Double.parseDouble(desconto));

			if (modelVendas.isNovo()) {
				msg = "Gravado com sucesso!";
			} else {
				msg = "Atualizado com sucesso!";
			}

			modelVendas = daoVendasRepository.GravarVenda(modelVendas, super.getUserLogado(request));

			List<ModelVendas> modellVendas = daoVendasRepository.consultarVendasList(super.getUserLogado(request));
			request.setAttribute("modellVendas", modellVendas);

			request.setAttribute("msg", msg);
			request.setAttribute("modelVendas", modelVendas);
			request.setAttribute("totalPagina", daoVendasRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
