<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="login.titulo"/></title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">

        <style>
            body{
                margin:0;
                font-family:'Segoe UI',sans-serif;
                height:100vh;
                background: linear-gradient(135deg,#eef2f7,#f8fbff);
            }

            .wrapper{
                display:flex;
                height:100vh;
            }

            .left-panel{
                flex:1;
                background: linear-gradient(180deg,#0E6655,#117864,#1ABC9C);
                display:flex;
                align-items:center;
                justify-content:center;
                padding:40px;
            }

            .image-box{
                width:87%;
                height:87%;
                border-radius:20px;
                background: rgba(255,255,255,0.08);
                border:1px solid rgba(255,255,255,0.2);
                backdrop-filter: blur(10px);
                display:flex;
                align-items:center;
                justify-content:center;
                overflow:hidden;
                box-shadow:0 20px 50px rgba(0,0,0,0.25);
            }

            .image-box img{
                width:100%;
                height:100%;
                object-fit:cover;
            }

            .right-panel{
                flex:1;
                display:flex;
                align-items:center;
                justify-content:center;
                padding:40px;
            }

            .login-card{
                width:380px;
                background:white;
                padding:35px;
                border-radius:18px;
                box-shadow:0 15px 40px rgba(0,0,0,0.12);
                position:relative;
            }

            .login-card::before{
                content:"";
                position:absolute;
                top:0;
                left:0;
                width:100%;
                height:5px;
                border-radius:18px 18px 0 0;
                background:linear-gradient(90deg,#1ABC9C,#3498DB,#8E44AD);
            }

            h2{
                color:#0E6655;
                font-weight:600;
                margin-bottom:5px;
            }

            .subtitle{
                font-size:13px;
                color:#777;
                margin-bottom:20px;
            }

            input{
                width:100%;
                padding:12px;
                margin:8px 0;
                border-radius:10px;
                border:1px solid #ddd;
                outline:none;
                transition:0.2s;
            }

            input:focus{
                border-color:#1ABC9C;
                box-shadow:0 0 0 3px rgba(26,188,156,0.2);
            }

            button{
                width:100%;
                padding:12px;
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                color:white;
                border:none;
                border-radius:10px;
                margin-top:10px;
                font-weight:500;
                transition:0.3s;
            }

            button:hover{
                transform:translateY(-2px);
                box-shadow:0 10px 20px rgba(0,0,0,0.15);
            }

            .error{
                color:#e74c3c;
                font-size:14px;
                margin-bottom:10px;
            }

            .lang-dropdown{
                position:absolute;
                top:20px;
                right:20px;
            }

            .footer{
                margin-top:15px;
                font-size:12px;
                color:#777;
                text-align:center;
            }

            @media(max-width:900px){
                .wrapper{
                    flex-direction:column;
                }

                .left-panel{
                    display:none;
                }

                .right-panel{
                    flex:1;
                }
            }
            #loader-overlay{
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(14, 102, 85, 0.85);
                display: none;
                align-items: center;
                justify-content: center;
                z-index: 9999;
            }

            .loader-logo{
                width: 80px;
                height: 80px;
                animation: spin 1.2s linear infinite;
            }

            @keyframes spin{
                from {
                    transform: rotate(0deg);
                }
                to {
                    transform: rotate(360deg);
                }
            }
            @media(max-width:600px){

                .right-panel{
                    padding:20px;
                }

                .login-card{
                    width:100%;
                    padding:25px 20px;
                    border-radius:14px;
                    box-shadow:none;
                }

                h2{
                    font-size:22px;
                    text-align:center;
                }

                .subtitle{
                    text-align:center;
                }
            }
            @media(max-width:600px){

                input{
                    padding:14px;
                    font-size:16px;
                }

                button{
                    padding:14px;
                    font-size:16px;
                }
            }
            @media(max-width:600px){

                .lang-dropdown{
                    top:10px;
                    right:10px;
                }

                .lang-dropdown button{
                    padding:6px 10px;
                    font-size:14px;
                }
            }
            body, .wrapper{
                min-height:100vh;
            }
            @media(max-width:600px){

                .right-panel{
                    align-items:flex-start;
                    padding-top:60px;
                }
            }
            @media(max-width:1100px){

                .wrapper{
                    flex-direction:column;
                }

                .left-panel{
                    display:none;
                }

                .right-panel{
                    flex:1;
                    padding:30px 20px;
                }

                .login-card{
                    width:100%;
                    max-width:420px;
                    margin:auto;
                }
            }
            .right-panel{
                display:flex;
                justify-content:center;
                align-items:center;
            }
        </style>
    </head>

    <body>

        <div class="wrapper">

            <div class="left-panel">
                <div class="image-box">
                    <img src="${pageContext.request.contextPath}/images/logo_login.png" alt="login-image">
                </div>
            </div>

            <div class="right-panel">

                <div class="dropdown lang-dropdown">
                    <button class="btn btn-light dropdown-toggle" data-bs-toggle="dropdown">
                        <i class="bi bi-globe"></i>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="?lang=es">🇪🇸 <fmt:message key="app.lang.es"/></a></li>
                        <li><a class="dropdown-item" href="?lang=en">🇺🇸 <fmt:message key="app.lang.en"/></a></li>
                        <li><a class="dropdown-item" href="?lang=it">🇮🇹 <fmt:message key="app.lang.it"/></a></li>
                    </ul>
                </div>

                <div class="login-card">

                    <h2><fmt:message key="login.titulo"/></h2>
                    <div class="subtitle"><fmt:message key='login.mensaje'/></div>

                    <c:if test="${not empty errorKey}">
                        <p class="error">
                            <fmt:message key="${errorKey}"/>
                        </p>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/login" method="post">

                        <input type="text" name="username"
                               placeholder="<fmt:message key='login.usuario'/>" required />

                        <input type="password" name="password"
                               placeholder="<fmt:message key='login.contrasena'/>" required />

                        <button type="submit">
                            <fmt:message key="login.ingresar"/>
                        </button>

                    </form>

                    <div id="loader-overlay">
                        <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
                    </div>

                    <div class="footer">
                        <fmt:message key="app.footer"/>
                        <a href="${pageContext.request.contextPath}/consulta-cita" class="btn btn-link">
                            <i class="fa-solid fa-calendar-check"></i>
                            <fmt:message key="login.consultaCitas"/>
                        </a>
                    </div>

                </div>

            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            const form = document.querySelector("form");
            const loader = document.getElementById("loader-overlay");

            form.addEventListener("submit", function () {
                loader.style.display = "flex";
            });
        </script>
    </body>
</html>