import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.screens.giftcard.component.GiftCardBackground
import com.composition.damoa.presentation.screens.giftcard.component.GiftCardForeground


@Composable
fun GiftCardItem(
    modifier: Modifier = Modifier,
    giftCard: GiftCard,
    onGiftCardDetailClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(10 / 5F),
        shape = CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(),
    ) {
        Box {
            GiftCardBackground(isUsable = !giftCard.isExpired and !giftCard.isUsed)
            GiftCardForeground(giftCard = giftCard, onGiftCardDetailClick = onGiftCardDetailClick)
        }
    }
}

