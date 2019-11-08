package com.gojuno.composer

import com.gojuno.composer.InstrumentationTest.Status.Failed
import com.gojuno.composer.InstrumentationTest.Status.Ignored
import com.gojuno.composer.InstrumentationTest.Status.Passed
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import rx.observers.TestSubscriber
import java.util.concurrent.TimeUnit.SECONDS

class InstrumentationSpec : Spek({

    context("read output with failed test") {

        val entries by memoized { readInstrumentationOutput(fileFromJarResources<InstrumentationSpec>("instrumentation-output-failed-test.txt")) }
        val entriesSubscriber by memoized { TestSubscriber<InstrumentationEntry>() }

        perform {
            entries.subscribe(entriesSubscriber)
            entriesSubscriber.awaitTerminalEvent(30, SECONDS)
        }

        it("emits expected entries") {
            var stacktrace = """java.net.UnknownHostException: Test Exception
at com.example.test.TestClass.test1.1.invoke(TestClass.kt:245)
at com.example.test.TestClass.test1.1.invoke(TestClass.kt:44)
at com.example.test.TestClass.test1(TestClass.kt:238)
at java.lang.reflect.Method.invoke(Native Method)
at org.junit.runners.model.FrameworkMethod.1.runReflectiveCall(FrameworkMethod.java:50)
at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
at org.junit.rules.ExpectedException.ExpectedExceptionStatement.evaluate(ExpectedException.java:239)
at com.example.test.utils.LaunchAppRule.apply.1.evaluate(LaunchAppRule.kt:36)
at com.example.test.utils.RetryRule.runTest(RetryRule.kt:43)
at com.example.test.utils.RetryRule.access.runTest(RetryRule.kt:14)
at com.example.test.utils.RetryRule.apply.1.evaluate(RetryRule.kt:29)
at org.junit.rules.RunRules.evaluate(RunRules.java:20)
at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
at org.junit.runners.ParentRunner.3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner.1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access.000(ParentRunner.java:58)
at org.junit.runners.ParentRunner.2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runners.Suite.runChild(Suite.java:128)
at org.junit.runners.Suite.runChild(Suite.java:27)
at org.junit.runners.ParentRunner.3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner.1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access.000(ParentRunner.java:58)
at org.junit.runners.ParentRunner.2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
at android.support.test.internal.runner.TestExecutor.execute(TestExecutor.java:59)
at android.support.test.runner.JunoAndroidRunner.onStart(JunoAndroidRunner.kt:107)
at android.app.Instrumentation.InstrumentationThread.run(Instrumentation.java:1932)"""
            stacktrace = normalizeLinefeed(stacktrace)

            var stream = """Error in test1(com.example.test.TestClass):
java.net.UnknownHostException: Test Exception
at com.example.test.TestClass.test1.1.invoke(TestClass.kt:245)
at com.example.test.TestClass.test1.1.invoke(TestClass.kt:44)
at com.example.test.TestClass.test1(TestClass.kt:238)
at java.lang.reflect.Method.invoke(Native Method)
at org.junit.runners.model.FrameworkMethod.1.runReflectiveCall(FrameworkMethod.java:50)
at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
at org.junit.rules.ExpectedException.ExpectedExceptionStatement.evaluate(ExpectedException.java:239)
at com.example.test.utils.LaunchAppRule.apply.1.evaluate(LaunchAppRule.kt:36)
at com.example.test.utils.RetryRule.runTest(RetryRule.kt:43)
at com.example.test.utils.RetryRule.access.runTest(RetryRule.kt:14)
at com.example.test.utils.RetryRule.apply.1.evaluate(RetryRule.kt:29)
at org.junit.rules.RunRules.evaluate(RunRules.java:20)
at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
at org.junit.runners.ParentRunner.3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner.1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access.000(ParentRunner.java:58)
at org.junit.runners.ParentRunner.2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runners.Suite.runChild(Suite.java:128)
at org.junit.runners.Suite.runChild(Suite.java:27)
at org.junit.runners.ParentRunner.3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner.1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access.000(ParentRunner.java:58)
at org.junit.runners.ParentRunner.2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
at android.support.test.internal.runner.TestExecutor.execute(TestExecutor.java:59)
at android.support.test.runner.JunoAndroidRunner.onStart(JunoAndroidRunner.kt:107)
at android.app.Instrumentation.InstrumentationThread.run(Instrumentation.java:1932)"""
            stream = normalizeLinefeed(stream)

            val expectedResultMessage = normalizeLinefeed("""Time: 96.641
There was 1 failure:
1) test1(com.example.test.TestClass)
$stacktrace

FAILURES!!!
Tests run: 4,  Failures: 1""")

            // We have no control over system time in tests.
            entriesSubscriber.assertValuesWithZeroedTimestamps(listOf(
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = "com.example.test.TestClass:",
                            id = "AndroidJUnitRunner",
                            test = "test1",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = stream,
                            id = "AndroidJUnitRunner",
                            test = "test1",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = stacktrace,
                            statusCode = StatusCode.Failure,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = "",
                            id = "AndroidJUnitRunner",
                            test = "test2",
                            clazz = "com.example.test.TestClass",
                            current = 2,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = ".",
                            id = "AndroidJUnitRunner",
                            test = "test2",
                            clazz = "com.example.test.TestClass",
                            current = 2,
                            stack = "",
                            statusCode = StatusCode.Ok,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = "com.example.test.TestClass:",
                            id = "AndroidJUnitRunner",
                            test = "test3",
                            clazz = "com.example.test.TestClass",
                            current = 3,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = ".",
                            id = "AndroidJUnitRunner",
                            test = "test3",
                            clazz = "com.example.test.TestClass",
                            current = 3,
                            stack = "",
                            statusCode = StatusCode.Ok,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = "",
                            id = "AndroidJUnitRunner",
                            test = "test4",
                            clazz = "com.example.test.TestClass",
                            current = 4,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 4,
                            stream = ".",
                            id = "AndroidJUnitRunner",
                            test = "test4",
                            clazz = "com.example.test.TestClass",
                            current = 4,
                            stack = "",
                            statusCode = StatusCode.Ok,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Result(
                            message = expectedResultMessage,
                            timestampNanos = 0
                    )
            ))
        }

        it("completes stream") {
            entriesSubscriber.assertCompleted()
        }

        it("does not emit error") {
            entriesSubscriber.assertNoErrors()
        }

        context("as tests") {

            val testsSubscriber by memoized { TestSubscriber<InstrumentationTest>() }

            perform {
                entries.asTests().subscribe(testsSubscriber)
                testsSubscriber.awaitTerminalEvent(30, SECONDS)
            }

            it("emits expected tests") {
                // We have no control over system time in tests.
                var stacktrace = """java.net.UnknownHostException: Test Exception
at com.example.test.TestClass.test1.1.invoke(TestClass.kt:245)
at com.example.test.TestClass.test1.1.invoke(TestClass.kt:44)
at com.example.test.TestClass.test1(TestClass.kt:238)
at java.lang.reflect.Method.invoke(Native Method)
at org.junit.runners.model.FrameworkMethod.1.runReflectiveCall(FrameworkMethod.java:50)
at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
at org.junit.rules.ExpectedException.ExpectedExceptionStatement.evaluate(ExpectedException.java:239)
at com.example.test.utils.LaunchAppRule.apply.1.evaluate(LaunchAppRule.kt:36)
at com.example.test.utils.RetryRule.runTest(RetryRule.kt:43)
at com.example.test.utils.RetryRule.access.runTest(RetryRule.kt:14)
at com.example.test.utils.RetryRule.apply.1.evaluate(RetryRule.kt:29)
at org.junit.rules.RunRules.evaluate(RunRules.java:20)
at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
at org.junit.runners.ParentRunner.3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner.1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access.000(ParentRunner.java:58)
at org.junit.runners.ParentRunner.2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runners.Suite.runChild(Suite.java:128)
at org.junit.runners.Suite.runChild(Suite.java:27)
at org.junit.runners.ParentRunner.3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner.1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access.000(ParentRunner.java:58)
at org.junit.runners.ParentRunner.2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
at android.support.test.internal.runner.TestExecutor.execute(TestExecutor.java:59)
at android.support.test.runner.JunoAndroidRunner.onStart(JunoAndroidRunner.kt:107)
at android.app.Instrumentation.InstrumentationThread.run(Instrumentation.java:1932)"""

                stacktrace = normalizeLinefeed(stacktrace)
                testsSubscriber.assertValuesWithZeroedDurations(listOf(
                        InstrumentationTest(
                                index = 1,
                                total = 4,
                                className = "com.example.test.TestClass",
                                testName = "test1",
                                status = Failed(stacktrace = stacktrace),
                                durationNanos = 0
                        ),
                        InstrumentationTest(
                                index = 2,
                                total = 4,
                                className = "com.example.test.TestClass",
                                testName = "test2",
                                status = Passed,
                                durationNanos = 0
                        ),
                        InstrumentationTest(
                                index = 3,
                                total = 4,
                                className = "com.example.test.TestClass",
                                testName = "test3",
                                status = Passed,
                                durationNanos = 0
                        ),
                        InstrumentationTest(
                                index = 4,
                                total = 4,
                                className = "com.example.test.TestClass",
                                testName = "test4",
                                status = Passed,
                                durationNanos = 0
                        )
                ))
            }

            it("completes stream") {
                testsSubscriber.assertCompleted()
            }

            it("does not emit error") {
                testsSubscriber.assertNoErrors()
            }
        }
    }

    context("read output with 0 tests") {

        val entries by memoized { readInstrumentationOutput(fileFromJarResources<InstrumentationSpec>("instrumentation-output-0-tests.txt")) }
        val entriesSubscriber by memoized { TestSubscriber<InstrumentationEntry>() }

        perform {
            entries.subscribe(entriesSubscriber)
            entriesSubscriber.awaitTerminalEvent(30, SECONDS)
        }

        it("emits no statuses, only the final result") {
            entriesSubscriber.assertValuesWithZeroedTimestamps(listOf(
                    InstrumentationEntry.Result(
                            message = "Time: 0\n\nOK (0 tests)",
                            timestampNanos = 0
                    )
            ))
        }

        it("completes stream") {
            entriesSubscriber.assertCompleted()
        }

        it("does not emit error") {
            entriesSubscriber.assertNoErrors()
        }

        context("as tests") {

            val testsSubscriber by memoized { TestSubscriber<InstrumentationTest>() }

            perform {
                entries.asTests().subscribe(testsSubscriber)
                testsSubscriber.awaitTerminalEvent(30, SECONDS)
            }

            it("does not emit any test") {
                testsSubscriber.assertNoValues()
            }

            it("completes stream") {
                testsSubscriber.assertCompleted()
            }

            it("does not emit error") {
                testsSubscriber.assertNoErrors()
            }
        }
    }

    context("read output with ignored test") {

        val entries by memoized { readInstrumentationOutput(fileFromJarResources<InstrumentationSpec>("instrumentation-output-ignored-test.txt")) }
        val entriesSubscriber by memoized { TestSubscriber<InstrumentationEntry>() }

        perform {
            entries.subscribe(entriesSubscriber)
            entriesSubscriber.awaitTerminalEvent(30, SECONDS)
        }

        it("emits expected entries") {
            // We have no control over system time in tests.
            entriesSubscriber.assertValuesWithZeroedTimestamps(listOf(
                    InstrumentationEntry.Status(
                            numTests = 2,
                            stream = "com.example.test.TestClass:",
                            id = "AndroidJUnitRunner",
                            test = "test1",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 2,
                            stream = ".",
                            id = "AndroidJUnitRunner",
                            test = "test1",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = "",
                            statusCode = StatusCode.Ok,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 2,
                            stream = "",
                            id = "AndroidJUnitRunner",
                            test = "test2",
                            clazz = "com.example.test.TestClass",
                            current = 2,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 2,
                            stream = "",
                            id = "AndroidJUnitRunner",
                            test = "test2",
                            clazz = "com.example.test.TestClass",
                            current = 2,
                            stack = "",
                            statusCode = StatusCode.Ignored,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Result(
                            message = "Time: 10.073\n\nOK (2 tests)",
                            timestampNanos = 0
                    )
            ))
        }

        it("completes stream") {
            entriesSubscriber.assertCompleted()
        }

        it("does not emit error") {
            entriesSubscriber.assertNoErrors()
        }

        context("as tests") {

            val testsSubscriber by memoized { TestSubscriber<InstrumentationTest>() }

            perform {
                entries.asTests().subscribe(testsSubscriber)
                testsSubscriber.awaitTerminalEvent(30, SECONDS)
            }

            it("emits expected tests") {
                testsSubscriber.assertValuesWithZeroedDurations(listOf(
                        InstrumentationTest(
                                index = 1,
                                total = 2,
                                className = "com.example.test.TestClass",
                                testName = "test1",
                                status = Passed,
                                durationNanos = 0L
                        ),
                        InstrumentationTest(
                                index = 2,
                                total = 2,
                                className = "com.example.test.TestClass",
                                testName = "test2",
                                status = Ignored(),
                                durationNanos = 0L
                        )
                ))
            }
        }
    }

    context("read output containing test with assumption violation") {

        val entries by memoized { readInstrumentationOutput(fileFromJarResources<InstrumentationSpec>("instrumentation-output-assumption-violation.txt")) }
        val entriesSubscriber by memoized { TestSubscriber<InstrumentationEntry>() }

        perform {
            entries.subscribe(entriesSubscriber)
            entriesSubscriber.awaitTerminalEvent(30, SECONDS)
        }

        it("emits expected entries") {
            // We have no control over system time in tests.
            var stacktrace = """org.junit.AssumptionViolatedException: got: "foo", expected: is "bar"
at org.junit.Assume.assumeThat(Assume.java:95)
at com.example.test.TestClass.violated(TestClass.java:22)
at java.lang.reflect.Method.invoke(Native Method)
at org.junit.runners.model.FrameworkMethod${'$'}1.runReflectiveCall(FrameworkMethod.java:50)
at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
at org.junit.runners.ParentRunner${'$'}3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner${'$'}1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access${'$'}000(ParentRunner.java:58)
at org.junit.runners.ParentRunner${'$'}2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runners.Suite.runChild(Suite.java:128)
at org.junit.runners.Suite.runChild(Suite.java:27)
at org.junit.runners.ParentRunner${'$'}3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner${'$'}1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access${'$'}000(ParentRunner.java:58)
at org.junit.runners.ParentRunner${'$'}2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
at android.support.test.internal.runner.TestExecutor.execute(TestExecutor.java:58)
at android.support.test.runner.AndroidJUnitRunner.onStart(AndroidJUnitRunner.java:375)
at android.app.Instrumentation${'$'}InstrumentationThread.run(Instrumentation.java:2074)"""
            stacktrace = normalizeLinefeed(stacktrace)

            entriesSubscriber.assertValuesWithZeroedTimestamps(listOf(
                    InstrumentationEntry.Status(
                            numTests = 1,
                            stream = "com.example.test.TestClass:",
                            id = "AndroidJUnitRunner",
                            test = "violated",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Status(
                            numTests = 1,
                            stream = "com.example.test.TestClass:",
                            id = "AndroidJUnitRunner",
                            test = "violated",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = stacktrace,
                            statusCode = StatusCode.AssumptionFailure,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Result(
                            message = "Time: ٠٫٠١٥\n\nOK (1 test)",
                            timestampNanos = 0
                    )
            ))
        }

        it("completes stream") {
            entriesSubscriber.assertCompleted()
        }

        it("does not emit error") {
            entriesSubscriber.assertNoErrors()
        }

        context("as tests") {

            val testsSubscriber by memoized { TestSubscriber<InstrumentationTest>() }

            perform {
                entries.asTests().subscribe(testsSubscriber)
                testsSubscriber.awaitTerminalEvent(30, SECONDS)
            }

            it("emits expected tests") {
                var stacktrace = """org.junit.AssumptionViolatedException: got: "foo", expected: is "bar"
at org.junit.Assume.assumeThat(Assume.java:95)
at com.example.test.TestClass.violated(TestClass.java:22)
at java.lang.reflect.Method.invoke(Native Method)
at org.junit.runners.model.FrameworkMethod${'$'}1.runReflectiveCall(FrameworkMethod.java:50)
at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
at org.junit.runners.ParentRunner${'$'}3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner${'$'}1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access${'$'}000(ParentRunner.java:58)
at org.junit.runners.ParentRunner${'$'}2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runners.Suite.runChild(Suite.java:128)
at org.junit.runners.Suite.runChild(Suite.java:27)
at org.junit.runners.ParentRunner${'$'}3.run(ParentRunner.java:290)
at org.junit.runners.ParentRunner${'$'}1.schedule(ParentRunner.java:71)
at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
at org.junit.runners.ParentRunner.access${'$'}000(ParentRunner.java:58)
at org.junit.runners.ParentRunner${'$'}2.evaluate(ParentRunner.java:268)
at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
at android.support.test.internal.runner.TestExecutor.execute(TestExecutor.java:58)
at android.support.test.runner.AndroidJUnitRunner.onStart(AndroidJUnitRunner.java:375)
at android.app.Instrumentation${'$'}InstrumentationThread.run(Instrumentation.java:2074)"""
                stacktrace = normalizeLinefeed(stacktrace)
                testsSubscriber.assertValuesWithZeroedDurations(listOf(
                        InstrumentationTest(
                                index = 1,
                                total = 1,
                                className = "com.example.test.TestClass",
                                testName = "violated",
                                status = Ignored(stacktrace = stacktrace),
                                durationNanos = 0L
                        )
                ))
            }
        }
    }

    context("read output with unable to find instrumentation info error") {

        val outputFile = fileFromJarResources<InstrumentationSpec>("instrumentation-output-unable-to-find-instrumentation-info.txt")
        val entries by memoized { readInstrumentationOutput(outputFile) }
        val entriesSubscriber by memoized { TestSubscriber<InstrumentationEntry>() }

        perform {
            entries.subscribe(entriesSubscriber)
            entriesSubscriber.awaitTerminalEvent(30, SECONDS)
        }

        it("emits exception with human readable message") {
            assertThat(entriesSubscriber.onErrorEvents.first()).hasMessage(
                    "Instrumentation was unable to run tests using runner com.composer.example.ExampleAndroidJUnitRunner.\n" +
                            "Most likely you forgot to declare test runner in AndroidManifest.xml or build.gradle.\n" +
                            "Detailed log can be found in ${outputFile.path} or Logcat output.\n" +
                            "See https://github.com/gojuno/composer/issues/79 for more info."
            )
        }
    }

    context("read output with crash") {

        val outputFile = fileFromJarResources<InstrumentationSpec>("instrumentation-output-app-crash.txt")
        val entries by memoized { readInstrumentationOutput(outputFile) }
        val entriesSubscriber by memoized { TestSubscriber<InstrumentationEntry>() }

        perform {
            entries.subscribe(entriesSubscriber)
            entriesSubscriber.awaitTerminalEvent(30, SECONDS)
        }

        it("emits expected entries") {
            entriesSubscriber.assertValuesWithZeroedTimestamps(listOf(
                    InstrumentationEntry.Status(
                            numTests = 1,
                            stream = "com.example.test.TestClass:",
                            id = "AndroidJUnitRunner",
                            test = "crashTest",
                            clazz = "com.example.test.TestClass",
                            current = 1,
                            stack = "",
                            statusCode = StatusCode.Start,
                            timestampNanos = 0
                    ),
                    InstrumentationEntry.Result(
                            message = "Process crashed.",
                            timestampNanos = 0
                    )
            ))
        }

        it("completes stream") {
            entriesSubscriber.assertCompleted()
        }

        it("does not emit error") {
            entriesSubscriber.assertNoErrors()
        }

        context("as tests") {
            val testsSubscriber by memoized { TestSubscriber<InstrumentationTest>() }

            perform {
                entries.asTests().subscribe(testsSubscriber)
                testsSubscriber.awaitTerminalEvent(30, SECONDS)
            }

            it("emits expected tests") {
                testsSubscriber.assertValuesWithZeroedDurations(listOf(
                        InstrumentationTest(
                                index = 1,
                                total = 1,
                                className = "com.example.test.TestClass",
                                testName = "crashTest",
                                status = Failed(stacktrace = "Process crashed."),
                                durationNanos = 0L
                        )
                ))
            }
        }
    }
})

private fun TestSubscriber<InstrumentationTest>.assertValuesWithZeroedDurations(
        expectedValues: List<InstrumentationTest>
) = assertThat(onNextEvents.map { it.copy(durationNanos = 0) }).isEqualTo(expectedValues)

private fun TestSubscriber<InstrumentationEntry>.assertValuesWithZeroedTimestamps(
        expectedValues: List<InstrumentationEntry>
) = assertThat(onNextEvents.map { it.copy(timestampNanos = 0) }).isEqualTo(expectedValues)

private fun InstrumentationEntry.copy(timestampNanos: Long) = when (this) {
    is InstrumentationEntry.Status -> copy(timestampNanos = timestampNanos)
    is InstrumentationEntry.Result -> copy(timestampNanos = timestampNanos)
}