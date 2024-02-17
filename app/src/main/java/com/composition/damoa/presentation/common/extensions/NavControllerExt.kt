package com.composition.damoa.presentation.common.extensions

import androidx.navigation.NavController

fun NavController.navigateSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId)
        launchSingleTop = true
    }
}