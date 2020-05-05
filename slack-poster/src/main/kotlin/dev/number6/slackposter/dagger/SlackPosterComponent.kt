package dev.number6.slackposter.dagger

import dagger.Component
import dev.number6.slack.dagger.SlackClientLiveBindingsModule
import dev.number6.slackposter.SlackService

@Component(modules = [SlackPosterPortModule::class,
    SlackPosterConfigurationModule::class,
    SlackClientLiveBindingsModule::class])
interface SlackPosterComponent {
    fun handler(): SlackService
}