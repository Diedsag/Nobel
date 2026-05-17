package com.example.nobel.presentation.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nobel.domain.model.Prize
import com.example.nobel.presentation.viewmodel.PrizesListUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelListScreen(
    state: PrizesListUiState,
    onItemClick: (Int) -> Unit,
    onFilter: (Int, String) -> Unit
) {
    val categories = listOf("physics", "chemistry", "literature", "peace", "medicine", "economics")
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var year by remember { mutableStateOf(2010) }
    Scaffold(
        topBar = {
            Column() {
                Text(
                    "Нобелевские премии",
                    fontSize = 30.sp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = year.toString(),
                        onValueChange = {it -> year = it.toInt()},
                        modifier = Modifier.width(100.dp)
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedCategory = item
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Button(
                    {onFilter(year, selectedCategory)}
                ){
                    Text("Применить фильтр")
                }
            }
        }
    ) {padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (state) {
                is PrizesListUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is PrizesListUiState.Success -> {
                    val prizes = (state as PrizesListUiState.Success).prizes
                    LazyColumn {
                        items(
                            prizes,
                            {it -> prizes.indexOf(it)}
                        ) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp, horizontal = 12.dp),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                        .clickable { onItemClick(prizes.indexOf(item)) }) {
                                    Text(
                                        (item.year.toString() + " - " + item.category), fontSize = 30.sp,
                                    )
                                    Text(
                                        item.fullNames.joinToString { it }, fontSize = 30.sp,
                                    )
                                    Text(
                                        "Motivation: " + item.motivations.joinToString { it }.take(100), fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                is PrizesListUiState.Error -> {
                    Text(
                        text = (state as PrizesListUiState.Error).message
                    )
                }

                is PrizesListUiState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Премий не найдено")
                    }
                }
            }

        }
    }
}