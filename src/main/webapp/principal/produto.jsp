<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="head.jsp"></jsp:include>
<body class="app sidebar-mini rtl">
	<jsp:include page="navbar.jsp"></jsp:include>
	<!-- Sidebar menu-->
	<jsp:include page="sidebar.jsp"></jsp:include>
	<main class="app-content">
	
		<div class="row">
			<div class="col-md-6" style="margin: 0 auto;">
				<div class="tile">
					<h3 class="tile-title">Cadastro de Produtos</h3>
					<div class="tile-body">
						<form action="<%= request.getContextPath() %>/ServletProdutoController" method="post" id="formUser">
							<input type="hidden" name="acao" id="acao" value="">
							<div class="form-group">
								<label class="control-label">ID</label> <input
									class="form-control" type="text" readonly="readonly" id="idProduto" name="idProduto" value="${modelProduto.idProduto}">
							</div>
							<div class="form-group">
								<label class="control-label">Nome</label> <input
									class="form-control" type="text" id="nome" name="nome" value="${modelProduto.nome}" required="required">
							</div>
							<div class="form-group">
								<label class="control-label">Valor</label> 
								<input class="form-control" type="text" required="required" id="valor" name="valor" value="${modelProduto.valor}">
							</div>
							
							<div class="form-group">
								<label class="control-label">Estoque</label> 
								<input class="form-control" type="text" required="required" id="estoque" name="estoque" value="${modelProduto.estoque}">
							</div>
							
							<button class="btn btn-primary" type="button"  onclick="limparForm();"><i class="fa fa-plus" aria-hidden="true"></i>Novo</button>
							<button class="btn btn-success"><i class="fa fa-hdd-o" aria-hidden="true"></i>Gravar</button>
							<button class="btn btn-warning" type="button" onclick="criarDelete();"><i class="fa fa-trash" aria-hidden="true"></i>Excluir</button>
							<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario"><i class="fa fa-search" aria-hidden="true"></i>Pesquisar</button>
							
						</form>
						<span id="msg">${msg}</span>
					</div>
					
				</div>
				
					<h3 class="tile-title" style="font-size: 20px;">PRODUTOS CARREGADOS</h3>
				<div style="height: 250px; overflow: scroll;">
					<table class="table table-striped" id="tabelaresultadosview">
						<thead>
							<tr>
								<th>ID</th>
								<th>Nome</th>
								<th>Ver</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items='${modelProdutos}' var='mp'>
								<tr>
									<td><c:out value="${mp.idProduto}"></c:out></td>
									<td><c:out value="${mp.nome}"></c:out></td>
									<td><a class="btn btn btn-success"
										href="<%= request.getContextPath() %>/ServletProdutoController?acao=buscarEditar&idProduto=${mp.idProduto}">Ver</a></td>
								</tr>


							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<nav aria-label="Page navigation example">
					<ul class="pagination">

						<%
						
							int totalPagina = (int) request.getAttribute("totalPagina");
											   
								for (int p = 0; p < totalPagina; p++){
									String url = request.getContextPath() + "/ServletProdutoController?acao=paginar&pagina=" + (p * 5);  
									out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p + 1)+"</a></li>");
							}
						
						%>
											   
					</ul>
				</nav>
				
			</div>
		</div>
	</main>
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de Produtos</h5>
					<button type="button" class="btn-close" data-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="button-addon2">
						<div class="input-group-append">
						<button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="buscarUsuario();">Buscar</button>
						</div>
					</div>
						<div style="height: 250px; overflow: scroll;">
							<table class="table table-striped" id="tabelaresultados">
								<thead>
									<tr>
										<th>ID</th>
										<th>Nome</th>
										<th>Ver</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items='${modelProdutos}' var='mp'>
									<tr>
										<td><c:out value="${mp.idProduto}"></c:out></td>
										<td><c:out value="${mp.nome}"></c:out></td>
										<td><a class="btn btn btn-success"
											href="<%= request.getContextPath() %>/ServletProdutoController?acao=buscarEditar&idProduto=${mp.idProduto}">Ver</a></td>
									</tr>


								</c:forEach>	
								</tbody>
							</table>
						</div>
					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">
							<%
						
								totalPagina = (int) request.getAttribute("totalPagina");
											   
								for (int p = 0; p < totalPagina; p++){
									String url = request.getContextPath() + "/ServletProdutoController?acao=paginar&pagina=" + (p * 5);  
									out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p + 1)+"</a></li>");
								}
						
							%>
						</ul>
					</nav>

					<span id="totalResultados"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascript.jsp"></jsp:include>
	
	<script type="text/javascript">


	function verEditar(idProduto) {
	   
	    var urlAction = document.getElementById('formUser').action;
	    
	    
	    window.location.href = urlAction + '?acao=buscarEditar&idProduto='+idProduto;
	    
	}

	function buscaUserPagAjax(url){
	   
	    
	    var urlAction = document.getElementById('formUser').action;
	    var nomeBusca = document.getElementById('nomeBusca').value;
	    
		 $.ajax({	     
		     method: "get",
		     url : urlAction,
		     data : url,
		     success: function (response, textStatus, xhr) {
			 
			 var json = JSON.parse(response);
			 
			 
			 $('#tabelaresultados > tbody > tr').remove();
			 $("#ulPaginacaoUserAjax > li").remove();
			 
			  for(var p = 0; p < json.length; p++){
			      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].idProduto+'</td> <td> '+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].idProduto+')" type="button" class="btn btn-info">Ver</button></td></tr>');
			  }
			  
			  document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
			  
			    var totalPagina = xhr.getResponseHeader("totalPagina");
		
			  
			    
				  for (var p = 0; p < totalPagina; p++){
				      
			
				      
				      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
				      
				   
				      $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>'); 
				      
				  }
			 
		     }
		     
		 }).fail(function(xhr, status, errorThrown){
		    alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		 });
	    
	}


	function buscarUsuario() {
	    
	    var nomeBusca = document.getElementById('nomeBusca').value;
	    
	    if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){ /*Validando que tem que ter valor pra buscar no banco*/
		
		 var urlAction = document.getElementById('formUser').action;
		
		 $.ajax({
		     
		     method: "get",
		     url : urlAction,
		     data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
		     success: function (response, textStatus, xhr) {
			 
			 var json = JSON.parse(response);
			 
			 
			 $('#tabelaresultados > tbody > tr').remove();
			 $("#ulPaginacaoUserAjax > li").remove();
			 
			  for(var p = 0; p < json.length; p++){
			      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].idProduto+'</td> <td> '+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].idProduto+')" type="button" class="btn btn-info">Ver</button></td></tr>');
			  }
			  
			  document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
			  
			    var totalPagina = xhr.getResponseHeader("totalPagina");
		
			  
			    
				  for (var p = 0; p < totalPagina; p++){
				      
				      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
				      
				   
				      $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
				      
				  }
			 
		     }
		     
		 }).fail(function(xhr, status, errorThrown){
		    alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		 });
		
		
	    }
	    
	}


	function criarDeleteComAjax() {
	    
	    if (confirm('Deseja realmente excluir os dados?')){
		
		 var urlAction = document.getElementById('formUser').action;
		 var idprodutoUser = document.getElementById('idProduto').value;
		 
		 $.ajax({
		     
		     method: "get",
		     url : urlAction,
		     data : "idProduto=" + idprodutoUser + '&acao=deletarajax',
		     success: function (response) {
			 
			  limparForm();
			  document.getElementById('msg').textContent = response;
		     }
		     
		 }).fail(function(xhr, status, errorThrown){
		    alert('Erro ao deletar usuário por id: ' + xhr.responseText);
		 });
		 
		  
	    }
	    
	}



	function criarDelete() {
	    
	    if(confirm('Deseja realmente excluir os dados?')) {
		
		    document.getElementById("formUser").method = 'get';
		    document.getElementById("acao").value = 'deletar';
		    document.getElementById("formUser").submit();
		    
	    }
	    
	}


	function limparForm() {
	    
	    var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/
	    
	    for (p = 0; p < elementos.length; p ++){
		    elementos[p].value = '';
		    
		    $("#nome").focus();
	    }
	}

</script>

</body>
</html>