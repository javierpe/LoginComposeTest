package com.javier.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.javier.design.theme.TestLoginTheme
import com.javier.design.theme.Typography
import com.javier.design.theme.buttonCornerRadius
import com.javier.design.theme.buttonHeight

@Composable
fun EditTextComponent(
    hint: String,
    type: EditTextComponentType = EditTextComponentType.NONE,
    onValueChange: (String) -> Unit
) {
    val text = rememberSaveable {
        mutableStateOf("")
    }

    val isPasswordType = type == EditTextComponentType.PASSWORD

    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onValueChange.invoke(it)
        },
        visualTransformation = if (isPasswordType)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPasswordType) KeyboardType.Password else KeyboardType.Text
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(buttonCornerRadius))
            .wrapContentHeight()
            .height(buttonHeight)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.primaryVariant,
            disabledTextColor = Color.Transparent,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        textStyle = Typography.subtitle2,
        placeholder = {
            Text(
                text = hint,
                style = Typography.subtitle2,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.alpha(0.3f)
            )
        }
    )
}

enum class EditTextComponentType{
    NONE,
    PASSWORD
}

@Composable
@Preview
fun PreviewEditTextComponent() {
    TestLoginTheme {
        EditTextComponent(hint = "Password", type = EditTextComponentType.PASSWORD) {

        }
    }
}