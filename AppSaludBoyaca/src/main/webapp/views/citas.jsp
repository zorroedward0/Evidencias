<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" />

<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><fmt:message key="cita.titulo"/></title>
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
            .btn-main{
                background: linear-gradient(135deg,#0E6655,#1ABC9C);
                color:white;
                border:none;
                border-radius:10px;
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

                <a href="${pageContext.request.contextPath}/citas" class="active">
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

                <div class="d-flex justify-content-between align-items-center mb-3">

                    <h3 class="mb-0">
                        <i class="fa-solid fa-calendar-check"></i>
                        <fmt:message key="cita.titulo"/>
                    </h3>

                    <div class="d-flex align-items-center gap-2">

                        <c:if test="${rol != 'ENFERMERO' && rol != 'MEDICO'}">

                            <button class="btn btn-main" data-bs-toggle="modal" data-bs-target="#modalCrear">
                                <i class="fa-solid fa-plus"></i>
                                <fmt:message key="cita.nueva"/>
                            </button>

                            <form method="post" action="citas" class="d-flex">
                                <input type="hidden" name="accion" value="pdfPaciente">

                                <div class="input-group" style="max-width: 350px;">
                                    <input type="text"
                                           id="doc-input"
                                           class="form-control"
                                           placeholder="Imprimir PDF por documento">

                                    <input type="hidden" name="documentoPaciente" id="doc-inputJ">
                                    <button class="btn btn-main" type="submit" id="boton-pdf">
                                        <i class="fa-solid fa-magnifying-glass"></i>
                                    </button>
                                </div>
                            </form>

                        </c:if>

                        <c:if test="${rol == 'MEDICO'}">
                            <form method="post" action="citas">
                                <input type="hidden" name="accion" value="pdf">

                                <button class="btn btn-main" type="submit">
                                    <i class="fa-solid fa-file-pdf"></i>
                                    <fmt:message key="cita.pdf"/>
                                </button>
                            </form>
                        </c:if>

                    </div>
                </div>

                <div class="card table-card fade-in">
                    <div class="card-header bg-white">
                        <h5 class="mb-0">
                            <i class="fa-solid fa-list"></i>
                            <fmt:message key="cita.titulo"/>
                        </h5>
                    </div>

                    <div class="card-body p-0">
                        <table class="table table-hover mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th><fmt:message key="cita.paciente"/></th>
                                    <th><fmt:message key="cita.medico"/></th>
                                    <th><fmt:message key="cita.fecha"/></th>
                                    <th><fmt:message key="cita.hora"/></th>
                                    <th><fmt:message key="cita.estado"/></th>
                                        <c:if test="${rol != 'ENFERMERO'}">
                                        <th><fmt:message key="cita.acciones"/></th>
                                        </c:if>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="c" items="${listaCitas}">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td>${c.nombrePaciente}</td>
                                        <td>${c.nombreMedico}</td>
                                        <td>${c.fechaCita}</td>
                                        <td>${c.horaCita}</td>

                                        <td>
                                            <span class="badge 
                                                  ${c.estado == 'PROGRAMADA' ? 'badge-warning' : 
                                                    c.estado == 'CONFIRMADA' ? 'badge-primary' : 
                                                    c.estado == 'ATENDIDA' ? 'badge-success' : 
                                                    c.estado == 'CANCELADA' ? 'badge-danger' : 'badge-secondary'}">

                                                <c:choose>
                                                    <c:when test="${c.estado == 'PROGRAMADA'}"><fmt:message key="cita.estado.programada"/></c:when>
                                                    <c:when test="${c.estado == 'CONFIRMADA'}"><fmt:message key="cita.estado.confirmada"/></c:when>
                                                    <c:when test="${c.estado == 'ATENDIDA'}"><fmt:message key="cita.estado.atendida"/></c:when>
                                                    <c:when test="${c.estado == 'CANCELADA'}"><fmt:message key="cita.estado.cancelada"/></c:when>
                                                    <c:otherwise>${c.estado}</c:otherwise>
                                                </c:choose>

                                            </span>
                                        </td>

                                        <td>
                                            <c:if test="${rol != 'ENFERMERO'}">

                                                <button class="btn btn-warning btn-sm"
                                                        data-id="${c.id}"
                                                        data-paciente="${c.idPaciente}"
                                                        data-medico="${c.idMedico}"
                                                        data-especialidad="${c.idEspecialidad}"
                                                        data-fecha="${c.fechaCita}"
                                                        data-hora="${c.horaCita}"
                                                        data-motivo="${c.motivo}"
                                                        data-estado="${c.estado}"
                                                        data-observaciones="${c.observaciones}"
                                                        onclick="abrirEditar(this)">
                                                    <i class="fa-solid fa-pen"></i>
                                                </button>

                                            </c:if>
                                        </td>

                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>

        <c:if test="${sessionScope.usuarioRol != 'ENFERMERO'}">
            <div class="modal fade" id="modalEditar">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5><fmt:message key="cita.editar"/></h5>
                            <button class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <form method="post" action="citas">
                            <div class="modal-body">
                                <input type="hidden" name="accion" value="editar">
                                <input type="hidden" name="id" id="editId">
                                <input type="hidden" id="estadoActual">

                                <c:choose>
                                    <c:when test="${sessionScope.usuarioRol == 'MEDICO'}">
                                        <input type="hidden" name="idPaciente" id="editPaciente">
                                        <input type="hidden" name="idMedico" id="editMedico">
                                        <input type="hidden" name="idEspecialidad" id="editEspecialidad">
                                        <input type="hidden" name="fecha" id="editFecha">
                                        <input type="hidden" name="hora" id="editHora">
                                        <input type="hidden" name="motivo" id="editMotivo">
                                    </c:when>
                                    <c:otherwise>
                                        <h6><fmt:message key="cita.paciente"/></h6>
                                        <select id="editPacientev" class="form-control mb-2" disabled>
                                            <c:forEach var="p" items="${listaPacientes}">
                                                <option value="${p.id}">${p.nombres} ${p.apellidos}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" name="idPaciente" id="editPaciente">

                                        <h6><fmt:message key="cita.medico"/></h6>
                                        <select id="editMedicoV" class="form-control mb-2">
                                            <c:forEach var="m" items="${listaMedicos}">
                                                <option value="${m.id}">${m.nombres} ${m.apellidos}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" name="idMedico" id="editMedico">

                                        <h6><fmt:message key="cita.especialidad"/></h6>
                                        <select id="editEspecialidadV" class="form-control mb-2">
                                            <c:forEach var="e" items="${listaEspecialidades}">
                                                <option value="${e.id}">${e.nombre}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" name="idEspecialidad" id="editEspecialidad">

                                        <h6><fmt:message key="cita.fecha"/></h6>
                                        <input type="date" id="editFechaV" class="form-control mb-2">
                                        <input type="hidden" name="fecha" id="editFecha">

                                        <h6><fmt:message key="cita.hora"/></h6>
                                        <input type="time" id="editHoraV" class="form-control mb-2">
                                        <input type="hidden" name="hora" id="editHora">

                                        <h6><fmt:message key="cita.motivo"/></h6>
                                        <input id="editMotivoV" class="form-control mb-2">
                                        <input type="hidden" name="motivo" id="editMotivo">
                                    </c:otherwise>
                                </c:choose>

                                <h6><fmt:message key="cita.estado"/></h6>
                                <select name="estado" id="editEstado" class="form-control mb-2">
                                    <option value="PROGRAMADA"><fmt:message key="cita.estado.programada"/></option>
                                    <option value="CONFIRMADA"><fmt:message key="cita.estado.confirmada"/></option>
                                    <option value="ATENDIDA"><fmt:message key="cita.estado.atendida"/></option>
                                    <option value="CANCELADA"><fmt:message key="cita.estado.cancelada"/></option>
                                </select>

                                <h6><fmt:message key="cita.observaciones"/></h6>
                                <input name="observaciones" id="editObservaciones" class="form-control mb-2">
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-main w-100">
                                    <fmt:message key="btn.actualizar"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>


        <c:if test="${sessionScope.usuarioRol != 'ENFERMERO'}">
            <div class="modal fade" id="modalCrear">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5><fmt:message key="cita.nueva"/></h5>
                            <button class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <form method="post" action="citas">
                            <div class="modal-body">

                                <input type="hidden" name="accion" value="crear">

                                <h6><fmt:message key="cita.paciente"/></h6>
                                <select name="idPaciente" class="form-control mb-2">
                                    <c:forEach var="p" items="${listaPacientes}">
                                        <option value="${p.id}">
                                            ${p.nombres} ${p.apellidos}
                                        </option>
                                    </c:forEach>
                                </select>

                                <h6><fmt:message key="cita.medico"/></h6>
                                <select name="idMedico" class="form-control mb-2">
                                    <c:forEach var="m" items="${listaMedicos}">
                                        <option value="${m.id}">
                                            ${m.nombres} ${m.apellidos}
                                        </option>
                                    </c:forEach>
                                </select>

                                <h6><fmt:message key="cita.especialidad"/></h6>
                                <select name="idEspecialidad" class="form-control mb-2">
                                    <c:forEach var="e" items="${listaEspecialidades}">
                                        <option value="${e.id}">
                                            ${e.nombre}
                                        </option>
                                    </c:forEach>
                                </select>

                                <h6><fmt:message key="cita.fecha"/></h6>
                                <input type="date" name="fecha" class="form-control mb-2">

                                <h6><fmt:message key="cita.hora"/></h6>
                                <input type="time" name="hora" class="form-control mb-2">

                                <h6><fmt:message key="cita.motivo"/></h6>
                                <input name="motivo" class="form-control mb-2">

                                <h6><fmt:message key="cita.estado"/></h6>
                                <select name="estado" class="form-control mb-2">
                                    <option value="PROGRAMADA">
                                        <fmt:message key="cita.estado.programada"/>
                                    </option>
                                    <option value="CONFIRMADA">
                                        <fmt:message key="cita.estado.confirmada"/>
                                    </option>
                                    <option value="ATENDIDA">
                                        <fmt:message key="cita.estado.atendida"/>
                                    </option>
                                    <option value="CANCELADA">
                                        <fmt:message key="cita.estado.cancelada"/>
                                    </option>
                                </select>

                                <h6><fmt:message key="cita.observaciones"/></h6>
                                <input name="observaciones" class="form-control mb-2">

                            </div>

                            <div class="modal-footer">
                                <button class="btn btn-main w-100">
                                    <fmt:message key="btn.guardar"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>

        <div id="loader-overlay">
            <img src="${pageContext.request.contextPath}/images/logo.png" class="loader-logo" alt="loading">
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
                                                            function toggleSidebar() {
                                                            document.getElementById('sidebar').classList.toggle('collapsed');
                                                            }

                                                            function abrirEditar(btn) {
            <c:choose>
                <c:when test="${sessionScope.usuarioRol == 'MEDICO'}">
                                                            editId.value = btn.dataset.id;
                                                            editPaciente.value = btn.dataset.paciente;
                                                            editMedico.value = btn.dataset.medico;
                                                            editEspecialidad.value = btn.dataset.especialidad;
                                                            editFecha.value = btn.dataset.fecha;
                                                            editHora.value = btn.dataset.hora;
                                                            editMotivo.value = btn.dataset.motivo;
                                                            editEstado.value = btn.dataset.estado?.trim().toUpperCase();
                                                            editObservaciones.value = btn.dataset.observaciones;
                                                            const estado = btn.dataset.estado?.trim().toUpperCase();
                                                            editEstado.disabled = (estado === 'ATENDIDA');
                                                            new bootstrap.Modal(modalEditar).show();
                </c:when>

                <c:when test="${sessionScope.usuarioRol != 'ENFERMERO'}">
                                                            editId.value = btn.dataset.id;
                                                            editPaciente.value = btn.dataset.paciente;
                                                            editPacientev.value = btn.dataset.paciente;
                                                            editMedico.value = btn.dataset.medico;
                                                            editMedicoV.value = btn.dataset.medico;
                                                            editEspecialidad.value = btn.dataset.especialidad;
                                                            editEspecialidadV.value = btn.dataset.especialidad;
                                                            editFecha.value = btn.dataset.fecha;
                                                            editFechaV.value = btn.dataset.fecha;
                                                            editHora.value = btn.dataset.hora;
                                                            editHoraV.value = btn.dataset.hora;
                                                            editMotivo.value = btn.dataset.motivo;
                                                            editMotivoV.value = btn.dataset.motivo;
                                                            editEstado.value = btn.dataset.estado?.trim().toUpperCase();
                                                            editObservaciones.value = btn.dataset.observaciones;
                                                            const estado = btn.dataset.estado?.trim().toUpperCase();
                                                            const esAtendida = (estado === 'ATENDIDA');
                                                            if (!esAtendida) {
                                                            editMedicoV.onchange = () => editMedico.value = editMedicoV.value;
                                                            editEspecialidadV.onchange = () => editEspecialidad.value = editEspecialidadV.value;
                                                            editFechaV.onchange = () => editFecha.value = editFechaV.value;
                                                            editHoraV.onchange = () => editHora.value = editHoraV.value;
                                                            editMotivoV.oninput = () => editMotivo.value = editMotivoV.value;
                                                            }

                                                            editMedicoV.disabled = esAtendida;
                                                            editEspecialidadV.disabled = esAtendida;
                                                            editFechaV.disabled = esAtendida;
                                                            editHoraV.disabled = esAtendida;
                                                            editMotivoV.disabled = esAtendida;
                                                            editEstado.disabled = esAtendida;
                                                            new bootstrap.Modal(modalEditar).show();
                </c:when>

                <c:otherwise>
                </c:otherwise>
            </c:choose>
                                                            }

        </script>

        <% Boolean hecho = (Boolean) request.getAttribute("hecho"); %>
        <c:if test="${rol != 'ENFERMERO' && rol != 'MEDICO'}">
            <script>
                document.getElementById("boton-pdf").addEventListener("click", () => {
                let input = document.getElementById('doc-input');
                let input1 = document.getElementById('doc-inputJ');
                if (input.value != ''){
                input1.value = input.value;
                input.value = '';
                }
                });
            </script>
        </c:if>


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
            window.location.href = '${pageContext.request.contextPath}/citas';
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