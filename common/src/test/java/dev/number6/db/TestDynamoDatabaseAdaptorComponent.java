package dev.number6.db;

import dev.number6.db.dagger.DatabasePortModule;
import dev.number6.db.model.ChannelComprehensionSummary;
import dev.number6.db.port.DatabasePort;
import dev.number6.generate.CommonRDG;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.waiters.AmazonDynamoDBWaiters;
import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

@Component(modules = {
        DatabasePortModule.class,
        TestDynamoDatabaseAdaptorComponent.FakeDynamoDBMapperModule.class
})
@Singleton
public interface TestDynamoDatabaseAdaptorComponent {

    DatabasePort getDatabase();

    DynamoDBMapper getFakeDynamoDBMapper();

    @Module
    class FakeDynamoDBMapperModule {

        @Provides
        @Singleton
        public DynamoDBMapper providesDynamoDBMapper() {
            return new FakeDynamoDBMapper(new FakeAmazonDynamoDB());
        }
    }

    class FakeDynamoDBMapper extends DynamoDBMapper {

        private List<ChannelComprehensionSummary> saved = new ArrayList<>();

        public FakeDynamoDBMapper(AmazonDynamoDB dynamoDB) {
            super(dynamoDB);
        }

        @Override
        public <T> T load(Class<T> clazz, Object hashKey, Object rangeKey, DynamoDBMapperConfig config) {
            return clazz.cast(CommonRDG.channelComprehensionSummary().next());
        }

        @Override
        public <T> void save(T object, DynamoDBMapperConfig config) {
            if (object instanceof ChannelComprehensionSummary) {
                save((ChannelComprehensionSummary) object);
            } else {
                fail("expecting summary but got " + object);
            }
        }

        private void save(ChannelComprehensionSummary summary) {
            saved.add(summary);
        }

        public List<ChannelComprehensionSummary> getSaved() {
            return saved;
        }
    }

    class FakeAmazonDynamoDB implements AmazonDynamoDB {

        @Override
        public void setEndpoint(String endpoint) {

        }

        @Override
        public void setRegion(Region region) {

        }

        @Override
        public BatchGetItemResult batchGetItem(BatchGetItemRequest batchGetItemRequest) {
            return null;
        }

        @Override
        public BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> requestItems, String returnConsumedCapacity) {
            return null;
        }

