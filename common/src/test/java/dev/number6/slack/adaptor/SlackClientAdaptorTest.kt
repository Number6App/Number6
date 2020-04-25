package dev.number6.slack.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.CallResponse
import dev.number6.slack.port.HttpPort
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.util.*

@ExtendWith(MockitoExtension::class)
@Disabled("replace Mockito")
internal class SlackClientAdaptorTest {
    @Mock
    var logger: LambdaLogger? = null

    @Mock
    var http: HttpPort? = null

    @InjectMocks
    var testee: SlackClientAdaptor? = null
    private val gson = Gson()
    private var testResponseObject: TestResponseObject? = null
    private val testObjectGenerator = TestObjectGenerator()

    @BeforeEach
    fun setup() {
        testResponseObject = testObjectGenerator.next()
    }

    @Test
    fun requestWithResponseObject() {
//            Mockito.`when`(http!![ArgumentMatchers.any(), ArgumentMatchers.any()]).thenReturn(CallResponse(gson.toJson(testResponseObject)))
        val response = testee!!.getSlackResponse(SlackClientAdaptor.CHANNEL_LIST_URL, TestResponseObject::class.java, logger!!)
        Assertions.assertThat(response.isPresent).isTrue()
        Assertions.assertThat(response.get()).isEqualTo(testResponseObject)
    }

    @Test
    fun postWithBodyNoResponse() {
        Mockito.`when`(http!!.post(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(CallResponse(gson.toJson(testResponseObject)))
        val response = testee!!.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, logger!!, "BODY")
        Assertions.assertThat(response.isPresent).isFalse()
    }

    @Test
    fun postWithBodyAndResponse() {
        Mockito.`when`(http!!.post(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(CallResponse(gson.toJson(testResponseObject)))
        val response = testee!!.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, "BODY", TestResponseObject::class.java, logger!!)
        Assertions.assertThat(response.isPresent).isTrue()
        Assertions.assertThat(response.get()).isEqualTo(testResponseObject)
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