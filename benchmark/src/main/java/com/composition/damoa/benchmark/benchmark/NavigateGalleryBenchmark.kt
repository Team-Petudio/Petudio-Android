package com.composition.damoa.benchmark.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.composition.damoa.benchmark.navigateGalleryAndFindGalleryTabs
import com.composition.damoa.benchmark.onlyStartup
import com.composition.damoa.benchmark.startupAndMeasure
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigateGalleryBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun Baseline_Profile_없이_갤러리_화면으로_이동() = benchmarkRule.startupAndMeasure(
        compilationMode = CompilationMode.None(),
        startupMode = StartupMode.WARM,
        setupBlock = { onlyStartup() },
        measure = { navigateGalleryAndFindGalleryTabs() },
    )

    @Test
    fun Baseline_Profile_포함_갤러리_화면으로_이동() = benchmarkRule.startupAndMeasure(
        compilationMode = CompilationMode.Partial(),
        startupMode = StartupMode.WARM,
        setupBlock = { onlyStartup() },
        measure = { navigateGalleryAndFindGalleryTabs() },
    )
}