        @Override
        public BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> requestItems) {
            return null;
        }

        @Override
        public BatchWriteItemResult batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) {
            return null;
        }

        @Override
        public BatchWriteItemResult batchWriteItem(Map<String, List<WriteRequest>> requestItems) {
            return null;
        }

        @Override
        public CreateBackupResult createBackup(CreateBackupRequest createBackupRequest) {
            return null;
        }

        @Override
        public CreateGlobalTableResult createGlobalTable(CreateGlobalTableRequest createGlobalTableRequest) {
            return null;
        }

        @Override
        public CreateTableResult createTable(CreateTableRequest createTableRequest) {
            return null;
        }

        @Override
        public CreateTableResult createTable(List<AttributeDefinition> attributeDefinitions, String tableName, List<KeySchemaElement> keySchema, ProvisionedThroughput provisionedThroughput) {
            return null;
        }

        @Override
        public DeleteBackupResult deleteBackup(DeleteBackupRequest deleteBackupRequest) {
            return null;
        }

        @Override
        public DeleteItemResult deleteItem(DeleteItemRequest deleteItemRequest) {
            return null;
        }

        @Override
        public DeleteItemResult deleteItem(String tableName, Map<String, AttributeValue> key) {
            return null;
        }

        @Override
        public DeleteItemResult deleteItem(String tableName, Map<String, AttributeValue> key, String returnValues) {
            return null;
        }

        @Override
        public DeleteTableResult deleteTable(DeleteTableRequest deleteTableRequest) {
            return null;
        }

        @Override
        public DeleteTableResult deleteTable(String tableName) {
            return null;
        }

        @Override
        public DescribeBackupResult describeBackup(DescribeBackupRequest describeBackupRequest) {
            return null;
        }

        @Override
        public DescribeContinuousBackupsResult describeContinuousBackups(DescribeContinuousBackupsRequest describeContinuousBackupsRequest) {
            return null;
        }

        @Override
        public DescribeContributorInsightsResult describeContributorInsights(DescribeContributorInsightsRequest describeContributorInsightsRequest) {
            return null;
        }

        @Override
        public DescribeEndpointsResult describeEndpoints(DescribeEndpointsRequest describeEndpointsRequest) {
            return null;
        }

        @Override
        public DescribeGlobalTableResult describeGlobalTable(DescribeGlobalTableRequest describeGlobalTableRequest) {
            return null;
        }

        @Override
        public DescribeGlobalTableSettingsResult describeGlobalTableSettings(DescribeGlobalTableSettingsRequest describeGlobalTableSettingsRequest) {
            return null;
        }

        @Override
        public DescribeLimitsResult describeLimits(DescribeLimitsRequest describeLimitsRequest) {
            return null;
        }

        @Override
        public DescribeTableResult describeTable(DescribeTableRequest describeTableRequest) {
            return null;
        }

        @Override
        public DescribeTableResult describeTable(String tableName) {
            return null;
        }

        @Override
        public DescribeTableReplicaAutoScalingResult describeTableReplicaAutoScaling(DescribeTableReplicaAutoScalingRequest describeTableReplicaAutoScalingRequest) {
            return null;
        }

        @Override
        public DescribeTimeToLiveResult describeTimeToLive(DescribeTimeToLiveRequest describeTimeToLiveRequest) {
            return null;
        }

        @Override
        public GetItemResult getItem(GetItemRequest getItemRequest) {
            return null;
        }

        @Override
        public GetItemResult getItem(String tableName, Map<String, AttributeValue> key) {
            return null;
        }

        @Override
        public GetItemResult getItem(String tableName, Map<String, AttributeValue> key, Boolean consistentRead) {
            return null;
        }

        @Override
        public ListBackupsResult listBackups(ListBackupsRequest listBackupsRequest) {
            return null;
        }

        @Override
        public ListContributorInsightsResult listContributorInsights(ListContributorInsightsRequest listContributorInsightsRequest) {
            return null;
        }

        @Override
        public ListGlobalTablesResult listGlobalTables(ListGlobalTablesRequest listGlobalTablesRequest) {
            return null;
        }

        @Override
        public ListTablesResult listTables(ListTablesRequest listTablesRequest) {
            return null;
        }

        @Override
        public ListTablesResult listTables() {
            return null;
        }

        @Override
        public ListTablesResult listTables(String exclusiveStartTableName) {
            return null;
        }

        @Override
        public ListTablesResult listTables(String exclusiveStartTableName, Integer limit) {
            return null;
        }

        @Override
        public ListTablesResult listTables(Integer limit) {
            return null;
        }

        @Override
        public ListTagsOfResourceResult listTagsOfResource(ListTagsOfResourceRequest listTagsOfResourceRequest) {
            return null;
        }

        @Override
        public PutItemResult putItem(PutItemRequest putItemRequest) {
            return null;
        }

        @Override
        public PutItemResult putItem(String tableName, Map<String, AttributeValue> item) {
            return null;
        }

        @Override
        public PutItemResult putItem(String tableName, Map<String, AttributeValue> item, String returnValues) {
            return null;
        }

        @Override
        public QueryResult query(QueryRequest queryRequest) {
            return null;
        }

        @Override
        public RestoreTableFromBackupResult restoreTableFromBackup(RestoreTableFromBackupRequest restoreTableFromBackupRequest) {
            return null;
        }

        @Override
        public RestoreTableToPointInTimeResult restoreTableToPointInTime(RestoreTableToPointInTimeRequest restoreTableToPointInTimeRequest) {
            return null;
        }

        @Override
        public ScanResult scan(ScanRequest scanRequest) {
            return null;
        }

        @Override
        public ScanResult scan(String tableName, List<String> attributesToGet) {
            return null;
        }

        @Override
        public ScanResult scan(String tableName, Map<String, Condition> scanFilter) {
            return null;
        }

        @Override
        public ScanResult scan(String tableName, List<String> attributesToGet, Map<String, Condition> scanFilter) {
            return null;
        }

        @Override
        public TagResourceResult tagResource(TagResourceRequest tagResourceRequest) {
            return null;
        }

        @Override
        public TransactGetItemsResult transactGetItems(TransactGetItemsRequest transactGetItemsRequest) {
            return null;
        }

        @Override
        public TransactWriteItemsResult transactWriteItems(TransactWriteItemsRequest transactWriteItemsRequest) {
            return null;
        }

        @Override
        public UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest) {
            return null;
        }

        @Override
        public UpdateContinuousBackupsResult updateContinuousBackups(UpdateContinuousBackupsRequest updateContinuousBackupsRequest) {
            return null;
        }

        @Override
        public UpdateContributorInsightsResult updateContributorInsights(UpdateContributorInsightsRequest updateContributorInsightsRequest) {
            return null;
        }

        @Override
        public UpdateGlobalTableResult updateGlobalTable(UpdateGlobalTableRequest updateGlobalTableRequest) {
            return null;
        }

        @Override
        public UpdateGlobalTableSettingsResult updateGlobalTableSettings(UpdateGlobalTableSettingsRequest updateGlobalTableSettingsRequest) {
            return null;
        }

        @Override
        public UpdateItemResult updateItem(UpdateItemRequest updateItemRequest) {
            return null;
        }

        @Override
        public UpdateItemResult updateItem(String tableName, Map<String, AttributeValue> key, Map<String, AttributeValueUpdate> attributeUpdates) {
            return null;
        }

        @Override
        public UpdateItemResult updateItem(String tableName, Map<String, AttributeValue> key, Map<String, AttributeValueUpdate> attributeUpdates, String returnValues) {
            return null;
        }

        @Override
        public UpdateTableResult updateTable(UpdateTableRequest updateTableRequest) {
            return null;
        }

        @Override
        public UpdateTableResult updateTable(String tableName, ProvisionedThroughput provisionedThroughput) {
            return null;
        }

        @Override
        public UpdateTableReplicaAutoScalingResult updateTableReplicaAutoScaling(UpdateTableReplicaAutoScalingRequest updateTableReplicaAutoScalingRequest) {
            return null;
        }

        @Override
        public UpdateTimeToLiveResult updateTimeToLive(UpdateTimeToLiveRequest updateTimeToLiveRequest) {
            return null;
        }

        @Override
        public void shutdown() {

        }

        @Override
        public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
            return null;
        }

        @Override
        public AmazonDynamoDBWaiters waiters() {
            return null;
        }
    }
}
