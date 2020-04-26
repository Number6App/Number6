package dev.number6.slackreader.dagger

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import dagger.Module
import dagger.Provides

@Module
class AmazonSnsModule {
    @Provides
    fun amazonSNS(): AmazonSNS {
        return AmazonSNSClientBuilder.defaultClient()
    }
}