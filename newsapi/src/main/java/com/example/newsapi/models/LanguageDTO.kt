package com.example.newsapi.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class LanguageDTO {
    @SerializedName("ar")
    AR,

    @SerializedName("de")
    DE,

    @SerializedName("en")
    EN,

    @SerializedName("es")
    ES,

    @SerializedName("fr")
    FR,

    @SerializedName("he")
    HE,

    @SerializedName("it")
    IT,

    @SerializedName("nl")
    NL,

    @SerializedName("no")
    NO,

    @SerializedName("pt")
    PT,

    @SerializedName("ru")
    RU,

    @SerializedName("sv")
    SV,

    @SerializedName("ud")
    UD,

    @SerializedName("zh")
    ZH
}