package com.composition.damoa.benchmark.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.composition.damoa.benchmark.onlyStartup
import com.composition.damoa.benchmark.startupAndMeasure
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StartupOnlyBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun Baseline_Profile_없이_앱_실행() = benchmarkRule.startupAndMeasure(
        compilationMode = CompilationMode.None(),
        metrics = listOf(StartupTimingMetric()),
        measure = { onlyStartup() },
    )

    @Test
    fun Baseline_Profile_포함_앱_실행() = benchmarkRule.startupAndMeasure(
        compilationMode = CompilationMode.Partial(),
        metrics = listOf(StartupTimingMetric()),
        measure = { onlyStartup() },
    )
}