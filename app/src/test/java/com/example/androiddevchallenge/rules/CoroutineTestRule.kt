package com.example.androiddevchallenge.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class CoroutineTestRule : TestRule {
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(testDispatcher)

    override fun apply(base: Statement?, description: Description?) =
        object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testDispatcher)

                try {
                    base?.evaluate()
                } finally {
                    Dispatchers.resetMain()
                    testDispatcher.cleanupTestCoroutines()
                }
            }
        }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        scope.runBlockingTest { block() }
}