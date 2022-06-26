package com.tasks.discogsconsumer.db.repo

interface Repo

//TODO: wrap responses and results into Results objects
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}