package com.javier.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.javier.design.theme.TestLoginTheme
import com.javier.design.theme.Typography
import com.javier.design.theme.buttonCornerRadius
import com.javier.design.theme.buttonHeight

@Composable
fun ButtonComponent(
    text: String,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(buttonCornerRadius))
            .wrapContentHeight()
            .height(buttonHeight)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
            .clickable { onClick?.invoke() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text.uppercase(),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            style = Typography.button
        )
    }
}

@Composable
@Preview
fun PreviewButtonComponent() {
    TestLoginTheme {
        ButtonComponent(text = "Login")
    }
}