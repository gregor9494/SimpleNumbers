package com.gg.simplenumbers.domain

sealed class LoadMoreResult {
    object Success : LoadMoreResult()
    object NoMore : LoadMoreResult()
    object Error : LoadMoreResult()
}