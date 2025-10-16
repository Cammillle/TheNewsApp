package com.example.news.main

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import javax.inject.Inject

class AppTextStyles @Inject constructor(
    private val sfProFontFamily: FontFamily
) {

    // Article Title SF Pro Bold 18
    val articleTitle = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )

    // Action Sheet SF Pro Regular 18
    val actionSheet = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )

    // Image Caption SF Pro Regular Italic 12
    val imageCaption = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp
    )

    // H1 SF Pro Bold 15
    val h1Bold = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    )

    // H1 SF Pro Medium 15
    val h1Medium = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    )

    // H2 SF Pro Medium 14
    val h2Bold = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )

    // H3 SF Pro Bold 13
    val h3Bold = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    )

    // H3 SF Pro Medium 13
    val h3Medium = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    )

    // P1 SF Pro Regular 15
    val p1Regular = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    )
}