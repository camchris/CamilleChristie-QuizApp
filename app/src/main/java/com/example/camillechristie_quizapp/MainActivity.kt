package com.example.camillechristie_quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.camillechristie_quizapp.ui.theme.CamilleChristieQuizAppTheme
import kotlinx.coroutines.launch
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CamilleChristieQuizAppTheme {
                quiz()
            }
        }
    }
}

@Composable
fun quiz(){

    val questions = listOf(
        "What is 2+2?" to "4",
        "What is 4+4?" to "8",
        "What letter does Michelle's name start with?" to "M",
        "What letter does Cami's name start with?" to "C",
        "What color is the sky?" to "blue",
        "What color is the grass?" to "green"
    )
    var currentText by remember{ mutableStateOf("") }
    var shuffledQuestions = remember { questions.shuffled() }
    var currentIndex by remember { mutableIntStateOf(0) }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentQuestion = shuffledQuestions[currentIndex]

    Column() {
        Spacer(modifier = Modifier.padding(15.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            Text(
                text = currentQuestion.first,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.padding(15.dp))


        TextField(
            value = currentText,
            onValueChange = { currentText = it },
            label = { Text("Answer here") })


        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            if (currentText == currentQuestion.second) {

                if (currentIndex == questions.size-1) {
                    scope.launch {
                        val result = snackBarHostState
                            .showSnackbar(
                                message = "All questions completed!",
                                actionLabel = "Restart Quiz",
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                shuffledQuestions = questions.shuffled()
                                currentIndex = 0
                            }
                            SnackbarResult.Dismissed -> {
                                // idk
                            }
                        }
                    }

                } else {
                    scope.launch {
                        val result = snackBarHostState
                            .showSnackbar(
                                message = "Correct!",
                                actionLabel = "Continue",
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                currentIndex++
                            }
                            SnackbarResult.Dismissed -> {
                                // idk
                            }
                        }
                    }

                }
            } else {
                scope.launch {
                    val result = snackBarHostState
                        .showSnackbar(
                            message = "Incorrect :(",

                        )

                }

            }
            currentText = ""


            }){
            Text("Submit Answer")
        }
        SnackbarHost(hostState = snackBarHostState)


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CamilleChristieQuizAppTheme {
        Greeting("Android")
    }
}