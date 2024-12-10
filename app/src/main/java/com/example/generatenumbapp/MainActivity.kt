package com.example.generatenumbapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
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
import java.text.DecimalFormat
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
    var μ by remember { mutableStateOf<Double?>(null) }
    var D by remember { mutableStateOf<Double?>(null) }
    var x by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Formula()
        CustomEditText("Введи µ", Ids.mean_val) { μ = it.toDoubleOrNull() }
        CustomEditText("Введи σ²", Ids.variance_value) { D = it.toDoubleOrNull() }
        CustomButton("Сгенерировать x", Ids.get_random_num) {
            if (μ != null && D != null)
                x = CountX(μ!!, D!!)
            else x = null
        }
        CustomTextView( "Результат", Ids.random_number_result, x)
    }
}

fun CountX(μ: Double, D: Double): String {
    return exp(μ + sqrt(D) * Random().nextGaussian()).toString()
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
fun CustomEditText(hint: String, id: Int, onValChange: (String) -> Unit) {
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
        update = { editText: EditText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onValChange(s.toString())
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    )
}

@Composable
fun CustomTextView(hint: String, id: Int, text: String? = null) {
    AndroidView(
        factory = { context: Context ->
            TextView(context).apply {
                this.hint = hint
                this.id = id
                this.setTag(id)
                this.textSize = 20f
                this.text = text
            }
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
        update = { editText ->
            editText.text = text
        }
    )
}

@Composable
fun CustomButton(hint: String, id: Int, onClick: () -> Unit) {
    AndroidView(
        factory = { context: Context ->
            Button(context).apply {
                this.text = hint
                this.id = id
                this.setTag(id)
                this.textSize = 20f
                setOnClickListener { onClick() }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}