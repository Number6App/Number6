package dev.number6.comprehend.adaptor;

import dev.number6.comprehend.AwsComprehendClient;
import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.message.ChannelMessages;
import com.amazonaws.services.comprehend.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AwsComprehensionAdaptor implements ComprehensionPort {

    private final AwsComprehendClient awsComprehendClient;

    public AwsComprehensionAdaptor(AwsComprehendClient awsComprehendClient) {
        this.awsComprehendClient = awsComprehendClient;
    }

    @Override
    public PresentableEntityResults getEntitiesForSlackMessages(ChannelMessages channelMessages) {
        Collection<DetectEntitiesResult> entityResults = channelMessages.getMessages().stream()
                .filter(Objects::nonNull)
                .filter(m -> m.length() > 0)
                .map(awsComprehendClient::getMessageEntities)
                .collect(Collectors.toList());

        Map<String, Map<String, Long>> entityCollection = entityResults.stream()
                .filter(e -> e.getEntities() != null && e.getEntities().size() > 0)
                .flatMap(e -> e.getEntities().stream())
                .collect(Collectors.groupingBy(Entity::getType, HashMap::new, Collectors.groupingBy(Entity::getText, Collectors.counting())));

        return new PresentableEntityResults(channelMessages.getComprehensionDate(), entityCollection, channelMessages.getChannelName());
    }

    @Override
    public PresentableSentimentResults getSentimentForSlackMessages(ChannelMessages channelMessages) {

        Collection<DetectSentimentResult> sentimentResults = channelMessages.getMessages().stream()
                .filter(Objects::nonNull)
                .filter(m -> m.length() > 0)
                .map(awsComprehendClient::getMessageSentiment)
                .collect(Collectors.toList());

        return new PresentableSentimentResults(channelMessages.getComprehensionDate(),
                sentimentResults,
                channelMessages.getChannelName());
    }

    @Override
    public PresentableKeyPhrasesResults getKeyPhrasesForSlackMessages(ChannelMessages channelMessages) {
        Collection<DetectKeyPhrasesResult> keyPhrasesResults = channelMessages.getMessages().stream()
                .filter(Objects::nonNull)
                .filter(m -> m.length() > 0)
                .map(awsComprehendClient::getMessageKeyPhrases)
                .collect(Collectors.toList());

        Map<String, Long> keyPhrases = keyPhrasesResults.stream()
                .filter(r -> r.getKeyPhrases() != null && r.getKeyPhrases().size() > 0)
                .flatMap(r -> r.getKeyPhrases().stream())
                .collect(Collectors.groupingBy(KeyPhrase::getText, Collectors.counting()));

        return new PresentableKeyPhrasesResults(channelMessages.getComprehensionDate(), keyPhrases, channelMessages.getChannelName());
    }
}
