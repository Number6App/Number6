package dev.number6.slackposter.dagger

import dagger.Component
import dev.number6.slack.dagger.HttpModule
import dev.number6.slackposter.SlackService

@Component(modules = [SlackPortModule::class, SlackPosterConfigurationModule::class, HttpModule::class])
interface SlackPosterComponent {
    fun handler(): SlackService
}