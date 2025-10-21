package com.example.news.main.ui.articleList

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.news.main.utils.AppTextStyles

@Composable
fun ChipsMenu(
    textStyles: AppTextStyles,
    onCategoryChange: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val categories = listOf("World", "Top", "Russia", "Politics", "Mobile", "RACL")
    var selectedCategory by rememberSaveable { mutableStateOf("World") }

    Log.d("ChipsMenu","recompostion")
    Log.d("ChipsMenu",selectedCategory)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        categories.forEach { category ->
            NewsChip(
                title = category,
                textStyles = textStyles,
                isSelected = selectedCategory == category,
                onSelectedChange = { selected ->
                    if (selected) {
                        selectedCategory = category
                    }
                },
                onCategoryChange = onCategoryChange
            )
        }

    }
}

@Composable
fun NewsChip(
    title: String = "Filter",
    isSelected: Boolean = false,
    textStyles: AppTextStyles,
    onSelectedChange: (Boolean) -> Unit,
    onCategoryChange: (String) -> Unit
) {
    SuggestionChip(
        shape = RoundedCornerShape(16.dp),
        border = SuggestionChipDefaults.suggestionChipBorder(false),
        onClick = {
            onSelectedChange(!isSelected)
            onCategoryChange(title)
        },
        label = {
            Text(
                text = title,
                style = textStyles.h1Medium,
                color = if (isSelected) Color.White else Color.Gray
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (isSelected) Color.Black else Color.LightGray
        )
    )


}
