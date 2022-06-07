<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
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
					<h3 class="tile-title">Cadastro de Usuários</h3>
					<div class="tile-body">
						<form action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
							<input type="hidden" name="acao" id="acao" value="">
							<div class="form-group">
								<label class="control-label">ID</label> <input
									class="form-control" type="text" readonly="readonly" id="id" name="id" value="${modelLogin.id}">
							</div>
							<div class="form-group">
								<label class="control-label">Nome</label> <input
									class="form-control" type="text" id="nome" name="nome" value="${modelLogin.nome}" required="required">
							</div>
							<div class="form-group">
								<label class="control-label">Email</label> <input class="form-control" type="email" required="required" id="email" name="email" value="${modelLogin.email}">
							</div>
							
							<div class="form-group">
								<select class="form-control"
									aria-label=".form-select-sm example" name="perfil">
									<option selected>Perfil</option>
									<option value="ADMIN" <%
										ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
									
										if(modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
											out.print(" ");
											out.print("selected=\"selected\"");
											out.print(" ");
										}
									
									%>>Supervisor</option>
									
									<option value="GERENTE" <%
									
										modelLogin = (ModelLogin) request.getAttribute("modelLogin");
									
										if (modelLogin != null && modelLogin.getPerfil().equals("GERENTE")) {
											out.print(" ");
											out.print("selected=\"selected\"");
											out.print(" ");
										}
									
									%>>Gerente de Loja</option>
									
									<option value="SUBGERENTE" <%
									
										modelLogin = (ModelLogin) request.getAttribute("modelLogin");
									
										if (modelLogin != null && modelLogin.getPerfil().equals("SUBGERENTE")) {
											out.print(" ");
										 	out.print("selected=\"selected\"");
											out.print(" ");
										}
										
									%>>Subgerente de Loja</option>
									
								</select>
							</div>
							
							
							<div class="form-group">
								<label class="control-label">Login</label> <input
									class="form-control" type="text" id="login" name="login" value="${modelLogin.login}" autocomplete="off" required="required">
							</div>
							<div class="form-group">
								<label class="control-label">Senha</label> <input
									class="form-control" type="password" id="senha" name="senha" value="${modelLogin.senha}" autocomplete="off" required="required">
							</div>
							
							<button class="btn btn-primary" type="button"  onclick="limparForm();"><i class="fa fa-plus" aria-hidden="true"></i>Novo</button>
							<button class="btn btn-success"><i class="fa fa-hdd-o" aria-hidden="true"></i>Gravar</button>
							<button class="btn btn-warning" type="button" onclick="criarDelete();"><i class="fa fa-trash" aria-hidden="true"></i>Excluir</button>
							<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario"><i class="fa fa-search" aria-hidden="true"></i>Pesquisar</button>
						</form>
						<span id="msg">${msg}</span>
					</div>
					
				</div>
				
					<h3 class="tile-title" style="font-size: 20px;">USUÁRIOS CARREGADOS</h3>
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
							<c:forEach items='${modelLogins}' var='ml'>
								<tr>
									<td><c:out value="${ml.id}"></c:out></td>
									<td><c:out value="${ml.nome}"></c:out></td>
									<td><a class="btn btn btn-success"
										href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
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
									String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);  
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
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de Usuários</h5>
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
								<c:forEach items='${modelLogins}' var='ml'>
									<tr>
										<td><c:out value="${ml.id}"></c:out></td>
										<td><c:out value="${ml.nome}"></c:out></td>
										<td><a class="btn btn btn-success"
											href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
									</tr>


								</c:forEach>	
								</tbody>
							</table>
						</div>
					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">

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


	function verEditar(id) {
	   
	    var urlAction = document.getElementById('formUser').action;
	    
	    
	    window.location.href = urlAction + '?acao=buscarEditar&id='+id;
	    
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
			      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td> '+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td></tr>');
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
			      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td> '+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td></tr>');
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
		 var idUser = document.getElementById('id').value;
		 
		 $.ajax({
		     
		     method: "get",
		     url : urlAction,
		     data : "id=" + idUser + '&acao=deletarajax',
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