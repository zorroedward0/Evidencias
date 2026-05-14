<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">

        <title><fmt:message key='otp.titulo'/></title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

        <style>
            body{
                margin:0;
                height:100vh;
                display:flex;
                align-items:center;
                justify-content:center;
                font-family:'Segoe UI',sans-serif;
                background: linear-gradient(135deg,#eef2f7,#f8fbff);
            }

            .otp-card{
                width:420px;
                border-radius:18px;
                overflow:hidden;
                background:white;
                box-shadow:0 15px 40px rgba(0,0,0,0.12);
                position:relative;
                transition:0.3s;
            }

            .otp-card::before{
                content:"";
                position:absolute;
                top:0;
                left:0;
                width:100%;
                height:5px;
                background:linear-gradient(90deg,#1ABC9C,#3498DB,#8E44AD);
            }

            .otp-card:hover{
                transform:translateY(-5px);
                box-shadow:0 20px 55px rgba(0,0,0,0.18);
            }

            .otp-header{
                background: linear-gradient(135deg,#0E6655,#117864,#1ABC9C);
                padding:25px;
                text-align:center;
                color:white;
            }

            .otp-header i{
                font-size:42px;
                margin-bottom:10px;
                filter: drop-shadow(0 5px 10px rgba(0,0,0,0.2));
            }

            .otp-body{
                padding:25px;
            }

            .otp-input{
                font-size:26px;
                letter-spacing:12px;
                text-align:center;
                border-radius:12px;
                padding:12px;
                border:1px solid #ddd;
                transition:0.2s;
            }

            .otp-input:focus{
                border-color:#1ABC9C;
                box-shadow:0 0 0 3px rgba(26,188,156,0.2);
            }

            .timer{
                font-weight:600;
                color:#0E6655;
            }

            .btn-success{
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                border:none;
                border-radius:10px;
                padding:10px;
                transition:0.3s;
            }

            .btn-success:hover{
                transform:translateY(-2px);
                box-shadow:0 10px 20px rgba(0,0,0,0.15);
            }

            .alert{
                border-radius:10px;
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
        </style>
    </head>

    <body>

        <div class="otp-card">

            <div class="otp-header">
                <i class="fa-solid fa-shield-halved"></i>
                <h4><fmt:message key='otp.titulo'/></h4>
            </div>

            <div class="otp-body">



                <c:choose>
                    <c:when test="${volver}"> 
                        <c:if test="${not empty errorKey}">
                            <div class="alert alert-danger">
                                <fmt:message key="${errorKey}" />
                            </div>
                        </c:if>

                        <a href="${pageContext.request.contextPath}/login"
                           class="btn btn-success w-100">
                            <fmt:message key="otp.volver"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${not empty errorKey}">
                            <div class="alert alert-danger">
                                <fmt:message key="${errorKey}" />
                            </div>
                        </c:if>

                        <p class="text-muted text-center">
                            <fmt:message key='otp.instruccion'>
                                <fmt:param value="${emailMasked}"/>
                            </fmt:message>
                        </p>

                        <div class="text-center mb-2">
                            ⏱️ <span id="countdown" class="timer">05:00</span>
                        </div>
                        <form action="${pageContext.request.contextPath}/otp" method="post">
                            <input type="text" name="otpCodigo"
                                   class="form-control otp-input mb-3"
                                   maxlength="6"
                                   pattern="[0-9]{6}"
                                   placeholder="000000"
                                   required>

                            <button type="submit" class="btn btn-success w-100">
                                <i class="fa-solid fa-check me-2"></i>
                                <fmt:message key="otp.verificar"/>
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>


            </div>

        </div>
        <div id="loader-overlay">
            <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
        </div>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            const form = document.querySelector("form");
            const loader = document.getElementById("loader-overlay");

            form.addEventListener("submit", function () {
                loader.style.display = "flex";
            });
        </script>
        <script>
            const msgExpired = "<fmt:message key='otp.expired'/>";
            const msgInvalid = "<fmt:message key='otp.invalid.redirect'/>";
            const msgConfirm = "<fmt:message key='otp.confirm'/>";

            function redirectLogout(message) {
                Swal.fire({
                    icon: "warning",
                    text: message,
                    confirmButtonText: msgConfirm,
                    allowOutsideClick: false,
                    allowEscapeKey: false
                }).then(() => {
                    window.location.href = "<%=request.getContextPath()%>/logout";
                });
            }

            window.addEventListener("load", function () {
                const navEntries = performance.getEntriesByType("navigation");

                if (navEntries.length > 0 && navEntries[0].type === "reload") {
                    redirectLogout(msgInvalid);
                }
            });

            window.addEventListener("pageshow", function (event) {
                if (event.persisted) {
                    window.location.href = "<%=request.getContextPath()%>/logout";
                }
            });

            let timeLeft = 300;
            let countdown = document.getElementById("countdown");

            let timer = setInterval(() => {
                let minutes = Math.floor(timeLeft / 60);
                let seconds = timeLeft % 60;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                countdown.textContent = minutes + ":" + seconds;
                timeLeft--;

                if (timeLeft < 0) {
                    clearInterval(timer);
                    countdown.textContent = msgExpired;
                    countdown.style.color = "#e74c3c";

                    redirectLogout(msgExpired);
                }
            }, 1000);
        </script>
    </body>
</html>