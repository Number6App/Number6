package dev.number6.slack

import dagger.Module
import dagger.Provides
import okhttp3.*
import org.apache.http.entity.ContentType

@Module
class FakeCallFactoryModule {
    //    @Provides
    //    public HttpPort providesHttpport() {
    //        return request -> new CallResponse("response");
    //    }
    @Provides
    fun providesFakeCallFactory(): Call.Factory {
        return FakeCallFactory()
    }

    //    enum Datasource {
    //        ChannelList {
    //            @Override
    //            boolean canHandle(Request request) {
    //                return request.url().toString().equals(SlackClientAdaptor.CHANNEL_LIST_URL);
    //            }
    //
    //            @Override
    //            String getData(FakeCallData fakeCallData, Request request) {
    //                return fakeCallData.getChannelListResponse();
    //            }
    //        }, ChannelMessages {
    //            @Override
    //            boolean canHandle(Request request) {
    //                return request.url().toString().startsWith("https://slack.com/api/channels.history");
    //            }
    //
    //            @Override
    //            String getData(FakeCallData fakeCallData, Request request) {
    //                return fakeCallData.getChannelHistoryResponse(request.url().queryParameter("channel"));
    //            }
    //        };
    //
    //        static Datasource getDatasourceForRequest(Request request) {
    //            for (Datasource datasource : Datasource.values()) {
    //                if (datasource.canHandle(request)) {
    //                    return datasource;
    //                }
    //            }
    //            throw new UnsupportedOperationException("Cannot find datasource for request " + request.url().toString());
    //        }
    //
    //        abstract boolean canHandle(Request request);
    //
    //        abstract String getData(FakeCallData fakeCallData, Request request);
    //    }
    //    public class FakeCallData {
    //
    //        private final ChannelsListResponse channelListResponse;
    //        private final Map<Channel, ChannelHistoryResponse> channelMessages;
    //        private final Gson gson = new Gson();
    //
    //        FakeCallData() {
    //            this.channelListResponse = SlackReaderRDG.channelsListResponse().next();
    //            this.channelMessages = channelListResponse.getChannels().stream()
    //                    .collect(Collectors.toMap(Function.identity(), c -> SlackReaderRDG.channelHistoryResponse().next()));
    //        }
    //
    //        String getChannelListResponse() {
    //            return gson.toJson(channelListResponse);
    //        }
    //
    //        String getChannelHistoryResponse(String channelId) {
    //            return gson.toJson(channelMessages.entrySet().stream()
    //                    .filter(e -> e.getKey().getId().equals(channelId))
    //                    .findFirst()
    //                    .orElseThrow(() -> new RuntimeException("Unable to find history for channel id " + channelId))
    //                    .getValue());
    //        }
    //
    //        String getFakeData(Request request) {
    //            return Datasource.getDatasourceForRequest(request).getData(this, request);
    //        }
    //
    //        public Iterable<? extends String> getChannelNames() {
    //            return channelListResponse.getChannels().stream().map(Channel::getName).collect(Collectors.toList());
    //        }
    //
    //        public Iterable<? extends String> getNessagesForChannelName(String channelName) {
    //            return channelMessages.entrySet().stream()
    //                    .filter(e -> e.getKey().getName().equals(channelName))
    //                    .findFirst()
    //                    .orElseThrow(() -> new RuntimeException("Unable to find history for channel name " + channelName))
    //                    .getValue()
    //                    .getMessages()
    //                    .stream()
    //                    .map(Message::getText)
    //                    .collect(Collectors.toList());
    //        }
    //    }
    internal inner class FakeCallFactory : Call.Factory {
        override fun newCall(request: Request): Call {
            return object : Call {
                override fun request(): Request {
                    throw UnsupportedOperationException("request not supported in fake Call")
                }

                override fun execute(): Response {
                    val responseBody = ResponseBody.create(MediaType.parse(ContentType.APPLICATION_JSON.toString()),
                            "ResponseBody")
                    return Response.Builder()
                            .protocol(Protocol.HTTP_1_1)
                            .request(Request.Builder().url("http://localhost:8080").build())
                            .code(200)
                            .message("message")
                            .body(responseBody)
                            .build()
                }

                override fun enqueue(responseCallback: Callback) {
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