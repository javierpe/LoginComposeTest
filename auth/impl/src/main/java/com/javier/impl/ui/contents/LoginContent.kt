package com.javier.impl.ui.contents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.javier.design.components.ButtonComponent
import com.javier.design.components.EditTextComponent
import com.javier.design.components.EditTextComponentType
import com.javier.design.theme.TestLoginTheme
import com.javier.design.theme.listVerticalArrangementSpacing
import com.javier.design.theme.logoSize
import com.javier.design.theme.spacingSize
import com.javier.impl.R

@Composable
fun LoginContent(
    loading: MutableState<Boolean>,
    onLoginClick: (String, String) -> Unit
) {
    val alpha = animateFloatAsState(if (loading.value) 0.6f else 1f)

    Column(
        verticalArrangement = Arrangement.spacedBy(listVerticalArrangementSpacing),
        modifier = Modifier.graphicsLayer(alpha = alpha.value)
    ) {

        val name = rememberSaveable {
            mutableStateOf("")
        }

        val password = rememberSaveable {
            mutableStateOf("")
        }

        Image(
            painter = painterResource(id = com.javier.design.R.drawable.ic_crown),
            contentDescription = "",
            modifier = Modifier
                .size(logoSize)
                .align(Alignment.CenterHorizontally)
        )

        EditTextComponent(
            stringResource(R.string.login_user_name_label),
            onValueChange = { name.value = it }
        )

        EditTextComponent(
            stringResource(R.string.login_user_password_label),
            type = EditTextComponentType.PASSWORD, onValueChange = { password.value = it }
        )

        Spacer(modifier = Modifier.size(spacingSize).fillMaxWidth())
        
        ButtonComponent(text = stringResource(R.string.login_button_label)) {
            onLoginClick.invoke(name.value, password.value)
        }
    }
}

@Composable
@Preview
fun PreviewLoginContent() {
    TestLoginTheme {
        val loading = remember {
            mutableStateOf(false)
        }
        LoginContent(loading) { _, _ -> }
    }
}