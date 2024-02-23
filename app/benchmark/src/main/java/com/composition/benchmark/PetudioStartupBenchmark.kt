package com.composition.benchmark

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PetudioStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun 앱이_COLD_스타트_할_때_실행되는_시간을_측정한다() = benchmarkRule.measureRepeated(
        packageName = TARGET_PACKAGE,
        iterations = DEFAULT_ITERATIONS,
        metrics = listOf(StartupTimingMetric()),
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait()
    }

    @Test
    fun 앱이_WARM_스타트_할_때_실행되는_시간을_측정한다() = benchmarkRule.measureRepeated(
        packageName = TARGET_PACKAGE,
        iterations = DEFAULT_ITERATIONS,
        metrics = listOf(StartupTimingMetric()),
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait()
    }

    @Test
    fun 앱이_HOT_스타트_할_때_실행되는_시간을_측정한다() = benchmarkRule.measureRepeated(
        packageName = TARGET_PACKAGE,
        iterations = DEFAULT_ITERATIONS,
        metrics = listOf(StartupTimingMetric()),
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait()
    }

    companion object {
        private const val TARGET_PACKAGE = "com.composition.damoa"
        private const val DEFAULT_ITERATIONS = 3
    }
}