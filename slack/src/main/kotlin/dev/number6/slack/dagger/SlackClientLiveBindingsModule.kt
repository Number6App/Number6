package dev.number6.slack.dagger

import dagger.Module

@Module(includes = [SlackClientModule::class,
    HttpClientModule::class,
    AWSSecretsModule::class])
class SlackClientLiveBindingsModule