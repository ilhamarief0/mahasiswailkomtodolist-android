package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.ui.theme.FirstAppTheme
import java.util.*

data class Student(val id: String, val name: String, val nim: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val students = remember { mutableStateListOf(
                            Student(UUID.randomUUID().toString(), "Ilham Arief", "F1G122025"),
                        ) }

                        StudentCRUD(students = students)
                    }
                }
            }
        }
    }
}

@Composable
fun StudentCRUD(students: MutableList<Student>) {
    var newName by remember { mutableStateOf("") }
    var newNim by remember { mutableStateOf("") }
    var selectedStudent by remember { mutableStateOf<Student?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Data Mahasiswa Ilkom 022", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        Column(modifier = Modifier.padding(8.dp)) {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = newNim,
                onValueChange = { newNim = it },
                label = { Text("NIM") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = {
                        if (selectedStudent == null) {
                            students.add(Student(UUID.randomUUID().toString(), newName, newNim))
                        } else {
                            selectedStudent?.let {
                                students.replace(it, Student(it.id, newName, newNim))
                            }
                        }
                        newName = ""
                        newNim = ""
                        selectedStudent = null
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (selectedStudent == null) "Add" else "Update")
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (selectedStudent != null) {
                    Button(
                        onClick = {
                            newName = ""
                            newNim = ""
                            selectedStudent = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(students) { student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(student.name, fontWeight = FontWeight.Bold)
                        Text(student.nim)
                    }
                    Row {
                        IconButton(onClick = {
                            newName = student.name
                            newNim = student.nim
                            selectedStudent = student
                        }) {
                            Text("Edit", color = Color.Blue)
                        }
                        IconButton(onClick = {
                            students.remove(student)
                        }) {
                            Text("Delete", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

fun MutableList<Student>.replace(old: Student, new: Student) {
    val index = indexOf(old)
    if (index >= 0) {
        set(index, new)
    }
}

@Preview(showBackground = true)
@Composable
fun StudentCRUDPreview() {
    FirstAppTheme {
        val students = remember { mutableStateListOf(
            Student(UUID.randomUUID().toString(), "Ilham Arief", "F1G122025"),
        ) }
        StudentCRUD(students)
    }
}
