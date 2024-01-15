package com.composition.damoa.presentation.screens.profileCreation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.PaymentItem
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.PrimaryColors

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Surface(
        color = Color.White,
        modifier =
            modifier
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
    ) {
        PaymentContent(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 20.dp, bottom = 100.dp),
        )
        PaymentButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {},
        )
    }
}

@Composable
private fun PaymentContent(modifier: Modifier = Modifier) {
    Column(modifier) {
        BigTitle(titleRes = R.string.payment)
        PaymentItem(
            modifier = Modifier.padding(top = 32.dp),
            titleRes = R.string.payment_standard_title,
            descriptionRes = R.string.payment_standard_desc,
            isDiscounted = true,
            originalPrice = 1500,
            discountedPrice = 880,
        )
        DonationDescription(modifier = Modifier.padding(vertical = 28.dp))
        InformationList()
        PolicyButtonList(modifier = Modifier.padding(top = 14.dp))
    }
}

@Composable
private fun DonationDescription(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.payment_donation_desc),
        fontSize = 13.sp,
        color = Gray60,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun InformationList() {
    InformationItem(
        titleRes = R.string.goods_information_title,
        descResList =
            listOf(
                R.string.goods_information_desc1,
                R.string.goods_information_desc2,
            ),
    )
    InformationItem(
        titleRes = R.string.refund_information_title,
        descResList =
            listOf(
                R.string.refund_information_desc1,
                R.string.refund_information_desc2,
                R.string.refund_information_desc3,
            ),
    )
}

@Composable
private fun InformationItem(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes descResList: List<Int> = emptyList(),
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val dividerPadding = if (isExpanded) 12.dp else 0.dp

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        InformationTitle(
            titleRes = titleRes,
            onClick = { isExpanded = !isExpanded },
        )
        AnimatedVisibility(visible = isExpanded) {
            InformationDescription(
                descResList = descResList,
            )
        }
        Divider(
            thickness = 1.dp,
            color = Gray20,
            modifier = Modifier.padding(top = dividerPadding),
        )
    }
}

@Composable
private fun InformationTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = onClick,
                ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(titleRes),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.Gray,
        )
    }
}

@Composable
private fun InformationDescription(
    modifier: Modifier = Modifier,
    @StringRes descResList: List<Int>,
) {
    Column(modifier) {
        descResList.forEach { descRes ->
            InformationDescriptionItem(descRes = descRes)
        }
    }
}

@Composable
private fun InformationDescriptionItem(
    modifier: Modifier = Modifier,
    @StringRes descRes: Int,
) {
    Text(
        modifier = modifier,
        text = stringResource(descRes),
        fontSize = 12.sp,
        color = Gray40,
    )
}

@Composable
private fun PolicyButtonList(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        TermOfUse(onClick = {})
        Privacy(onClick = {})
    }
}

@Composable
private fun TermOfUse(onClick: () -> Unit) {
    PolicyTextButton(textRes = R.string.terms_of_use, onClick = onClick)
}

@Composable
private fun Privacy(onClick: () -> Unit) {
    PolicyTextButton(textRes = R.string.privacy_policy, onClick = onClick)
}

@Composable
private fun PolicyTextButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    onClick: () -> Unit,
) {
    TextButton(modifier = modifier, onClick = onClick) {
        Text(
            text = stringResource(id = textRes),
            color = Gray40,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun PaymentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(modifier) {
        Spacer(modifier = Modifier.weight(1F))
        GradientButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 1F),
            shape = RoundedCornerShape(12.dp),
            text = stringResource(id = R.string.payment),
            fontColor = Color.White,
            fontSize = 16.sp,
            enabled = true,
            fontWeight = FontWeight.Bold,
            gradient = Brush.horizontalGradient(PrimaryColors),
            onClick = onClick,
        )
    }
}

@Preview
@Composable
private fun PaymentScreenPreview() {
    PaymentScreen(navController = rememberNavController())
}
