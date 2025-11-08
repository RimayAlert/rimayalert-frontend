package com.fazq.rimayalert.core.ui.navigation

import androidx.navigation.NavController

/**
 * Navega a una pantalla limpiando el back stack hasta esa misma pantalla.
 * Útil para navegación entre pantallas principales (bottom navigation).
 */
fun NavController.navigateToScreen(screen: Screen) {
    navigate(screen.route) {
        popUpTo(screen.route) { inclusive = true }
        launchSingleTop = true
    }
}

/**
 * Navega a una pantalla con Single Top (evita duplicados en el stack).
 */
fun NavController.navigateSingleTop(screen: Screen) {
    navigate(screen.route) {
        launchSingleTop = true
    }
}

/**
 * Navega a una pantalla limpiando todo el back stack hasta una ruta específica.
 * Útil para login/logout flows.
 */
fun NavController.navigateAndClearBackStackTo(
    screen: Screen,
    popUpToRoute: String,
    inclusive: Boolean = true
) {
    navigate(screen.route) {
        popUpTo(popUpToRoute) {
            this.inclusive = inclusive
        }
        launchSingleTop = true
    }
}

/**
 * Navega guardando el estado (útil para bottom navigation con múltiples pantallas).
 */
fun NavController.navigateWithState(screen: Screen, popUpToRoute: String) {
    navigate(screen.route) {
        popUpTo(popUpToRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}