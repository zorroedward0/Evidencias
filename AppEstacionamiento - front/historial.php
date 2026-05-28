<?php

require_once 'config.php';

$desde = isset($_GET['desde']) ? $_GET['desde'] : '';
$hasta = isset($_GET['hasta']) ? $_GET['hasta'] : '';
$tipo = isset($_GET['tipo']) ? $_GET['tipo'] : '';

$query = "/registros?estado=FINALIZADO";

if (!empty($desde)) {

    $query .= "&desde=" . urlencode($desde);

}

if (!empty($hasta)) {

    $query .= "&hasta=" . urlencode($hasta);

}

if (!empty($tipo)) {

    $query .= "&tipo=" . urlencode($tipo);

}

$historial = apiRequest($query);

$error = isset($historial['error']) ? $historial['error'] : null;

$totalRecaudado = 0;

if (!$error && is_array($historial)) {

    foreach ($historial as $reg) {

        $totalRecaudado += $reg['tarifa'];

    }

}

?>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Historial - Parqueadero Boyacá</title>

    <link rel="icon" type="image/svg+xml" href="favicon.svg">

    <link rel="stylesheet" href="css/styles.css">

</head>

<body>

    <header class="header">

        <div class="header-content">

            <div class="logo">
                🅿️ Parqueadero Boyacá
            </div>

            <nav>

                <a href="index.php" class="nav-link">
                    🏠 Inicio
                </a>

                <a href="entrada.php" class="nav-link">
                    ⬇️ Registrar Entrada
                </a>

                <a href="historial.php" class="nav-link active">
                    📋 Historial
                </a>

                <a href="vehiculos.php" class="nav-link">
                    🚗 Vehículos
                </a>

                <a href="reportes.php" class="nav-link">
                    📊 Reportes
                </a>

            </nav>

        </div>

    </header>

    <main class="container">

        <h1 class="titulo-pagina">
            📋 Historial de Registros
        </h1>

        <form method="GET" class="form-filtros">

            <div class="campo">

                <label>
                    Fecha Inicio
                </label>

                <input type="date" name="desde" value="<?= htmlspecialchars($desde) ?>">

            </div>

            <div class="campo">

                <label>
                    Fecha Fin
                </label>

                <input type="date" name="hasta" value="<?= htmlspecialchars($hasta) ?>">

            </div>

            <div class="campo">

                <label>
                    Tipo Vehículo
                </label>

                <select name="tipo">

                    <option value="">
                        Todos
                    </option>

                    <option value="CARRO" <?= $tipo == 'CARRO' ? 'selected' : '' ?>>
                        CARRO
                    </option>

                    <option value="MOTO" <?= $tipo == 'MOTO' ? 'selected' : '' ?>>
                        MOTO
                    </option>

                    <option value="CAMION" <?= $tipo == 'CAMION' ? 'selected' : '' ?>>
                        CAMION
                    </option>

                </select>

            </div>

            <div class="campo boton-filtro">

                <button type="submit" class="btn btn-azul">
                    🔎 Filtrar
                </button>

            </div>

        </form>

        <?php if ($error): ?>

            <div class="alerta alerta-error">
                ❌ <?= htmlspecialchars($error) ?>
            </div>

        <?php else: ?>

            <div class="tarjetas-grid">

                <div class="tarjeta tarjeta-verde">

                    <div class="tarjeta-icono">
                        💰
                    </div>

                    <div class="tarjeta-numero">
                        $<?= number_format($totalRecaudado, 0, ',', '.') ?>
                    </div>

                    <div class="tarjeta-label">
                        Total recaudado
                    </div>

                </div>

                <div class="tarjeta tarjeta-azul">

                    <div class="tarjeta-icono">
                        🚗
                    </div>

                    <div class="tarjeta-numero">
                        <?= count($historial) ?>
                    </div>

                    <div class="tarjeta-label">
                        Servicios encontrados
                    </div>

                </div>

            </div>

            <section class="seccion">

                <?php if (count($historial) > 0): ?>

                    <div class="tabla-container">

                        <table class="tabla">

                            <thead>

                                <tr>
                                    <th>#</th>
                                    <th>Placa</th>
                                    <th>Tipo</th>
                                    <th>Entrada</th>
                                    <th>Salida</th>
                                    <th>Tarifa</th>
                                </tr>

                            </thead>

                            <tbody>

                                <?php foreach ($historial as $reg): ?>

                                    <tr>

                                        <td>
                                            <?= htmlspecialchars($reg['id']) ?>
                                        </td>

                                        <td>
                                            <strong class="placa">
                                                <?= htmlspecialchars($reg['placa']) ?>
                                            </strong>
                                        </td>

                                        <td>

                                            <span class="badge badge-<?= strtolower($reg['tipo']) ?>">

                                                <?= htmlspecialchars($reg['tipo']) ?>

                                            </span>

                                        </td>

                                        <td>
                                            <?= htmlspecialchars($reg['entrada']) ?>
                                        </td>

                                        <td>
                                            <?= htmlspecialchars($reg['salida']) ?>
                                        </td>

                                        <td>

                                            <strong>

                                                $<?= number_format($reg['tarifa'], 0, ',', '.') ?>

                                            </strong>

                                        </td>

                                    </tr>

                                <?php endforeach; ?>

                            </tbody>

                        </table>

                    </div>

                    <div class="total-tabla">

                        <h3>

                            💰 Total acumulado:

                            <span>

                                $<?= number_format($totalRecaudado, 0, ',', '.') ?>

                            </span>

                        </h3>

                    </div>

                <?php else: ?>

                    <div class="mensaje-vacio">
                        📭 No hay registros encontrados.
                    </div>

                <?php endif; ?>

            </section>

        <?php endif; ?>

    </main>

    <footer class="footer">

        <p>

            SENA CIMM · ADSO 228118 · Regional Boyacá · <?= date('Y') ?>

        </p>

    </footer>

    <script src="js/app.js"></script>

</body>

</html>