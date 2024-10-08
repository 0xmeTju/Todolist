# Simple To-Do App

A minimalistic To-Do list application built using **Jetpack Compose** for Android. The app allows users to add and manage tasks efficiently with a clean and user-friendly interface.

## Features

- **Add Tasks**: Enter tasks in the input field and click "Add" to save them.
- **Task List**: All tasks are displayed in a scrollable list, with the most recently added tasks appearing at the top.
- **Delete Tasks**: Mark tasks as completed by clicking the ✔ button, which removes the task from the list.
- **Data Persistence**: Tasks are saved locally using **SharedPreferences**, so even after closing the app, your tasks remain available when you reopen it.

## Screens

### Welcome Screen
- The initial screen with the app’s title and an "Enter" button to proceed to the task management screen.

### Task Management Screen
- A list of saved tasks is shown.
- A text field to input new tasks.
- Buttons to add tasks and mark them as completed.

## Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or a physical device.

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **SharedPreferences** (for task storage)
