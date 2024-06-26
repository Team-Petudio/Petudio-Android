package com.composition.damoa.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

private const val DEFAULT_ITERATIONS = 10
private const val PACKAGE_NAME = "com.composition.damoa"


fun MacrobenchmarkRule.startupAndMeasure(
    compilationMode: CompilationMode,
    startupMode: StartupMode = StartupMode.COLD,
    iterations: Int = DEFAULT_ITERATIONS,
    metrics: List<Metric>,
    setupBlock: MacrobenchmarkScope.() -> Unit = { },
    measure: MacrobenchmarkScope.() -> Unit = { },
) = measureRepeated(
    packageName = PACKAGE_NAME,
    metrics = metrics,
    iterations = iterations,
    startupMode = startupMode,
    compilationMode = compilationMode,
    setupBlock = setupBlock,
    measureBlock = measure,
)

fun MacrobenchmarkScope.navigateGalleryAndFindGalleryTabs() {
    val galleryTab = device.findObject(By.text("갤러리")) ?: error("갤러리 탭을 찾을 수 없습니다.")
    galleryTab.click()

    device.wait(Until.hasObject(By.text("앨범")), 5_000)
}

fun MacrobenchmarkScope.onlyStartup() {
    pressHome()
    startActivityAndWait()
}