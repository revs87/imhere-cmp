package com.rvcoding.solotrek.ui.screens._internal.allinoneapi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.rvcoding.solotrek.domain.data.api.model.ConfigurationKeys.Companion.USER_ID_KEY
import com.rvcoding.solotrek.ui.component.PrimaryButton
import com.rvcoding.solotrek.ui.component.SecondaryButton
import com.rvcoding.solotrek.ui.component.TertiaryButton
import com.rvcoding.solotrek.ui.screens.users.UsersScreen
import com.rvcoding.solotrek.ui.screens.users.UsersStateModel
import com.rvcoding.solotrek.ui.theme.AppTheme
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Preview
@Composable
fun AllInOneApiScreen(
    sm: AllInOneApiStateModel = getKoin().get<AllInOneApiStateModel>(),
    um: UsersStateModel = getKoin().get<UsersStateModel>(),
    modifier: Modifier = Modifier
) {
    val content by sm.content.collectAsState()
    var userId by remember { mutableStateOf("revs") }
    var password by remember { mutableStateOf("test") }
    var showUsers by remember { mutableStateOf(false) }
    var toSubscribe by remember { mutableStateOf("") }
    var toUnsubscribe by remember { mutableStateOf("") }

    val coScope = rememberCoroutineScope()
    val onConfiguration: () -> Unit = { coScope.launch { sm.userIntent.send(AllInOneApiIntent.Configuration) } }
    val onRegister: (String, String, String, String) -> Unit = { uid, pass, firstName, lastName -> coScope.launch { sm.userIntent.send(AllInOneApiIntent.Register(uid, pass, firstName, lastName)) } }
    val onLogin: (String, String) -> Unit = { uid, pass -> coScope.launch { sm.userIntent.send(AllInOneApiIntent.Login(uid, pass)) } }
    val onLogout: (String) -> Unit = { uid -> coScope.launch { sm.userIntent.send(AllInOneApiIntent.Logout(uid)) } }
    val configState by sm.userConfig.collectAsState()
    val state by sm.state.collectAsState()

    LaunchedEffect(true) {
        um.error.consumeAsFlow()
            .onEach { error ->
                sm.setContent(error)
                showUsers = false
            }
            .launchIn(coScope)
    }

    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "State: $state", fontSize = 14.sp)
                Text(text = "LoggedIn: ${configState.settings[USER_ID_KEY]}", fontSize = 14.sp)
                PrimaryButton(onClick = { onConfiguration.invoke(); showUsers = false }, text = "Configuration")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = userId,
                        onValueChange = { userId = it },
                        label = { Text("User ID") }
                    )
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") }
                    )
                }
                Row {
                    PrimaryButton(onClick = { onRegister(userId, password, "", ""); showUsers = false }, text = "Register")
                    PrimaryButton(onClick = { onLogin(userId, password); showUsers = false }, text = "Login")
                    SecondaryButton(onClick = { onLogout(userId); showUsers = false }, text = "Logout", thin = true)
                }
                Row {
                    PrimaryButton(onClick = { um.requestUsers(""); showUsers = true }, text = "AllUsers")
                    PrimaryButton(onClick = { um.requestSubscribedUsers(userId); showUsers = true }, text = "SubscribedUsers")
                    TertiaryButton(onClick = { um.requestSubscribingUsers(userId); showUsers = true }, text = "Watchers", thin = true)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = toSubscribe,
                        onValueChange = { toSubscribe = it },
                        label = { Text("User ID to subscribe") }
                    )
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = toUnsubscribe,
                        onValueChange = { toUnsubscribe = it },
                        label = { Text("User ID to unsubscribe") }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { um.requestSubscription(userId, toSubscribe); showUsers = true }) { Text("Subscribe")
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { um.requestUnsubscription(userId, toUnsubscribe); showUsers = true }) { Text("Unsubscribe")
                    }
                }

                AnimatedVisibility(showUsers) {
                    UsersScreen(sm = um, modifier = Modifier.weight(1f).fillMaxWidth())
                }
                AnimatedVisibility(!showUsers) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = content,
                        fontSize = 14.sp
                    )
                }
            }

        }
    }
}