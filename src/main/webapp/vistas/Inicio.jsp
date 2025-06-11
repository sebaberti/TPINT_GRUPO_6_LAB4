<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Página de Inicio - Banco SIX</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInicio.css">
 
</head>
<body>

<jsp:include page="/Header.jsp" />

<main>
    <section class="hero text-center">
        <div class="container">
            <h1>Bienvenido a SIX</h1>
            <p class="text">Tu banco de confianza para operaciones seguras, préstamos personales y más.</p>
            <% if (session.getAttribute("usuarioNombre") == null) { %>
                <a href="Login.jsp" class="btn btn-light btn-lg mt-3">Accedé ahora</a>
            <% } %>
        </div>
    </section>
</main>

<jsp:include page="/Footer.jsp" />

</body>
</html>