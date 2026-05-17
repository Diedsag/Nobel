package com.example.nobel.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nobel.domain.model.Prize

@Composable
fun NobelDetailScreen(
    prize: Prize,
) {
    Column (modifier = Modifier.fillMaxSize()) {
        Text(
            "Год: " + prize.year.toString(),
            fontSize = 50.sp,
            lineHeight = 50.sp,
        )
        Text(
            "Категория: " + prize.category, fontSize = 30.sp,
        )
        for (i in 0..<prize.fullNames.size){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 12.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "${i + 1}. ${prize.fullNames.get(i)}", fontSize = 30.sp,
                    )
                    Text(
                        "Доля 1/${prize.fullNames.size}", fontSize = 30.sp,
                    )
                    Text(
                        "Мотивация: " + prize.motivations.get(i), fontSize = 15.sp
                    )
                }
            }
        }
    }
}