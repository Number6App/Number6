package dev.number6.slackreader.dagger;

import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slackreader.generate.SlackReaderRDG;
import dev.number6.slackreader.model.Channel;
import dev.number6.slackreader.model.ChannelHistoryResponse;
import dev.number6.slackreader.model.ChannelsListResponse;
import dev.number6.slackreader.model.Message;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.*;
import org.apache.http.entity.ContentType;

import javax.inject.Singleton;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Module
public class FakeCallFactoryModule {

//    @Provides
//    public HttpPort providesHttpPort(Call.Factory client) {
//        return new OkHttpAdaptor(client);
//    }

    @Provides
    @Singleton
    public FakeCallData providesFakeCallData() {
        return new FakeCallData();
    }

    @Provides
    public Call.Factory providesFakeCallFactory(FakeCallData fakeCallData) {
        return new FakeCallFactory(fakeCallData);
    }

    enum Datasource {
        ChannelList {
            @Override
            boolean canHandle(Request request) {
                return request.url().toString().equals(SlackClientAdaptor.CHANNEL_LIST_URL);
            }

            @Override
            String getData(FakeCallData fakeCallData, Request request) {
                return fakeCallData.getChannelListResponse();
            }
        }, ChannelMessages {
            @Override
            boolean canHandle(Request request) {
                return request.url().toString().startsWith("https://slack.com/api/channels.history");
            }

            @Override
            String getData(FakeCallData fakeCallData, Request request) {
                return fakeCallData.getChannelHistoryResponse(request.url().queryParameter("channel"));
            }
        };

        static Datasource getDatasourceForRequest(Request request) {
            for (Datasource datasource : Datasource.values()) {
                if (datasource.canHandle(request)) {
                    return datasource;
                }
            }
            throw new UnsupportedOperationException("Cannot find datasource for request " + request.url().toString());
        }

        abstract boolean canHandle(Request request);

        abstract String getData(FakeCallData fakeCallData, Request request);
    }

    public class FakeCallData {

        private final ChannelsListResponse channelListResponse;
        private final Map<Channel, ChannelHistoryResponse> channelMessages;
        private final Gson gson = new Gson();

        FakeCallData() {
            this.channelListResponse = SlackReaderRDG.channelsListResponse().next();
            this.channelMessages = channelListResponse.getChannels().stream()
                    .collect(Collectors.toMap(Function.identity(), c -> SlackReaderRDG.channelHistoryResponse().next()));
        }

        String getChannelListResponse() {
            return gson.toJson(channelListResponse);
        }

        String getChannelHistoryResponse(String channelId) {
            return gson.toJson(channelMessages.entrySet().stream()
                    .filter(e -> e.getKey().getId().equals(channelId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unable to find history for channel id " + channelId))
                    .getValue());
        }

        String getFakeData(Request request) {
            return Datasource.getDatasourceForRequest(request).getData(this, request);
        }

        public Iterable<? extends String> getChannelNames() {
            return channelListResponse.getChannels().stream().map(Channel::getName).collect(Collectors.toList());
        }

        public Iterable<? extends String> getNessagesForChannelName(String channelName) {
            return channelMessages.entrySet().stream()
                    .filter(e -> e.getKey().getName().equals(channelName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unable to find history for channel name " + channelName))
                    .getValue()
                    .getMessages()
                    .stream()
                    .map(Message::getText)
                    .collect(Collectors.toList());
        }
    }

    class FakeCallFactory implements Call.Factory {
        private final FakeCallData fakeCallData;

        FakeCallFactory(FakeCallData fakeCallData) {

            this.fakeCallData = fakeCallData;
        }

        @Override
        public Call newCall(Request request) {

            return new Call() {

                @Override
                public Request request() {
                    throw new UnsupportedOperationException("request not supported in fake Call");
                }

                @Override
                public Response execute() {
                    ResponseBody responseBody = ResponseBody.create(MediaType.parse(ContentType.APPLICATION_JSON.toString()), fakeCallData.getFakeData(request));
                    return new Response.Builder()
                            .protocol(Protocol.HTTP_1_1)
                            .request(new Request.Builder().url("http://localhost:8080").build())
                            .code(200)
                            .message("message")
                            .body(responseBody)
                            .build();
                }

                @Override
                public void enqueue(Callback responseCallback) {
                    throw new UnsupportedOperationException("enqueue not supported in fake Call");
                }

                @Override
                public void cancel() {
                    throw new UnsupportedOperationException("cancel not supported in fake Call");
                }

                @Override
                public boolean isExecuted() {
                    throw new UnsupportedOperationException("isExecuted not supported in fake Call");
                }

                @Override
                public boolean isCanceled() {
                    throw new UnsupportedOperationException("isCanceled not supported in fake Call");
                }

                @Override
                public Call clone() {
                    throw new UnsupportedOperationException("clone not supported in fake Call");
                }
            };
        }
    }
}
