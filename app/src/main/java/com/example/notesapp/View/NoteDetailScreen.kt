package com.example.notesapp.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.Model.Note
import com.example.notesapp.ViewModel.NoteViewModel

@Composable
fun NoteDetailScreen(navController: NavController, viewModel: NoteViewModel, noteId: Int?) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var content by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(noteId) {
        noteId?.let {
            val note = viewModel.getNoteById(it)
            title = TextFieldValue(note?.title ?: "")
            content = TextFieldValue(note?.content ?: "")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Nền xám nhạt
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Nút quay lại
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF333333))
        }

        // Tiêu đề
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            textStyle = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF333333),
                fontSize = 24.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (title.text.isEmpty()) {
                        Text(
                            text = "Tiêu đề",
                            color = Color.Gray,
                            fontSize = 24.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nội dung
        BasicTextField(
            value = content,
            onValueChange = { content = it },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF333333),
                fontSize = 18.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (content.text.isEmpty()) {
                        Text(
                            text = "Nhập nội dung",
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Hàng chứa nút Save & Delete
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Nút Save
            IconButton(onClick = {
                val note = Note(id = noteId ?: 0, title.text, content.text, System.currentTimeMillis())
                if (noteId == null) viewModel.insert(note) else viewModel.update(note)
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save", tint = Color(0xFF4CAF50))
            }

            // Nút Delete (chỉ hiển thị khi chỉnh sửa)
            if (noteId != null) {
                IconButton(onClick = {
                    viewModel.delete(Note(noteId, title.text, content.text, System.currentTimeMillis()))
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFF44336))
                }
            }
        }
    }
}
