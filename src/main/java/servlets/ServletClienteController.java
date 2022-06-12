package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOClienteRepository;
import model.ModelCliente;
import util.ServletGenericUtil;

@WebServlet("/ServletClienteController")
public class ServletClienteController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOClienteRepository daoClienteRepository = new DAOClienteRepository();

	public ServletClienteController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idCli = request.getParameter("idCliente");

				List<ModelCliente> modelClientes = daoClienteRepository
						.consultarClienteList(super.getUserLogado(request));
				request.setAttribute("modelClientes", modelClientes);

				daoClienteRepository.deletarCli(idCli);

				request.setAttribute("msg", "Excluído com sucesso!");
				request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idCli = request.getParameter("idCliente");

				daoClienteRepository.deletarCli(idCli);

				response.getWriter().write("Excluído com sucesso!");
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelCliente> dadosJsonUser = daoClienteRepository.consultarClienteList(nomeBusca,
						super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoClienteRepository
						.consultarClienteListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelCliente> dadosJsonUser = daoClienteRepository.consultarClienteListOffSet(nomeBusca,
						super.getUserLogado(request), Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.addHeader("totalPagina", "" + daoClienteRepository
						.consultarClienteListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String idCliente = request.getParameter("idCliente");

				ModelCliente modelCliente = daoClienteRepository.consultarClienteID(idCliente,
						super.getUserLogado(request));

				List<ModelCliente> modelClientes = daoClienteRepository
						.consultarClienteList(super.getUserLogado(request));
				request.setAttribute("modelClientes", modelClientes);

				request.setAttribute("msg", "Cliente em edição");
				request.setAttribute("modelCliente", modelCliente);
				request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				List<ModelCliente> modelClientes = daoClienteRepository
						.consultarClienteList(super.getUserLogado(request));

				request.setAttribute("modelClientes", modelClientes);
				request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelCliente> modelClientes = daoClienteRepository
						.consultarClienteListPaginada(this.getUserLogado(request), offset);

				request.setAttribute("modelClientes", modelClientes);
				request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);

			} else {

				List<ModelCliente> modelClientes = daoClienteRepository
						.consultarClienteList(super.getUserLogado(request));
				request.setAttribute("modelClientes", modelClientes);
				request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String msg = "Operação realizada com sucesso!";

			String idCliente = request.getParameter("idCliente");
			String nome = request.getParameter("nome");
			String endereco = request.getParameter("endereco");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String cep = request.getParameter("cep");
			String uf = request.getParameter("uf");
			String telefone = request.getParameter("telefone");

			ModelCliente modelCliente = new ModelCliente();

			modelCliente.setIdCliente(idCliente != null && !idCliente.isEmpty() ? Long.parseLong(idCliente) : null);
			modelCliente.setNome(nome);
			modelCliente.setEndereco(endereco);
			modelCliente.setBairro(bairro);
			modelCliente.setCidade(cidade);
			modelCliente.setCep(cep);
			modelCliente.setUf(uf);
			modelCliente.setTelefone(telefone);

			if (modelCliente.isNovo()) {
				msg = "Gravado com sucesso!";
			} else {
				msg = "Atualizado com sucesso!";
			}

			modelCliente = daoClienteRepository.GravarCliente(modelCliente, super.getUserLogado(request));

			List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
			request.setAttribute("modelClientes", modelClientes);

			request.setAttribute("msg", msg);
			request.setAttribute("modelCliente", modelCliente);
			request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
