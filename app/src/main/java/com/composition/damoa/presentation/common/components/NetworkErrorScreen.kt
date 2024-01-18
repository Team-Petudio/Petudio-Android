package com.composition.damoa.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.PrimaryColors
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun NetworkErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .padding(bottom = 30.dp)
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi_error),
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(120.dp),
        )
        Text(
            text = stringResource(id = R.string.network_error_occurred),
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 12.dp),
        )
        RetryButton(modifier.padding(top = 40.dp)) {}
    }
}

@Composable
private fun RetryButton(
    modifier: Modifier,
    onRetryClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier.wrapContentSize(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Gray30),
        onClick = onRetryClick,
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Filled.Refresh,
                contentDescription = null,
                tint = Color.DarkGray,
            )
            Text(
                text = stringResource(id = R.string.refresh),
                modifier = Modifier.padding(start = 12.dp),
                color = Color.DarkGray,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview
@Composable
private fun NetworkErrorScreenPreview() {
    NetworkErrorScreen()
}
