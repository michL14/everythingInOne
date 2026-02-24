package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import everythinginone.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter

@Composable
fun SuccessAnimation() {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/success-confetti.json").decodeToString()
        )
    }
    Image(
        painter = rememberLottiePainter(
            composition = composition,
            iterations = Compottie.IterateForever
        ),
        contentDescription = "Lottie animation"
    )
}