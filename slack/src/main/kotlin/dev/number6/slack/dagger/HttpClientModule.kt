package dev.number6.slack.dagger

import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient

@Module
internal class HttpClientModule {

    @Provides
    fun httpClient(): Call.Factory {
        return OkHttpClient()
    }
}