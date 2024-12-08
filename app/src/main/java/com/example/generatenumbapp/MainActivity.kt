package com.example.generatenumbapp

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.generatenumbapp.ui.theme.GenerateNumbAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.alpha
import java.util.Random
import kotlin.math.exp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                MainScreen(innerPadding)
            }
        }
    }
}

object Ids {
    const val mean_val = 1
    const val variance_value = 2
    const val get_random_num = 3
    const val random_number_result = 4
}

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
//        val random = Random()
//        val z = random.nextGaussian()
        Formula()
        CustomEditText("Введи µ", Ids.mean_val)
        CustomEditText("Введи σ²", Ids.variance_value)
        CustomButton("Сгенерировать x", Ids.get_random_num)
        CustomTextView( "Результат", Ids.random_number_result)
    }
}

@Composable
fun Formula() {
    Box(modifier = Modifier.padding(10.dp).border(2.dp, Color.Gray).background(Color.LightGray).padding(8.dp)) {
        val textWidth = remember { mutableStateOf(0.dp) }
        Text(
            text = "x = e",
            fontSize = 25.sp,
            modifier = Modifier.alpha(0f)
                .onGloballyPositioned { layoutCoordinates ->
                    textWidth.value = layoutCoordinates.size.width.dp
                }
        )
        Column(horizontalAlignment = Alignment.Start) {
            Row {
                Spacer(modifier = Modifier.width(textWidth.value / 2))
                Text(
                    text = "(μ + σ * z)",
                    fontSize = 23.sp,
                    color = Color.DarkGray
                )
            }
            Row {
                Text(
                    text = "x = e",
                    fontSize = 27.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(textWidth.value))
                Text(
                    text = ", где",
                    fontSize = 22.sp,
                    color = Color.DarkGray
                )
            }
            Text(
                text = "μ - математическое ожидание",
                fontSize = 22.sp,
                color = Color.DarkGray
            )
            Text(
                text = "σ - среднее квадратичное отклонение",
                fontSize = 22.sp,
                color = Color.DarkGray
            )
            Text(
                text = "z~N(0,1) - случайное значение",
                fontSize = 22.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun CustomEditText(hint: String, id: Int) {
    val context = LocalContext.current
    AndroidView(
        factory = { context: Context ->
            EditText(context).apply {
                this.hint = hint
                this.id = id
                this.setTag(id)
                this.textSize = 20f
            }
        },
        modifier = Modifier.fillMaxWidth(),
        update = { editText ->
        }
    )
}

@Composable
fun CustomTextView(hint: String, id: Int) {
    AndroidView(
        factory = { context: Context ->
            TextView(context).apply {
                this.hint = hint
                this.id = id
                this.setTag(id)
                this.textSize = 20f
            }
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
        update = { editText ->
        }
    )
}

@Composable
fun CustomButton(hint: String, id: Int) {
    AndroidView(
        factory = { context: Context ->
            Button(context).apply {
                this.hint = hint
                this.id = id
                this.setTag(id)
                this.textSize = 20f
            }
        },
        modifier = Modifier.fillMaxWidth(),
        update = { editText ->
        }
    )
}