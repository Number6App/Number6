package dev.number6.slack.dagger

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.generate.SlackRDG
import dev.number6.slack.model.Channel
import dev.number6.slack.model.ChannelHistoryResponse
import dev.number6.slack.model.ChannelsListResponse
import dev.number6.slack.model.JoinChannelResponse
import okhttp3.*
import org.apache.http.entity.ContentType
import javax.inject.Singleton

@Module
class FakeHttpClientModule {

    @Provides
    @Singleton
    fun providesFakeCallData(): FakeCallData {
        return FakeCallData()
    }

    @Provides
    fun providesFakeCallFactory(fakeCallData: FakeCallData): Call.Factory {
        return FakeCallFactory(fakeCallData)
    }

    @Suppress("unused")
    internal enum class Datasource {
        ChannelList {
            override fun canHandle(request: Request): Boolean {
                return request.url().toString() == SlackClientAdaptor.CHANNEL_LIST_URL
            }

            override fun getData(fakeCallData: FakeCallData, request: Request): String {
                return fakeCallData.getChannelListResponse()
            }
        },
        JoinChannel {
            override fun canHandle(request: Request): Boolean {
                return request.url().toString().startsWith("https://slack.com/api/conversations.join")
            }

            override fun getData(fakeCallData: FakeCallData, request: Request): String {
                return fakeCallData.getJoinedChannelResponse()
            }
        },
        ChannelMessages {
            override fun canHandle(request: Request): Boolean {
                return request.url().toString().startsWith("https://slack.com/api/channels.history")
            }

            override fun getData(fakeCallData: FakeCallData, request: Request): String {
                return fakeCallData.getChannelHistoryResponse(request.url().queryParameter("channel"))
            }
        },
        PostMessage {
            override fun canHandle(request: Request): Boolean {
                return request.url().toString().startsWith("https://slack.com/api/chat.postMessage")
            }

            override fun getData(fakeCallData: FakeCallData, request: Request): String {
                return ""
            }
        };

        abstract fun canHandle(request: Request): Boolean
        abstract fun getData(fakeCallData: FakeCallData, request: Request): String

        companion object {
            fun getDatasourceForRequest(request: Request): Datasource {
                for (datasource in values()) {
                    if (datasource.canHandle(request)) {
                        return datasource
                    }
                }
                throw UnsupportedOperationException("Cannot find datasource for request " + request.url().toString())
            }
        }
    }

    inner class FakeCallData internal constructor() {
        private val channelListResponse: ChannelsListResponse = SlackRDG.channelsListResponse().next()
        private val channelMessages: Map<Channel, ChannelHistoryResponse>
        private val gson = Gson()

        fun getChannelListResponse(): String {
            return gson.toJson(channelListResponse)
        }

        fun getJoinedChannelResponse(): String {
            return gson.toJson(JoinChannelResponse(true))
        }

        fun getChannelHistoryResponse(channelId: String?): String {
            return gson.toJson(channelMessages.entries.stream()
                    .filter { e -> e.key.id == channelId }
                    .findFirst()
                    .orElseThrow { RuntimeException("Unable to find history for channel id $channelId") }
                    .value)
        }

        fun getFakeData(request: Request): String {
            return Datasource.getDatasourceForRequest(request).getData(this, request)
        }

        init {
            channelMessages = channelListResponse.channels
                    .associateWith { _ -> SlackRDG.channelHistoryResponse(true).next() }
        }
    }

    internal inner class FakeCallFactory(private val fakeCallData: FakeCallData) : Call.Factory {
        override fun newCall(request: Request): Call {
            return object : Call {
                override fun request(): Request {
                    throw UnsupportedOperationException("request not supported in fake Call")
                }

                override fun execute(): Response {
                    val responseBody = ResponseBody.create(MediaType.parse(ContentType.APPLICATION_JSON.toString()), fakeCallData.getFakeData(request))
                    return Response.Builder()
                            .protocol(Protocol.HTTP_1_1)
                            .request(Request.Builder().url("http://localhost:8080").build())
                            .code(200)
                            .message("message")
                            .body(responseBody)
                            .build()
                }

                override fun enqueue(responseCallback: Callback?) {
                    throw UnsupportedOperationException("enqueue not supported in fake Call")
                }

                override fun cancel() {
                    throw UnsupportedOperationException("cancel not supported in fake Call")
                }

                override fun isExecuted(): Boolean {
                    throw UnsupportedOperationException("isExecuted not supported in fake Call")
                }

                override fun isCanceled(): Boolean {
                    throw UnsupportedOperationException("isCanceled not supported in fake Call")
                }

                override fun clone(): Call {
                    throw UnsupportedOperationException("clone not supported in fake Call")
                }
            }
        }
    }
}