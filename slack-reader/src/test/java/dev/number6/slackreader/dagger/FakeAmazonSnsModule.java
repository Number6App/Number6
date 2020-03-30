package dev.number6.slackreader.dagger;

import com.amazonaws.services.sns.AbstractAmazonSNS;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

@Module
public class FakeAmazonSnsModule {

    @Provides
    public AmazonSNS providesAmazonSNS(FakeAmazonSns sns) {

        return sns;
    }

    @Provides
    @Singleton
    FakeAmazonSns providesFakeAmazonSns(){
        return new FakeAmazonSns();
    }

    public class FakeAmazonSns extends AbstractAmazonSNS {

        final Collection<String> published = new ArrayList<>();

        public Collection<String> getPublishedMessages() {
            return published;
        }

        @Override
        public PublishResult publish(PublishRequest request) {
            published.add(request.getMessage());
            return new PublishResult();
        }
    }
}
