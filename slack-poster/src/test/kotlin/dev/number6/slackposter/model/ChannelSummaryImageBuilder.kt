package dev.number6.slackposter.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import dev.number6.slackposter.SlackPosterRDG
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.generators.characters.CharacterSetFilter
import uk.org.fyodor.range.Range
import java.time.LocalDate
import java.util.*

class ChannelSummaryImageBuilder private constructor() {
    var version = RDG.integer(8).next()
    var channelName = RDG.string(10, CharacterSetFilter.LettersOnly).next()
    var comprehensionDate = LocalDate.now().toString()
    var sentimentTotals = RDG.map(RDG.string(10), SlackPosterRDG.integerAttributeValues(10), Range.closed(5, 20)).next()
    var sentimentScoreTotals = RDG.map(RDG.string(10), SlackPosterRDG.doubleAttributeValues(10.0), Range.closed(5, 20)).next()
    var entityTotals = RDG.map(RDG.string(10, CharacterSetFilter.LettersAndDigits),
            SlackPosterRDG.mapAttributeValues(RDG.string(20, CharacterSetFilter.LettersAndDigits), SlackPosterRDG.integerAttributeValues(10), RDG.integer(20).next()), Range.closed(5, 20)).next()
    var keyPhrasesTotals = RDG.map(RDG.string(10), SlackPosterRDG.integerAttributeValues(10), Range.closed(5, 20)).next()
    fun version(v: Int): ChannelSummaryImageBuilder {
        version = v
        return this
    }

    fun sentimentScoreTotals(sentimentScoreTotals: Map<String, AttributeValue?>): ChannelSummaryImageBuilder {
        this.sentimentScoreTotals = sentimentScoreTotals
        return this
    }

    fun sentimentTotals(sentimentTotals: Map<String, AttributeValue?>): ChannelSummaryImageBuilder {
        this.sentimentTotals = sentimentTotals
        return this
    }

    fun entityTotals(entityTotals: Map<String, AttributeValue?>): ChannelSummaryImageBuilder {
        this.entityTotals = entityTotals
        return this
    }

    fun keyPhrasesTotals(keyPhrasesTotals: Map<String, AttributeValue?>): ChannelSummaryImageBuilder {
        this.keyPhrasesTotals = keyPhrasesTotals
        return this
    }

    fun build(): ChannelSummaryImage {
        val vals: MutableMap<String, AttributeValue> = HashMap()
        vals[ChannelSummaryImage.CHANNEL_NAME_KEY] = AttributeValue(channelName)
        vals[ChannelSummaryImage.COMPREHENSION_DATE_KEY] = AttributeValue(comprehensionDate)
        vals[ChannelSummaryImage.VERSION_KEY] = AttributeValue().withN(version.toString())
        vals[ChannelSummaryImage.SENTIMENT_TOTALS_KEY] = AttributeValue().withM(sentimentTotals)
        vals[ChannelSummaryImage.SENTIMENT_SCORE_TOTALS] = AttributeValue().withM(sentimentScoreTotals)
        vals[ChannelSummaryImage.ENTITY_TOTALS_KEY] = AttributeValue().withM(entityTotals)
        vals[ChannelSummaryImage.KEYPHRASES_TOTALS_KEY] = AttributeValue().withM(keyPhrasesTotals)
        return ChannelSummaryImage(vals)
    }

    companion object {
        fun builder(): ChannelSummaryImageBuilder {
            return ChannelSummaryImageBuilder()
        }

        fun finalImage(): ChannelSummaryImageBuilder {
            return ChannelSummaryImageBuilder().version(ChannelSummaryImage.FINAL_UPDATE_MINIMUM_VERSION)
        }

        fun notFinalImage(): ChannelSummaryImageBuilder {
            return ChannelSummaryImageBuilder().version(ChannelSummaryImage.FINAL_UPDATE_MINIMUM_VERSION - 1)
        }
    }
}