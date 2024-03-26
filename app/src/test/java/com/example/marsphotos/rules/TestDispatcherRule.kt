package com.example.marsphotos.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    // TestWatcher class enables you to take actions on different execution phases of a test.
    // Test trai qua nhieu giai doan, dung TestWatcher de dieu khien 1 trong cac giai doan test do
    // Su dung cac dispatcher khac nhau bang tham so ham constructor.
    // Tham so nay can 1 gia tri mac dinh la 1 doi tuong UnconfinedTestDispatcher
    // Tham so nay chi ra rang cac task ko duoc thuc thi theo trat tu lon xon
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }

}