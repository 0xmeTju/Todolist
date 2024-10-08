package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.utils.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load saved tasks from SharedPreferences
        val savedTasks = loadTasksFromPreferences(this)

        setContent {
            // Pass the saved tasks to MyApp and provide a lambda to save tasks
            MyApp(savedTasks, onSaveTasks = { tasks ->
                saveTasksToPreferences(this, tasks)}
            )
        }
    }
}

@Composable
fun MyApp(savedTasks :List<String>, onSaveTasks: (List<String>) -> Unit) {
    // State variables
    var isWelcomeScreen by remember { mutableStateOf(true) }
    var taskInput by remember { mutableStateOf("") }
    // Load saved tasks or initialize an empty list of tasks
    val tasks = remember { mutableStateListOf<String>().apply { addAll(savedTasks) } }

    // Determine which screen to display
    if (isWelcomeScreen) {
        WelcomeScreen { isWelcomeScreen = false }
    } else {
        TaskScreen(taskInput,
            onInputChange = { taskInput = it }, // Update the task input
            onAddTask = {                       // Add the task to the front of list and save
                if (taskInput.isNotBlank()) {
                    tasks.add(0,taskInput)
                    taskInput = ""
                    onSaveTasks(tasks)
                }
            },
            tasks = tasks,
            onTaskDelete = { task ->            // Remove the task from the list and save
                tasks.remove(task)
                onSaveTasks(tasks)
            }
        )
    }
}

@Composable
fun WelcomeScreen(onEnter: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffA1D6B2)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(                              // Icon for the app
                painter = painterResource(R.drawable.checkmark_logo2),
                contentDescription = null
            )
            Text(                               // Title
                text = "Simple To-Do App",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = onEnter,   // Button to enter the app
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffCEDF9F),
                    contentColor = Color.Black),) {
                Text("Enter")}
        }

        Text(
            text = "Developed by 0xmeTju",
            fontSize = 10.sp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TaskScreen(
    taskInput: String,                          // Input for the task
    onInputChange: (String) -> Unit,            // Lambda to update the task input
    onAddTask: () -> Unit,                      // Lambda to add the task
    tasks: List<String>,                        // List of tasks
    onTaskDelete: (String) -> Unit              // Lambda to delete a task
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xffA1D6B2))) {
        Text(                                   // Title of screen
            "Your saved tasks",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        TextField(                              // Input field for the tasks
            value = taskInput,
            onValueChange = onInputChange,
            label = { Text("Enter your task here")},
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedButton(onClick = onAddTask,     // Button to add the task
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffCEDF9F),
                contentColor = Color.Black),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()) {
            Text("Add", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {                            // List of tasks in scrollable view
            items(tasks) { task ->              // For each task call TaskRow
                TaskRow(task = task, onTaskDelete = onTaskDelete)
            }
        }
    }
}

@Composable
fun TaskRow(task: String, onTaskDelete: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffCEDF9F)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = task, modifier = Modifier   // Task text
            .weight(1f)
            .padding(4.dp), fontSize = 16.sp)
        Button(onClick = { onTaskDelete(task) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffFF8A8A),
                contentColor = Color.White)) {
            Text("✔", fontSize = 16.sp)
        }
    }
}