package com.chul.plannode

data class Question<T, R>(
    val case: List<Case<T, R>> = emptyList()
)