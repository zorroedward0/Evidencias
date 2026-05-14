<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title><fmt:message key="nav.dashboard"/></title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

        <style>
            :root{
                --primary:#0E6655;
                --secondary:#117864;
                --accent:#1ABC9C;
                --blue:#3498DB;
                --purple:#8E44AD;
                --orange:#F39C12;
            }

            body{
                background: linear-gradient(135deg,#eef2f7,#f8fbff);
                font-family: 'Segoe UI', sans-serif;
                overflow-x:hidden;
            }

            .layout{
                display:flex;
            }

            .sidebar{
                width:240px;
                min-width:240px;
                height:100vh;
                background: linear-gradient(180deg,#0E6655,#117864,#1ABC9C);
                color:white;
                padding:20px;
                transition:0.3s;
            }

            .sidebar.collapsed{
                width:80px;
                min-width:80px;
            }

            .sidebar a{
                display:flex;
                align-items:center;
                gap:12px;
                padding:10px;
                border-radius:10px;
                color:white;
                text-decoration:none;
                transition:0.25s;
            }

            .sidebar a:hover{
                background: rgba(255,255,255,0.15);
                transform:translateX(5px);
            }

            .sidebar a.active{
                background: rgba(255,255,255,0.25);
                box-shadow:0 0 10px rgba(255,255,255,0.2);
            }

            .sidebar i{
                background: rgba(255,255,255,0.2);
                padding:10px;
                border-radius:50%;
                min-width:40px;
                text-align:center;
            }

            .sidebar.collapsed span,
            .sidebar.collapsed h4,
            .sidebar.collapsed p{
                display:none;
            }

            .content{
                flex:1;
                padding:20px;
                transition:0.3s;
            }

            .toggle-btn{
                cursor:pointer;
                font-size:20px;
            }

            .card-metric{
                border:none;
                border-radius:18px;
                background:white;
                box-shadow:0 10px 25px rgba(0,0,0,0.08);
                transition:0.3s;
                position:relative;
                overflow:hidden;
            }

            .card-metric::before{
                content:"";
                position:absolute;
                width:100%;
                height:4px;
                top:0;
                left:0;
                background:linear-gradient(90deg,#1ABC9C,#3498DB,#8E44AD);
            }

            .card-metric:hover{
                transform:translateY(-8px) scale(1.02);
                box-shadow:0 15px 35px rgba(0,0,0,0.15);
            }

            .icon-box{
                font-size:26px;
                padding:15px;
                border-radius:14px;
                color:white;
                background: linear-gradient(135deg,#1ABC9C,#16A085);
                box-shadow:0 5px 15px rgba(0,0,0,0.15);
            }

            .bg2{
                background: linear-gradient(135deg,#3498DB,#2E86C1);
            }
            .bg3{
                background: linear-gradient(135deg,#F39C12,#D68910);
            }
            .bg4{
                background: linear-gradient(135deg,#8E44AD,#6C3483);
            }

            .table-card{
                border-radius:18px;
                overflow:hidden;
                box-shadow:0 10px 25px rgba(0,0,0,0.08);
                background:white;
            }

            .table thead{
                position:sticky;
                top:0;
                background:white;
                z-index:1;
            }

            .table tbody tr{
                transition:0.25s;
            }

            .table tbody tr:hover{
                background:#f2f8ff;
                transform:scale(1.01);
            }

            .badge{
                padding:6px 10px;
                border-radius:10px;
                font-weight:500;
            }

            .badge-success{
                background:linear-gradient(135deg,#2ECC71,#27AE60);
            }

            .badge-warning{
                background:linear-gradient(135deg,#F1C40F,#D4AC0D);
            }

            .badge-danger{
                background:linear-gradient(135deg,#E74C3C,#C0392B);
            }

            .badge-primary{
                background:linear-gradient(135deg,#3498DB,#2E86C1);
            }

            .badge-secondary{
                background:linear-gradient(135deg,#95A5A6,#7F8C8D);
            }
            .fade-in{
                animation:fadeIn 0.6s ease-in-out;
            }

            @keyframes fadeIn{
                from{
                    opacity:0;
                    transform:translateY(10px);
                }
                to{
                    opacity:1;
                    transform:translateY(0);
                }
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

        <div class="layout">

            <div class="sidebar" id="sidebar">

                <h4><i class="fa-solid fa-hospital"></i> <span>SaludBoyacá</span></h4>
                <hr>

                <p><i class="fa-solid fa-user"></i> <span>${sessionScope.usuarioNombre}</span></p>
                <p><i class="fa-solid fa-id-badge"></i> <span>${rol}</span></p>

                <hr>

                <a href="${pageContext.request.contextPath}/dashboard" class="active">
                    <i class="fa-solid fa-gauge"></i>
                    <span><fmt:message key="nav.dashboard"/></span>
                </a>

                <a href="${pageContext.request.contextPath}/pacientes">
                    <i class="fa-solid fa-user-injured"></i>
                    <span><fmt:message key="nav.pacientes"/></span>
                </a>

                <a href="${pageContext.request.contextPath}/citas">
                    <i class="fa-solid fa-calendar-check"></i>
                    <span><fmt:message key="nav.citas"/></span>
                </a>
                
                <c:if test="${sessionScope.usuarioRol != 'ENFERMERO'}">
                    <a href="${pageContext.request.contextPath}/horarios">
                        <i class="fa-solid fa-clock"></i>
                        <span><fmt:message key="nav.horarios"/></span>
                    </a>
                </c:if>


                <a href="${pageContext.request.contextPath}/logout" class="mt-4">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span><fmt:message key="nav.salir"/></span>
                </a>

            </div>

            <div class="content">

                <div class="toggle-btn mb-3" onclick="toggleSidebar()">
                    <i class="fa-solid fa-bars"></i>
                </div>

                <h3 class="mb-3">
                    <i class="fa-solid fa-chart-line"></i>
                    <fmt:message key="dashboard.bienvenida"/>
                    ${sessionScope.usuarioNombre}
                </h3>

                <p class="text-muted">${fechaActual}</p>

                <div class="row g-3 fade-in">

                    <div class="col-md-3">
                        <div class="card card-metric p-3">
                            <div class="d-flex align-items-center">
                                <div class="icon-box me-3">
                                    <i class="fa-solid fa-calendar-day"></i>
                                </div>
                                <div>
                                    <h5>${citasHoy}</h5>
                                    <small><fmt:message key="dashboard.citas.hoy"/></small>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card card-metric p-3">
                            <div class="d-flex align-items-center">
                                <div class="icon-box bg2 me-3">
                                    <i class="fa-solid fa-clock"></i>
                                </div>
                                <div>
                                    <h5>${citasPendientes}</h5>
                                    <small><fmt:message key="dashboard.citas.pendientes"/></small>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card card-metric p-3">
                            <div class="d-flex align-items-center">
                                <div class="icon-box bg3 me-3">
                                    <i class="fa-solid fa-calendar"></i>
                                </div>
                                <div>
                                    <h5>${citasMes}</h5>
                                    <small><fmt:message key="dashboard.citas.mes"/></small>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="card card-metric p-3">
                            <div class="d-flex align-items-center">
                                <div class="icon-box bg4 me-3">
                                    <i class="fa-solid fa-users"></i>
                                </div>
                                <div>
                                    <h5>${totalPacientes}</h5>
                                    <small><fmt:message key="dashboard.pacientes"/></small>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="mt-4 fade-in">

                    <div class="card table-card">

                        <div class="card-header bg-white">
                            <h5 class="mb-0">
                                <i class="fa-solid fa-calendar-check"></i>
                                <fmt:message key="dashboard.proximas.citas"/>
                            </h5>
                        </div>

                        <div class="card-body p-0">

                            <table class="table table-hover mb-0">

                                <thead class="table-light">
                                    <tr>
                                        <th><fmt:message key="tabla.paciente"/></th>
                                        <th><fmt:message key="tabla.fecha"/></th>
                                        <th><fmt:message key="tabla.hora"/></th>
                                        <th><fmt:message key="tabla.estado"/></th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="c" items="${proximasCitas}">
                                        <tr>
                                            <td>${c.nombrePaciente}</td>
                                            <td>${c.fechaCita}</td>
                                            <td>${c.horaCita}</td>
                                            <td>
                                                <span class="badge 
                                                      ${c.estado == 'PROGRAMADA' ? 'badge-warning' : 
                                                        c.estado == 'CONFIRMADA' ? 'badge-primary' : 
                                                        c.estado == 'ATENDIDA' ? 'badge-success' : 
                                                        c.estado == 'CANCELADA' ? 'badge-danger' : 'badge-secondary'}">
                                                          ${c.estado}
                                                      </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>

                                </table>

                            </div>

                        </div>

                    </div>

                </div>

            </div>
            <div id="loader-overlay">
                <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
            </div>
            <script>
                function toggleSidebar() {
                    document.getElementById('sidebar').classList.toggle('collapsed');
                }
            </script>
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