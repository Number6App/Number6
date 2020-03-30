package dev.number6.slackreader.dagger;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import dagger.Module;
import dagger.Provides;

@Module
public class AmazonSnsModule {

    @Provides
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.defaultClient();
    }
}
