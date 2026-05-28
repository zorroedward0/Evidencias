<?php

require_once 'config.php';

$reporte = apiRequest('/registros/historial');

$error = isset($reporte['error']) ? $reporte['error'] : null;

$totalDia = 0;
$cantidadSalidas = 0;

if (!$error && is_array($reporte)) {

    $totalDia = $reporte['totaldia'];
    $cantidadSalidas = $reporte['cantidadsalidas'];

}

?>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Reportes - Parqueadero Boyacá</title>

    <link rel="icon" type="image/svg+xml" href="favicon.svg">

    <link rel="stylesheet" href="css/styles.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

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

                <a href="historial.php" class="nav-link">
                    📋 Historial
                </a>

                <a href="vehiculos.php" class="nav-link">
                    🚗 Vehículos
                </a>

                <a href="reportes.php" class="nav-link active">
                    📊 Reportes
                </a>

            </nav>

        </div>

    </header>

    <main class="container">

        <h1 class="titulo-pagina">
            📊 Reportes del Sistema
        </h1>

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
                        $<?= number_format($totalDia, 0, ',', '.') ?>
                    </div>

                    <div class="tarjeta-label">
                        Total recaudado hoy
                    </div>

                </div>

                <div class="tarjeta tarjeta-azul">

                    <div class="tarjeta-icono">
                        🚗
                    </div>

                    <div class="tarjeta-numero">
                        <?= $cantidadSalidas ?>
                    </div>

                    <div class="tarjeta-label">
                        Vehículos retirados
                    </div>

                </div>

                <div class="tarjeta tarjeta-naranja">

                    <div class="tarjeta-icono">
                        📈
                    </div>

                    <div class="tarjeta-numero">

                        <?php

                        if ($cantidadSalidas > 0) {

                            echo "$" . number_format($totalDia / $cantidadSalidas, 0, ',', '.');

                        } else {

                            echo "$0";

                        }

                        ?>

                    </div>

                    <div class="tarjeta-label">
                        Promedio por servicio
                    </div>

                </div>

                <div class="tarjeta tarjeta-roja">

                    <div class="tarjeta-icono">
                        📅
                    </div>

                    <div class="tarjeta-numero">
                        <?= date('d/m/Y') ?>
                    </div>

                    <div class="tarjeta-label">
                        Fecha del reporte
                    </div>

                </div>

            </div>

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