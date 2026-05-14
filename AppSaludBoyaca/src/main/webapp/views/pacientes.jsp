<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/logo.png">

        <title><fmt:message key="pacientes.titulo"/></title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">

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
            .table tbody tr:hover{
                background:#f2f8ff;
            }
            .btn-main{
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                color:white;
                border:none;
                border-radius:10px;
            }
            .modal-content{
                border-radius:18px;
            }
            .modal-header{
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                color:white;
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
                <h4><i class="fa-solid fa-hospital"></i> <span><fmt:message key="app.nombre"/></span></h4>
                <hr>

                <p><i class="fa-solid fa-user"></i> <span>${sessionScope.usuarioNombre}</span></p>
                <p><i class="fa-solid fa-id-badge"></i> <span>${rol}</span></p>

                <hr>

                <a href="${pageContext.request.contextPath}/dashboard">
                    <i class="fa-solid fa-gauge"></i>
                    <span><fmt:message key="menu.dashboard"/></span>
                </a>

                <a href="${pageContext.request.contextPath}/pacientes" class="active">
                    <i class="fa-solid fa-user-injured"></i>
                    <span><fmt:message key="menu.pacientes"/></span>
                </a>

                <a href="${pageContext.request.contextPath}/citas">
                    <i class="fa-solid fa-calendar-check"></i>
                    <span><fmt:message key="menu.citas"/></span>
                </a>

                <c:if test="${sessionScope.usuarioRol != 'ENFERMERO'}">
                    <a href="${pageContext.request.contextPath}/horarios">
                        <i class="fa-solid fa-clock"></i>
                        <span><fmt:message key="nav.horarios"/></span>
                    </a>
                </c:if>

                <a href="${pageContext.request.contextPath}/logout" class="mt-4">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span><fmt:message key="menu.salir"/></span>
                </a>

            </div>

            <div class="content">

                <div class="toggle-btn mb-3" onclick="toggleSidebar()">
                    <i class="fa-solid fa-bars"></i>
                </div>

                <div class="d-flex justify-content-between mb-3">
                    <h3>
                        <i class="fa-solid fa-users"></i>
                        <fmt:message key="pacientes.titulo"/>
                    </h3>

                    <c:if test="${rol ne 'ENFERMERO' and rol ne 'MEDICO'}">
                        <button class="btn btn-main" data-bs-toggle="modal" data-bs-target="#modalCrear">
                            <i class="fa-solid fa-plus"></i>
                            <fmt:message key="pacientes.nuevo"/>
                        </button>
                    </c:if>

                </div>

                <div class="card table-card p-3">

                    <table id="tablaPacientes" class="table table-hover">

                        <thead>
                            <tr>
                                <th>ID</th>
                                <th><fmt:message key="pacientes.nombre"/></th>
                                <th><fmt:message key="pacientes.documento"/></th>
                                <th><fmt:message key="pacientes.telefono"/></th>
                                <th><fmt:message key="pacientes.email"/></th>
                                <th><fmt:message key="pacientes.eps"/></th>
                                    <c:if test="${rol ne 'ENFERMERO' and rol ne 'MEDICO'}">
                                    <th><fmt:message key="pacientes.acciones"/></th>
                                    </c:if>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="p" items="${listaPacientes}">
                                <tr>
                                    <td>${p.id}</td>
                                    <td>${p.nombres} ${p.apellidos}</td>
                                    <td>${p.documento}</td>
                                    <td>${p.telefono}</td>
                                    <td>${p.email}</td>
                                    <td>${p.eps}</td>

                                    <c:if test="${rol ne 'ENFERMERO' and rol ne 'MEDICO'}">
                                        <td>

                                            <button class="btn btn-warning btn-sm"
                                                    data-id="${p.id}"
                                                    data-nombres="${p.nombres}"
                                                    data-apellidos="${p.apellidos}"
                                                    data-documento="${p.documento}"
                                                    data-fecha="${p.fechaNacimiento}"
                                                    data-telefono="${p.telefono}"
                                                    data-email="${p.email}"
                                                    data-eps="${p.eps}"
                                                    data-vereda="${p.veredaBarrio}"
                                                    onclick="abrirEditar(this)">
                                                <i class="fa-solid fa-pen"></i>
                                            </button>

                                            <button class="btn btn-danger btn-sm"
                                                    data-id="${p.id}"
                                                    onclick="abrirEliminar(this)">
                                                <i class="fa-solid fa-trash"></i>
                                            </button>

                                        </td>
                                    </c:if>

                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>

                </div>

            </div>

        </div>

        <c:if test="${rol ne 'ENFERMERO' and rol ne 'MEDICO'}">

            <div class="modal fade" id="modalCrear">
                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5><fmt:message key="pacientes.crear"/></h5>
                            <button class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <form method="post" action="${pageContext.request.contextPath}/pacientes">
                            <div class="modal-body">

                                <input type="hidden" name="accion" value="crear">

                                <h6><fmt:message key="pacientes.nombres"/></h6>
                                <input name="nombres" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.apellidos"/></h6>
                                <input name="apellidos" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.documento"/></h6>
                                <input name="documento" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.fecha"/></h6>
                                <input type="date" name="fechaNacimiento" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.telefono"/></h6>
                                <input name="telefono" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.email"/></h6>
                                <input name="email" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.eps"/></h6>
                                <input name="eps" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.vereda"/></h6>
                                <input name="veredaBarrio" class="form-control mb-2">

                            </div>

                            <div class="modal-footer">
                                <button class="btn btn-main w-100"><fmt:message key="btn.guardar"/></button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>

            <div class="modal fade" id="modalEditar">
                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5><fmt:message key="pacientes.editar"/></h5>
                            <button class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <form method="post" action="${pageContext.request.contextPath}/pacientes">
                            <div class="modal-body">

                                <input type="hidden" name="accion" value="editar">
                                <input type="hidden" name="id" id="editId">

                                <h6><fmt:message key="pacientes.nombres"/></h6>
                                <input name="nombres" id="editNombres" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.apellidos"/></h6>
                                <input name="apellidos" id="editApellidos" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.documento"/></h6>
                                <input name="documento" id="editDocumento" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.fecha"/></h6>
                                <input type="date" name="fechaNacimiento" id="editFecha" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.telefono"/></h6>
                                <input name="telefono" id="editTelefono" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.email"/></h6>
                                <input name="email" id="editEmail" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.eps"/></h6>
                                <input name="eps" id="editEps" class="form-control mb-2">

                                <h6><fmt:message key="pacientes.vereda"/></h6>
                                <input name="veredaBarrio" id="editVereda" class="form-control mb-2">

                            </div>

                            <div class="modal-footer">
                                <button class="btn btn-main w-100"><fmt:message key="btn.actualizar"/></button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>

            <div class="modal fade" id="modalEliminar">
                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5><fmt:message key="pacientes.eliminar"/></h5>
                            <button class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <form method="post" action="${pageContext.request.contextPath}/pacientes">
                            <div class="modal-body text-center">

                                <input type="hidden" name="accion" value="eliminar">
                                <input type="hidden" name="idEliminar" id="deleteId">

                                <h5><fmt:message key="pacientes.confirmarEliminar"/></h5>

                            </div>

                            <div class="modal-footer">
                                <button class="btn btn-danger w-100"><fmt:message key="btn.eliminar"/></button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
            <div id="loader-overlay">
                <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
            </div>
        </c:if>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
                                                        function toggleSidebar() {
                                                        document.getElementById('sidebar').classList.toggle('collapsed');
                                                        }
                                                        function abrirEditar(btn) {
                                                        document.getElementById("editId").value = btn.dataset.id;
                                                        document.getElementById("editNombres").value = btn.dataset.nombres;
                                                        document.getElementById("editApellidos").value = btn.dataset.apellidos;
                                                        document.getElementById("editDocumento").value = btn.dataset.documento;
                                                        document.getElementById("editFecha").value = btn.dataset.fecha.split(" ")[0];
                                                        document.getElementById("editTelefono").value = btn.dataset.telefono;
                                                        document.getElementById("editEmail").value = btn.dataset.email;
                                                        document.getElementById("editEps").value = btn.dataset.eps;
                                                        document.getElementById("editVereda").value = btn.dataset.vereda;
                                                        new bootstrap.Modal(document.getElementById("modalEditar")).show();
                                                        }
                                                        function abrirEliminar(btn) {
                                                        document.getElementById("deleteId").value = btn.dataset.id;
                                                        new bootstrap.Modal(document.getElementById("modalEliminar")).show();
                                                        }
                                                        $(document).ready(function () {
                                                        $('#tablaPacientes').DataTable({
                                                        pageLength: 10,
                                                                dom: '<"d-flex justify-content-between mb-2"f>rt<"d-flex justify-content-between mt-2"ip>'
                                                        });
                                                        });
        </script>

        <% Boolean hecho = (Boolean) request.getAttribute("hecho"); %>

        <% if (hecho != null) {%>
        <script>
            Swal.fire({
            icon: '<%= hecho ? "success" : "error"%>',
            <%if (hecho) {%>
            title: '<fmt:message key="swal.tituloOk"/>',
                    text: '<fmt:message key="swal.textoOk"/>'
            <%} else if (!hecho) {%>
            title: '<fmt:message key="swal.tituloEr"/>',
                    text: '<fmt:message key="swal.textoEr"/>'
            <%}%>
            }).then(() => {
            window.location.href = '${pageContext.request.contextPath}/pacientes';
            });
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