package dev.number6.slack.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.OkHttpAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slack.port.SecretsPort
import okhttp3.Call
import okhttp3.OkHttpClient

@Module(includes = [AWSSecretsModule::class])
class HttpModule {
    @Provides
    fun providesHttpPort(client: Call.Factory, secrets: SecretsPort): HttpPort {
        return OkHttpAdaptor(client, secrets)
    }

    @Provides
    fun callFactory(): Call.Factory {
        return OkHttpClient()
    }
}