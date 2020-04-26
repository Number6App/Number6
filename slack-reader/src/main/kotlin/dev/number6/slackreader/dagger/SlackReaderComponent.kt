package dev.number6.slackreader.dagger

import dagger.Component
import dev.number6.db.dagger.DatabaseConfigurationPortModule
import dev.number6.db.dagger.DatabasePortModule
import dev.number6.db.dagger.DynamoDBMapperModule
import dev.number6.slack.dagger.HttpModule
import dev.number6.slackreader.SlackReader

@Component(modules = [SlackServiceModule::class, SlackPortModule::class, HttpModule::class, DatabasePortModule::class, DatabaseConfigurationPortModule::class, DynamoDBMapperModule::class, AmazonSnsModule::class, ConfigurationPortModule::class, ClockModule::class])
interface SlackReaderComponent {
    open fun handler(): SlackReader
}