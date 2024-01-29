package com.composition.damoa.presentation.screens.pointCharge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.PointChargeItem
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.DonationDescription
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.PaymentInformationList
import com.composition.damoa.presentation.common.components.PolicyButtonList
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.utils.pointChargeItems
import com.composition.damoa.presentation.ui.theme.Gray10
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PointChargeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PointChargeScreen()
        }
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, PointChargeActivity::class.java)
    }
}

@Composable
private fun PointChargeScreen() {
    PetudioTheme {
        val context = LocalContext.current
        Scaffold(
            topBar = { PointChargeTopBar { (context as? Activity)?.finish() } },
        ) { padding ->
            PointChargeContent(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding()),
                userOwnPoint = 0,
                pointChargeItems = pointChargeItems(),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PointChargeTopBar(onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}

@Composable
private fun PointChargeContent(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    userOwnPoint: Int,
    pointChargeItems: List<PointChargeItem>,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        PointChargeTitle()
        UserOwnPoint(userOwnPoint = userOwnPoint)
        DonationDescription(modifier = Modifier.padding(vertical = 28.dp))
        PointList(pointChargeItems = pointChargeItems)
        PaymentInformationList()
        PolicyButtonList(modifier = Modifier.padding(top = 14.dp))
    }
}

@Composable
private fun PointChargeTitle() {
    BigTitle(titleRes = R.string.point_charge_title)
}

@Composable
private fun UserOwnPoint(userOwnPoint: Int) {
    PointRow(
        modifier = Modifier.padding(top = 20.dp),
        title = stringResource(id = R.string.my_point),
        point = userOwnPoint,
    )
}

@Composable
private fun PointList(
    modifier: Modifier = Modifier,
    pointChargeItems: List<PointChargeItem>,
) {
    Column(modifier = modifier) {
        pointChargeItems.forEachIndexed { index, pointChargeItem ->
            PointChargeItem(pointChargeItem = pointChargeItem)
        }
    }
}

@Composable
private fun PointChargeItem(
    modifier: Modifier = Modifier,
    pointChargeItem: PointChargeItem,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
        modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Point(
            modifier = modifier.padding(vertical = 20.dp),
            point = pointChargeItem.point,
        )
        PurchaseButton(price = pointChargeItem.price)
    }
}

@Composable
private fun PurchaseButton(
    modifier: Modifier = Modifier,
    price: Int,
    onClick: () -> Unit = {},
) {
    GradientButton(
        modifier = modifier.size(90.dp, 44.dp),
        text = String.format("%,d", price),
        shape = RoundedCornerShape(12.dp),
        gradient =
        Brush.verticalGradient(
            colors = listOf(Color.Black, Color.Black),
            startY = Float.POSITIVE_INFINITY,
            endY = 0F,
        ),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        onClick = onClick,
    )
}

@Composable
private fun PointRow(
    modifier: Modifier,
    title: String,
    point: Int,
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Gray10)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SmallTitle(title = title)
        Point(point = point)
    }
}

@Composable
private fun Point(
    modifier: Modifier = Modifier,
    point: Int = 0,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = R.drawable.img_gold_coin),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = String.format("%,d", point),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp),
            textAlign = TextAlign.Start,
        )
    }
}

@Preview
@Composable
private fun PointChargePreview() {
    PointChargeScreen()
}
