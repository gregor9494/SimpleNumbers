package com.gg.simplenumbers.domain.numbers


class AddNewNumberUseCase(private val numbersListRepository: NumbersListRepository) {
    fun addNewNumber(number: Int) = numbersListRepository.addNewNumber(number)
}