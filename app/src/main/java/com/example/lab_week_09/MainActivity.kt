package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import androidx.compose.runtime.mutableStateListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // We call the Home composable, which now manages its own state.
                    Home()
                }
            }
        }
    }
}

// Data class for Student remains the same.
data class Student(
    var name: String
)

@Composable
fun Home() {
    // A reactive list of students. Compose will observe this for changes.
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // A reactive state holder for the text field's current value.
    val inputField = remember { mutableStateOf("") }

    // Pass the state and event handlers down to the stateless HomeContent composable.
    HomeContent(
        items = listData,
        inputValue = inputField.value,
        onInputValueChange = { newName ->
            // Update the state with the new text from the TextField.
            inputField.value = newName
        },
        onButtonClick = {
            // Add a new student if the input is not blank, then clear the field.
            if (inputField.value.isNotBlank()) {
                listData.add(Student(inputField.value))
                inputField.value = "" // Clear the input field
            }
        }
    )
}

@Composable
fun HomeContent(
    items: SnapshotStateList<Student>,
    inputValue: String, // Changed to accept a String
    onInputValueChange: (String) -> Unit, // Changed to expect a String
    onButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.enter_item))

                TextField(
                    value = inputValue, // Display the current input value
                    onValueChange = onInputValueChange, // Call the lambda on text change
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text // Changed to Text for names
                    )
                )

                Button(onClick = onButtonClick) { // Fixed: Call the onButtonClick lambda
                    Text(text = stringResource(id = R.string.button_click))
                }
            }
        }

        // Fixed: Use the 'items' parameter passed into this function
        items(items) { student ->
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display the name property of the Student object
                Text(text = student.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    // Fixed: Call Home() without any parameters.
    LAB_WEEK_09Theme {
        Home()
    }
}
