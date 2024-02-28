package com.composition.damoa.benchmark.baselineprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.composition.damoa.benchmark.navigateGalleryAndFindGalleryTabs
import com.composition.damoa.benchmark.onlyStartup
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@RequiresApi(Build.VERSION_CODES.P)
class NavigateGalleryBaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generateBaselineProfile() = rule.collect(
        packageName = "com.composition.damoa",
    ) {
        onlyStartup()
        navigateGalleryAndFindGalleryTabs()
    }
}
