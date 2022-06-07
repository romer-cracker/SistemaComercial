<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
	
	<c:set scope="session" var="perfil" value='<%= request.getSession().getAttribute("perfil").toString() %>'></c:set>

<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
	<div class="app-sidebar__user">
		
		<div>
			<p class="app-sidebar__user-name"><%= request.getSession().getAttribute("usuario") %></p>
			<p class="app-sidebar__user-designation">Frontend Developer</p>
		</div>
	</div>
	<ul class="app-menu">
	    <c:if test="${perfil == 'ADMIN'}">
	    
		<li><a class="app-menu__item active" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=listarUser"><i class="app-menu__icon fa fa-users" aria-hidden="true"></i><span class="app-menu__label">Usuários</span></a></li>
	    
	    </c:if>
		
		
		<li><a class="app-menu__item" href="<%= request.getContextPath() %>/ServletClienteController?acao=listarUser""><i class="app-menu__icon fa fa-address-card-o" aria-hidden="true"></i><span class="app-menu__label">Clientes</span></a></li>
		
		
		
	</ul>
</aside>