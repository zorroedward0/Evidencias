<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><fmt:message key="horario.titulo"/></title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/jquery.dataTables.min.css">
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
                font-family:'Segoe UI',sans-serif;
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
            }
            .toggle-btn{
                cursor:pointer;
                font-size:20px;
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

            .dataTables_wrapper {
                background: white;
                padding: 15px;
                border-radius: 14px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.04);
            }

            .dataTables_filter input {
                border: 1px solid #e6eef7;
                border-radius: 10px;
                padding: 6px 12px;
                outline: none;
                background: #fbfdff;
                color: #222;
            }

            table.dataTable {
                border-collapse: collapse !important;
                width: 100%;
            }

            table.dataTable thead th {
                background: #f2f7ff !important;
                color: #222;
                border-bottom: 1px solid #dbe9ff !important;
                font-weight: 600;
                padding: 12px;
            }

            table.dataTable tbody td {
                border-bottom: 1px solid #e9edf2;
                color: #222;
                padding: 10px;
            }

            table.dataTable tbody tr:hover {
                background: #f7fbff !important;
            }

            .dataTables_wrapper label,
            .dataTables_info {
                color: #222;
                font-size: 13px;
            }

            .dataTables_paginate {
                margin-top: 10px;
            }

            .dataTables_paginate .paginate_button {
                border: none !important;
                background: transparent !important;
                color: #222 !important;
                padding: 5px 10px;
                border-radius: 8px;
                margin: 2px;
            }

            .dataTables_paginate .paginate_button:hover {
                background: #eef5ff !important;
                color: #1a73e8 !important;
            }

            .dataTables_paginate .paginate_button.current {
                background: #eaf2ff !important;
                color: #1a73e8 !important;
                font-weight: 600;
            }
            .card-header h5 {
                font-weight: 600;
                color: #2c3e50;
            }

            table.dataTable tbody tr {
                transition: all 0.2s ease;
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

                <a href="${pageContext.request.contextPath}/dashboard">
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

                <div class="d-flex justify-content-between mb-3">
                    <h3>
                        <i class="fa-solid fa-clock"></i>
                        <fmt:message key="horario.titulo"/>
                    </h3>
                </div>

                <div class="card table-card fade-in">

                    <div class="card-header bg-white">
                        <h5 class="mb-0">
                            <i class="fa-solid fa-list"></i>
                            <fmt:message key="horario.titulo"/>
                        </h5>
                    </div>

                    <div class="card-body p-0">

                        <table id="tablaHorarios" class="table table-hover mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th><fmt:message key="horario.medico"/></th>
                                    <th><fmt:message key="horario.dia"/></th>
                                    <th><fmt:message key="horario.horaInicio"/></th>
                                    <th><fmt:message key="horario.horaFin"/></th>
                                    <th><fmt:message key="horario.maxCitas"/></th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="h" items="${listaHorarios}">
                                    <tr>
                                        <td>${h.id}</td>
                                        <td>${h.nombreMedico}</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${h.diaSemana == 1}"><fmt:message key="dia.lunes"/></c:when>
                                                <c:when test="${h.diaSemana == 2}"><fmt:message key="dia.martes"/></c:when>
                                                <c:when test="${h.diaSemana == 3}"><fmt:message key="dia.miercoles"/></c:when>
                                                <c:when test="${h.diaSemana == 4}"><fmt:message key="dia.jueves"/></c:when>
                                                <c:when test="${h.diaSemana == 5}"><fmt:message key="dia.viernes"/></c:when>
                                                <c:when test="${h.diaSemana == 6}"><fmt:message key="dia.sabado"/></c:when>
                                                <c:when test="${h.diaSemana == 7}"><fmt:message key="dia.domingo"/></c:when>
                                            </c:choose>
                                        </td>

                                        <td>${h.horaInicio}</td>
                                        <td>${h.horaFin}</td>
                                        <td>${h.maxCitas}</td>

                                    </tr>
                                </c:forEach>
                            </tbody>

                        </table>

                    </div>
                </div>

            </div>
        </div>

        <div id="loader-overlay">
            <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
        </div>

        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <script>
                    function toggleSidebar() {
                        document.getElementById('sidebar').classList.toggle('collapsed');
                    }

                    $('#tablaHorarios').DataTable({
                        pageLength: 10,
                        lengthChange: false,
                        info: false,
                        ordering: false,
                        responsive: true,
                        autoWidth: false,
                        language: {
                            search: "Buscar:",
                            paginate: {
                                first: "Primero",
                                last: "Último",
                                next: "→",
                                previous: "←"
                            },
                            zeroRecords: "No se encontraron resultados"
                        }
                    });
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