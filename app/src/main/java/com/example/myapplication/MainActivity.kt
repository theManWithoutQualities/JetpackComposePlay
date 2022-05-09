package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Conversation(messages = sampleData)
            }
        }
    }
}

data class Message(
    val author: String,
    val body: String,
)

@Composable
fun MessageCard(message: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "none",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                message.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )
                    .padding(1.dp)
            ) {
                val padding by animateDpAsState(
                    if (isExpanded) 16.dp else 4.dp,
                )
                Text(
                    message.body,
                    modifier = Modifier.padding(all = padding),
                    style = MaterialTheme.typography.body2,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    Column {
        Surface(color = MaterialTheme.colors.primary) {
            Text(text = "Hello, Android", modifier = Modifier.padding(all = 24.dp))
        }
        LazyColumn {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PreviewMessageCard() {
    MyApplicationTheme {
        Conversation(messages = sampleData)
    }
}

val sampleData = listOf(
    Message(
        "author1",
        "message1"
    ),
    Message(
        "author2",
        "message2 dfdfdffd dfdfdfdfd fdfdsfsfdsfssfsfsdf dsfsf"
    ),
    Message(
        "author3",
        "message3"
    ),
    Message(
        "author4",
        "message4 dfssfsfsdfsffssfs"
    ),
    Message(
        "author5",
        "message5 message2 dfdfdffd dfdfdfdfd fdfdsfsfdsfssfsfsdf dsfsf message2 " +
                "dfdfdffd dfdfdfdfd fdfdsfsfdsfssfsfsdf dsfsf"
    ),
)
