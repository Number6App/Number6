package dev.number6.slackreader.dagger;

import dev.number6.db.port.DatabaseConfigurationPort;
import com.amazonaws.services.dynamodbv2.AbstractAmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

@Module
public class FakeDynamoDbMapperModule {

    @Provides
    DynamoDBMapper providesDbMapper(DatabaseConfigurationPort dbConfig, FakeAmazonDynamoDB client) {

        return new DynamoDBMapper(client) {

            @Override
            public <T> void save(T object, DynamoDBSaveExpression saveExpression, DynamoDBMapperConfig config) {
                client.save(object);
            }
        };
    }

    @Provides
    @Singleton
    FakeAmazonDynamoDB providesAmazonDynamoDB() {
        return new FakeAmazonDynamoDB();
    }

    public class FakeAmazonDynamoDB extends AbstractAmazonDynamoDB {

        private final Collection<Object> savedObjects = new ArrayList<>();

        public Collection<Object> getSavedObjects() {
            return savedObjects;
        }

        void save(Object o) {
            savedObjects.add(o);
        }
    }
}