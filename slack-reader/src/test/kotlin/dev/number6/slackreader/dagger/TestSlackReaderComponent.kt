package dev.number6.slackreader.dagger

import dagger.Component
import dev.number6.db.dagger.DatabaseConfigurationPortModule
import dev.number6.db.dagger.DatabasePortModule
import dev.number6.slack.port.HttpPort
import dev.number6.slackreader.dagger.FakeAmazonSnsModule.FakeAmazonSns
import dev.number6.slackreader.port.SlackPort
import java.time.Clock
import javax.inject.Singleton

@Component(modules = [FakeHttpModule::class,  //        FakeAWSSecretsManagerModule.class,
    FakeDynamoDbMapperModule::class,
    DatabasePortModule::class,
    DatabaseConfigurationPortModule::class,
    SlackServiceModule::class,
    RecordingSlackPortModule::class,
    FakeAmazonSnsModule::class,
    FakeConfigurationPortModule::class,
    FakeClockModule::class])
@Singleton
interface TestSlackReaderComponent : SlackReaderComponent {
    fun getFakeHttpAdaptor(): HttpPort
    fun getFakeAmazonDynamoClient(): FakeDynamoDbMapperModule.FakeAmazonDynamoDB
    fun getFakeAmazonSns(): FakeAmazonSns
    fun getClock(): Clock
    fun getSlackPort(): SlackPort
}