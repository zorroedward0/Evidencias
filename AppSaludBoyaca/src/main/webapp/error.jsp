<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<c:set var="errorCode" value="${pageContext.errorData.statusCode}" />
<c:set var="errorMessage" value="${pageContext.errorData.throwable.message}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><fmt:message key="error.titulo"/></title>

        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

        <style>
            :root{
                --primary:#0E6655;
                --secondary:#117864;
                --accent:#1ABC9C;
            }
            body{
                height:100vh;
                margin:0;
                display:flex;
                justify-content:center;
                align-items:center;
                background: linear-gradient(135deg,#eef2f7,#f8fbff);
                font-family:'Segoe UI',sans-serif;
            }
            .error-container{
                text-align:center;
                background:white;
                padding:50px 40px;
                border-radius:20px;
                box-shadow:0 15px 40px rgba(0,0,0,0.12);
                max-width:500px;
                width:100%;
                animation:fadeIn 0.6s ease-in-out;
            }
            .logo{
                font-size:22px;
                font-weight:600;
                color:var(--primary);
                margin-bottom:15px;
            }
            .error-icon{
                font-size:70px;
                color:#E74C3C;
                margin-bottom:20px;
            }
            h1{
                font-size:60px;
                font-weight:700;
                color:#2C3E50;
            }
            p{
                color:#7F8C8D;
                margin-top:10px;
            }
            .btn-main{
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                color:white;
                border:none;
                border-radius:10px;
                padding:10px 20px;
            }
            @keyframes fadeIn{
                from{
                    opacity:0;
                    transform:translateY(20px);
                }
                to{
                    opacity:1;
                    transform:translateY(0);
                }
            }
        </style>
    </head>

    <body>

        <div class="error-container">

            <div class="logo">
                <i class="fa-solid fa-hospital"></i>
                <fmt:message key="app.nombre"/>
            </div>

            <div class="error-icon">
                <i class="fa-solid fa-circle-exclamation"></i>
            </div>

            <h1>
                <c:choose>
                    <c:when test="${not empty errorCode}">
                        ${errorCode}
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="error.codigo.default"/>
                    </c:otherwise>
                </c:choose>
            </h1>

            <h4 class="mt-2">
                <c:choose>
                    <c:when test="${errorCode == 404}">
                        <fmt:message key="error.404"/>
                    </c:when>
                    <c:when test="${errorCode == 500}">
                        <fmt:message key="error.500"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="error.general"/>
                    </c:otherwise>
                </c:choose>
            </h4>

            <p>
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    ${errorMessage}
                </c:when>
                <c:otherwise>
                    <fmt:message key="error.descripcion"/>
                </c:otherwise>
            </c:choose>
        </p>

        <div class="mt-4 d-flex justify-content-center gap-3 flex-wrap">

            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-main">
                <i class="fa-solid fa-house"></i>
                <fmt:message key="btn.inicio"/>
            </a>

            <button onclick="history.back()" class="btn btn-outline-secondary">
                <i class="fa-solid fa-arrow-left"></i>
                <fmt:message key="btn.volver"/>
            </button>

        </div>

    </div>

</body>
</html>