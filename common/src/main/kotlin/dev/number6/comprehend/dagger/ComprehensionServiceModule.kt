package dev.number6.comprehend.dagger

import com.amazonaws.services.comprehend.AmazonComprehend
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder
import dagger.Module
import dagger.Provides
import dev.number6.comprehend.AwsComprehendClient
import dev.number6.comprehend.adaptor.AwsComprehensionAdaptor
import dev.number6.comprehend.port.ComprehensionPort

@Module
class ComprehensionServiceModule {
    @Provides
    fun providesComprehensionPort(client: AwsComprehendClient): ComprehensionPort {
        return AwsComprehensionAdaptor(client)
    }

    @Provides
    fun providesComprehensionClient(amazonComprehend: AmazonComprehend): AwsComprehendClient {
        return AwsComprehendClient(amazonComprehend)
    }

    @Provides
    fun providesAmazonComprehend(): AmazonComprehend {
        return AmazonComprehendClientBuilder.defaultClient()
    }
}