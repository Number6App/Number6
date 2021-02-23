package dev.number6.slack.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.OkHttpAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slack.port.SecretsPort
import okhttp3.Call

@Module
internal class HttpModule {
    @Provides
    fun providesHttpPort(client: Call.Factory, secrets: SecretsPort): HttpPort {
        return OkHttpAdaptor(client, secrets)
    }
}