package com.example.news.main.ui.articleList

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.news.main.NewsMainViewModel
import com.example.news.main.utils.AppTextStyles

@Composable
fun ChipsMenu(
    viewModel: NewsMainViewModel = hiltViewModel(),
    textStyles: AppTextStyles,
    onCategoryChange: (String) -> Unit
) {
    val scrollState =  rememberScrollState()
    val categories = listOf("World", "Top", "Russia", "Politics", "Mobile", "RACL")
    val selectedCategory = viewModel.selectedCategory.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        categories.forEach { category ->
            NewsChip(
                onClick = {
                    if (selectedCategory.value != category) {
                        viewModel.changeCategory(category)
                        onCategoryChange(category)
                    }
                },
                title = category,
                textStyles = textStyles,
                isSelected = selectedCategory.value == category
            )
        }

    }
}

@Composable
fun NewsChip(
    title: String = "Filter",
    isSelected: Boolean,
    textStyles: AppTextStyles,
    onClick: () -> Unit
) {
    SuggestionChip(
        shape = RoundedCornerShape(16.dp),
        border = SuggestionChipDefaults.suggestionChipBorder(false),
        onClick = onClick,
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
