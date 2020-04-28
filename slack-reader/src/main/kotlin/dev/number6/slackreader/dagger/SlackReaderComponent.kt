package dev.number6.slackreader.dagger

import dagger.Component
import dev.number6.db.dagger.DatabaseConfigurationPortModule
import dev.number6.db.dagger.DatabasePortModule
import dev.number6.db.dagger.DynamoDBMapperModule
import dev.number6.slack.dagger.SlackClientLiveBindingsModule
import dev.number6.slack.dagger.SlackClientModule
import dev.number6.slackreader.SlackReader

@Component(modules = [SlackServiceModule::class,
    SlackReaderPortModule::class,
    SlackClientLiveBindingsModule::class,
    DatabasePortModule::class,
    DatabaseConfigurationPortModule::class,
    DynamoDBMapperModule::class,
    AmazonSnsModule::class,
    ConfigurationPortModule::class,
    ClockModule::class])
interface SlackReaderComponent {
    fun handler(): SlackReader
}