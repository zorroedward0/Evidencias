<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><fmt:message key="consulta.titulo"/></title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

        <style>
            :root{
                --primary:#0E6655;
                --secondary:#117864;
                --accent:#1ABC9C;
                --blue:#3498DB;
            }

            body{
                background: linear-gradient(135deg,#eef2f7,#f8fbff);
                font-family:'Segoe UI',sans-serif;
            }

            .header{
                display:flex;
                justify-content:space-between;
                align-items:center;
                padding:15px 25px;
            }

            .btn-login{
                background: linear-gradient(135deg,#3498DB,#2E86C1);
                color:white;
                border:none;
                border-radius:10px;
            }

            .card-main{
                border:none;
                border-radius:20px;
                background:white;
                box-shadow:0 15px 40px rgba(0,0,0,0.1);
            }

            .card-main::before{
                content:"";
                display:block;
                height:5px;
                background:linear-gradient(90deg,#1ABC9C,#3498DB,#8E44AD);
                border-radius:20px 20px 0 0;
            }

            .captcha-img{
                border-radius:10px;
                border:1px solid #ddd;
            }

            .table-card{
                border-radius:18px;
                overflow:hidden;
                box-shadow:0 10px 25px rgba(0,0,0,0.08);
                background:white;
            }

            .table tbody tr:hover{
                background:#f2f8ff;
                transform:scale(1.01);
            }

            .badge-success{
                background:linear-gradient(135deg,#2ECC71,#27AE60);
            }
            .badge-warning{
                background:linear-gradient(135deg,#F1C40F,#D4AC0D);
            }
            .badge-primary{
                background:linear-gradient(135deg,#3498DB,#2E86C1);
            }

            .btn-main{
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                color:white;
                border:none;
                border-radius:10px;
            }
            #loader-overlay{
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(14, 102, 85, 0.85);
                display: flex; 
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

        <div class="header">
            <h4><i class="fa-solid fa-hospital"></i> SaludBoyacá</h4>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-login">
                <i class="fa-solid fa-arrow-left"></i> <fmt:message key="consulta.volver"/>
            </a>
        </div>

        <div class="container">

            <div class="card card-main p-4 mb-4">

                <h4 class="mb-3">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <fmt:message key="consulta.buscar"/>
                </h4>

                <form method="post" action="consulta-cita">

                    <div class="row g-3 align-items-center">

                        <div class="col-md-5">
                            <label><fmt:message key="consulta.documento"/></label>
                            <input type="text" name="documento" class="form-control" required>
                        </div>

                        <div class="col-md-4 text-center">
                            <label><fmt:message key="consulta.captcha"/></label><br>
                            <img src="${captchaImagen}" class="captcha-img mb-2"><br>
                            <input type="text" name="captchaIngresado" class="form-control" placeholder="Captcha" required>
                        </div>

                        <div class="col-md-3 d-flex align-items-end">
                            <button class="btn btn-main w-100">
                                <i class="fa-solid fa-search"></i>
                                <fmt:message key="consulta.buscarBtn"/>
                            </button>
                        </div>

                    </div>

                </form>
            </div>

            <c:if test="${not empty listaCitasConsulta}">
                <div class="card table-card">
                    <div class="card-header bg-white">
                        <h5>
                            <i class="fa-solid fa-calendar-check"></i>
                            <fmt:message key="consulta.resultados"/>
                        </h5>
                    </div>

                    <div class="card-body p-0">
                        <table class="table table-hover mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th><fmt:message key="consulta.medico"/></th>
                                    <th><fmt:message key="consulta.especialidad"/></th>
                                    <th><fmt:message key="consulta.fecha"/></th>
                                    <th><fmt:message key="consulta.hora"/></th>
                                    <th><fmt:message key="consulta.estado"/></th>
                                    <th><fmt:message key="consulta.acciones"/></th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="c" items="${listaCitasConsulta}">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td>${c.nombreMedico}</td>
                                        <td>${c.nombreEspecialidad}</td>
                                        <td>${c.fechaCita}</td>
                                        <td>${c.horaCita}</td>

                                        <td>
                                            <span class="badge 
                                                  ${c.estado == 'PROGRAMADA' ? 'badge-warning' : 
                                                    c.estado == 'CONFIRMADA' ? 'badge-primary' : 'badge-success'}">
                                                      ${c.estado}
                                                  </span>
                                            </td>

                                            <td>
                                                <form method="post" action="consulta-cita">
                                                    <input type="hidden" name="accion" value="pdfComprobante">
                                                    <input type="hidden" name="id" value="${c.id}">
                                                    <button class="btn btn-danger btn-sm">
                                                        <i class="fa-solid fa-file-pdf"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                            </table>
                        </div>
                    </div>
                </c:if>

            </div>
            <div id="loader-overlay">
                <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

            <% Boolean hecho = (Boolean) request.getAttribute("hecho"); %>
            <% if (hecho != null) {%>
            <script>
                Swal.fire({
                icon: '<%= hecho ? "success" : "error"%>',
                <%if (hecho) {%>
                title: '<fmt:message key="swal.tituloOk"/>',
                        text: '<fmt:message key="consulta.ok"/>'
                });
                <%} else {%>
                title: '<fmt:message key="swal.tituloEr"/>',
                        text: '<fmt:message key="consulta.error"/>'
                }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/consulta-cita';
                });
                <%}%>

            </script>
            <% }%>
            <script>
                window.addEventListener("load", function () {
                const loader = document.getElementById("loader-overlay");
                loader.style.opacity = "0";
                loader.style.transition = "opacity 0.5s ease";
                setTimeout(() => {
                loader.style.display = "none";
                }, 500);
                });
            </script>
        </body>
    </html>