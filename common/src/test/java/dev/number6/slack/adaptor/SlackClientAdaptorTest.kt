package dev.number6.slack.adaptor

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.CallResponse
import dev.number6.slack.port.HttpPort
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.util.*

@ExtendWith(MockKExtension::class)
internal class SlackClientAdaptorTest {
    var logger: LambdaLogger = mockk(relaxUnitFun = true)
    var http: HttpPort = mockk()

    val testee = SlackClientAdaptor(http)

    private val gson = Gson()
    private var testResponseObject: TestResponseObject? = null
    private val testObjectGenerator = TestObjectGenerator()

    @BeforeEach
    fun setup() {
        testResponseObject = testObjectGenerator.next()
    }

    @Test
    fun requestWithResponseObject() {
        every { http.get(any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val response = testee.getSlackResponse(SlackClientAdaptor.CHANNEL_LIST_URL, TestResponseObject::class.java, logger!!)
        assertThat(response.isPresent).isTrue()
        assertThat(response.get()).isEqualTo(testResponseObject)
    }

    @Test
    fun postWithBodyNoResponse() {
        every { http.post(any(), any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val response = testee.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, logger, "BODY")
        assertThat(response.isPresent).isFalse()
    }

    @Test
    fun postWithBodyAndResponse() {
        every { http.post(any(), any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val response = testee.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, "BODY", TestResponseObject::class.java, logger!!)
        assertThat(response.isPresent).isTrue()
        assertThat(response.get()).isEqualTo(testResponseObject)
    }

    class TestResponseObject internal constructor(private val field1: Int, private val field2: String, private val field3: Double) {
        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o == null || javaClass != o.javaClass) return false
            val that = o as TestResponseObject
            return field1 == that.field1 &&
                    field2 == that.field2 &&
                    field3 == that.field3
        }

        override fun hashCode(): Int {
            return Objects.hash(field1, field2, field3)
        }

    }

    internal class TestObjectGenerator : Generator<TestResponseObject> {
        override fun next(): TestResponseObject {
            return TestResponseObject(RDG.integer(999).next(),
                    RDG.string(30).next(),
                    RDG.doubleVal(9999.0).next())
        }
    }
}