package dev.number6.slack.dagger;

import dev.number6.slack.adaptor.OkHttpAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slack.port.SecretsPort;
import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import okhttp3.OkHttpClient;

@Module(includes = {AWSSecretsModule.class})
public class HttpModule {

    @Provides
    public HttpPort providesHttpPort(Call.Factory client, SecretsPort secrets) {
        return new OkHttpAdaptor(client, secrets);
    }

    @Provides
    public Call.Factory callFactory() {
        return new OkHttpClient();
    }
}
