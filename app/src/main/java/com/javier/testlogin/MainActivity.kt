package com.javier.testlogin

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsets.Type.ime
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import com.javier.api.AuthApi
import com.javier.api.AuthLoaderApi
import com.javier.design.theme.TestLoginTheme
import com.javier.design.theme.Typography
import com.javier.design.theme.screenPadding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authLoaderApi: AuthLoaderApi

    @Inject
    lateinit var authApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TestLoginTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    /**
                     * Replace this with compose navigation
                     */

                    val account = authApi.currentUser().collectAsState()

                    Box(
                        modifier = Modifier
                            .padding(screenPadding)
                    ) {

                        val visible = remember { mutableStateOf(true) }
                        val density = LocalDensity.current

                        visible.value = account.value == null

                        AnimatedVisibility(
                            visible = visible.value,
                            enter = slideInVertically {
                                // Slide in from 40 dp from the top.
                                with(density) { -40.dp.roundToPx() }
                            } + expandVertically(
                                // Expand from the top.
                                expandFrom = Alignment.Top
                            ) + fadeIn(
                                // Fade in with the initial alpha of 0.3f.
                                initialAlpha = 0.6f
                            ),
                            exit = fadeOut()
                        ) {
                            authLoaderApi.Load()
                        }

                        if (visible.value.not()) {
                            Text(
                                text = getString(R.string.main_welcome_label, account.value?.name),
                                style = Typography.h4,
                                color = MaterialTheme.colors.primaryVariant,
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
