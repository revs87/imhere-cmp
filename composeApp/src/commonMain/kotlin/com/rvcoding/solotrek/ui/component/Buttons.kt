package com.rvcoding.solotrek.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rvcoding.solotrek.ui.theme.DarkBackground
import com.rvcoding.solotrek.ui.theme.DarkTextColor
import com.rvcoding.solotrek.ui.theme.DarkTransparent
import com.rvcoding.solotrek.ui.theme.LightBackground
import com.rvcoding.solotrek.ui.theme.LightTextColor
import com.rvcoding.solotrek.ui.theme.PrimaryGreen
import com.rvcoding.solotrek.ui.theme.TertiaryGreen
import org.jetbrains.compose.ui.tooling.preview.Preview as JetbrainsPreview


@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thin: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier.setHeightIf(thin),
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen,
            contentColor = DarkTextColor,
            disabledContainerColor = PrimaryGreen.copy(alpha = 0.5f)
        ),
        contentPadding = setPaddingsIf(thin)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = thin.textSize()
        )
    }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thin: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier.setHeightIf(thin),
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = LightBackground,
            contentColor = LightTextColor,
            disabledContainerColor = LightBackground.copy(alpha = 0.5f)
        ),
        contentPadding = setPaddingsIf(thin)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = thin.textSize()
        )
    }
}

@Composable
fun SecondaryIconButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thin: Boolean = false,
    icon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = text,
            modifier = Modifier.size(20.dp)
        )
    }
) {
    Button(
        onClick = onClick,
        modifier = modifier.setHeightIf(thin),
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkTransparent,
            contentColor = LightTextColor,
            disabledContainerColor = DarkTransparent.copy(alpha = 0.5f)
        ),
        contentPadding = setPaddingsIf(thin)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = thin.textSize()
            )
        }
    }
}

@Composable
fun TertiaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thin: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier.setHeightIf(thin),
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = TertiaryGreen,
            contentColor = DarkTextColor,
            disabledContainerColor = TertiaryGreen.copy(alpha = 0.5f)
        ),
        contentPadding = setPaddingsIf(thin)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = thin.textSize()
        )
    }
}

@Composable
fun PromptTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    thin: Boolean = false
) {
    Row(
        modifier = modifier.setHeightIf(thin),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = text,
                color = LightTextColor,
                fontSize = thin.textSize(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun Modifier.setHeightIf(
    thin: Boolean
) = this.then(
    if (thin) { Modifier.height(40.dp) }
    else { Modifier.height(48.dp) }
)

private fun Boolean.textSize(
) = if (this) { 14.sp }
    else { 16.sp }

private fun setPaddingsIf(
    thin: Boolean
) = if (thin) {
    PaddingValues(
        start = 20.dp,
        end = 20.dp,
        top = 6.dp,
        bottom = 6.dp
    )
} else {
    PaddingValues(
        start = 20.dp,
        end = 20.dp,
        top = 12.dp,
        bottom = 12.dp
    )
}

@JetbrainsPreview
@Composable
fun ButtonsPreview() {
    Surface(color = DarkBackground) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                text = "Register"
            )
            SecondaryButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                text = "Request Help"
            )
            SecondaryIconButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                text = "Add note",
                thin = true
            )
            TertiaryButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                text = "Save"
            )
            PromptTextButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                text = "New User? Sign Up",
            )
        }
    }
}