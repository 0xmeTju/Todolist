package com.example.todolist.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Save tasks to SharedPreferences using Json (Gson)
fun saveTasksToPreferences(context: Context, tasks: List<String>) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Convert the list of tasks to JSON using Gson
    val gson = Gson()
    val jsonTasks = gson.toJson(tasks)
    editor.putString("tasks", jsonTasks)
    editor.apply()  // Save the changes
}

// Load tasks from SharedPreferences using Json (Gson)
fun loadTasksFromPreferences(context: Context): List<String> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
    val jsonTasks = sharedPreferences.getString("tasks", null)

    return if (jsonTasks != null) {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        gson.fromJson(jsonTasks, type)  // Convert JSON to a list of tasks and return
    } else {
        emptyList()  // Return an empty list if there are no tasks
    }
}
