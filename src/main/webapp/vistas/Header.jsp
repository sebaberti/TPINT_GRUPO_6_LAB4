<%@ page session="true" %>
<%
    String usuarioLogueado = (String) session.getAttribute("usuarioNombre"); 
    String tipoUsuario = (String) session.getAttribute("usuarioTipo");
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="Inicio.jsp">
            <img src="https://img.icons8.com/ios-filled/30/ffffff/bank.png" alt="Logo" class="me-2">
            Banco SIX
        </a>
        <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">
                <% if (usuarioLogueado == null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="Login.jsp">Iniciar Sesión</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="registro.jsp">Registrarse</a>
                    </li>
                <% } else { %>
                    <li class="nav-item">
                        <span class="nav-link text-white">Bienvenido, <strong><%= usuarioLogueado %></strong> (<%= tipoUsuario %>)</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="LogoutServlet">Cerrar Sesión</a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>