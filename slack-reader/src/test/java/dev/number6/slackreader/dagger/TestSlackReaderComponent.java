package dev.number6.slackreader.dagger;

import dev.number6.db.dagger.DatabaseConfigurationPortModule;
import dev.number6.db.dagger.DatabasePortModule;
import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.port.SlackPort;
import dagger.Component;

import javax.inject.Singleton;
import java.time.Clock;

@Component(modules = {FakeHttpModule.class,
//        FakeAWSSecretsManagerModule.class,
        FakeDynamoDbMapperModule.class,
        DatabasePortModule.class,
        DatabaseConfigurationPortModule.class,
        SlackServiceModule.class,
        RecordingSlackPortModule.class,
        FakeAmazonSnsModule.class,
        FakeConfigurationPortModule.class,
        FakeClockModule.class})
@Singleton
public interface TestSlackReaderComponent extends SlackReaderComponent {

    HttpPort getFakeHttpAdaptor();

    FakeDynamoDbMapperModule.FakeAmazonDynamoDB getFakeAmazonDynamoClient();

    FakeAmazonSnsModule.FakeAmazonSns getFakeAmazonSns();

    Clock getClock();

    SlackPort getSlackPort();
}
