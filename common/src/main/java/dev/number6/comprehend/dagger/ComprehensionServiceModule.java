package dev.number6.comprehend.dagger;

import dev.number6.comprehend.AwsComprehendClient;
import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.adaptor.AwsComprehensionAdaptor;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import dagger.Module;
import dagger.Provides;

@Module
public class ComprehensionServiceModule {

    @Provides
    public ComprehensionPort providesComprehensionPort(AwsComprehendClient client) {
        return new AwsComprehensionAdaptor(client);
    }

    @Provides
    public AwsComprehendClient providesComprehensionClient(AmazonComprehend amazonComprehend) {
        return new AwsComprehendClient(amazonComprehend);
    }

    @Provides
    public AmazonComprehend providesAmazonComprehend() {
        return AmazonComprehendClientBuilder.defaultClient();
    }
}
