package dev.number6.slackreader.dagger

import com.amazonaws.services.sns.AbstractAmazonSNS
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.amazonaws.services.sns.model.PublishResult
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeAmazonSnsModule {
    @Provides
    fun providesAmazonSNS(sns: FakeAmazonSns): AmazonSNS {
        return sns
    }

    @Provides
    @Singleton
    fun providesFakeAmazonSns(): FakeAmazonSns {
        return FakeAmazonSns()
    }

    inner class FakeAmazonSns : AbstractAmazonSNS() {
        val published: MutableCollection<String> = mutableListOf()
        fun getPublishedMessages(): MutableCollection<String> {
            return published
        }

        override fun publish(request: PublishRequest): PublishResult {
            published.add(request.message)
            return PublishResult()
        }
    }
}