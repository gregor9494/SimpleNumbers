package com.gg.simplenumbers.domain.numbers

import com.gg.simplenumbers.UnitTest
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldBe
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock

class AddNewNumberUseCaseTest : UnitTest() {

    lateinit var addNewNumberUseCase: AddNewNumberUseCase

    @Mock
    lateinit var numbersListRepository: NumbersListRepository

    @Test
    fun `test value when number does not exist`() {
        whenever(numbersListRepository.addNewNumber(1)).thenReturn(false)
        addNewNumberUseCase = AddNewNumberUseCase(
            numbersListRepository = numbersListRepository
        )

        val addNewNumber = addNewNumberUseCase.addNewNumber(1)

        addNewNumber shouldBe false
    }

    @Test
    fun `test value when number exist`() {
        whenever(numbersListRepository.addNewNumber(1)).thenReturn(true)
        addNewNumberUseCase = AddNewNumberUseCase(
            numbersListRepository = numbersListRepository
        )

        val addNewNumber = addNewNumberUseCase.addNewNumber(1)

        addNewNumber shouldBe true
    }
}