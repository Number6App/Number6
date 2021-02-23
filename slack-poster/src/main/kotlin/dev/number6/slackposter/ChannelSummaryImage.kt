package dev.number6.slackposter

import com.amazonaws.services.dynamodbv2.model.AttributeValue

class ChannelSummaryImage(private val vals: Map<String, AttributeValue>) {
    fun hasFinalUpdate(): Boolean {
        return valNamed(VERSION_KEY).n.toInt() >= FINAL_UPDATE_MINIMUM_VERSION
    }

    val sentimentScoreTotals: Map<String, Double> by lazy {
        valNamed(SENTIMENT_SCORE_TOTALS).m
                .mapKeys { e -> e.key }
                .mapValues { e -> e.value.n.toDouble() }
    }

    val sentimentTotals: Map<String, Int> by lazy {
        valNamed(SENTIMENT_TOTALS_KEY).m
                .mapKeys { e -> e.key }
                .mapValues { e -> e.value.n.toInt() }
    }

    val keyPhrasesTotals: Map<String, Int> by lazy {
        valNamed(KEYPHRASES_TOTALS_KEY).m
                .mapKeys { e -> e.key }
                .mapValues { e -> e.value.n.toInt() }
    }

    val channelName: String by lazy { valNamed(CHANNEL_NAME_KEY).s }

    val comprehensionDate: String by lazy { valNamed(COMPREHENSION_DATE_KEY).s }

    val entityTotals: Map<String, Map<String, Int>> by lazy {
        val entityTotals = valNamed(ENTITY_TOTALS_KEY).m

        entityTotals.mapKeys { e -> e.key }
                .mapValues { e -> e.value.m.mapKeys { e1 -> e1.key }.mapValues { e1 -> e1.value.n.toInt() } }
    }

    private fun valNamed(key: String): AttributeValue {
        return vals[key] ?: error("Cannot find $key in $vals")
    }

    override fun toString(): String {
        return "ChannelSummaryImage{" +
                "vals=" + vals +
                '}'
    }

    companion object {
        const val FINAL_UPDATE_MINIMUM_VERSION = 4
        const val CHANNEL_NAME_KEY = "channelName"
        const val COMPREHENSION_DATE_KEY = "comprehensionDate"
        const val VERSION_KEY = "version"
        const val SENTIMENT_TOTALS_KEY = "sentimentTotals"
        const val ENTITY_TOTALS_KEY = "entityTotals"
        const val KEYPHRASES_TOTALS_KEY = "keyPhrasesTotals"
        const val SENTIMENT_SCORE_TOTALS = "sentimentScoreTotals"
    }
}