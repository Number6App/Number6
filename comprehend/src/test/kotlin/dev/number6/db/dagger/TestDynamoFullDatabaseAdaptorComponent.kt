package dev.number6.db.dagger

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.ResponseMetadata
import com.amazonaws.regions.Region
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.model.*
import com.amazonaws.services.dynamodbv2.waiters.AmazonDynamoDBWaiters
import dagger.Component
import dagger.Module
import dagger.Provides
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.db.port.FullDatabasePort
import dev.number6.generate.ComprehendRDG
import org.junit.jupiter.api.Assertions
import java.util.*
import javax.inject.Singleton

@Component(modules = [FullDatabasePortModule::class, TestDynamoFullDatabaseAdaptorComponent.FakeDynamoDBMapperModule::class])
@Singleton
interface TestDynamoFullDatabaseAdaptorComponent {
    val database: FullDatabasePort
    val fakeDynamoDBMapper: DynamoDBMapper

    @Module
    class FakeDynamoDBMapperModule {
        @Provides
        @Singleton
        fun providesDynamoDBMapper(): DynamoDBMapper {
            return FakeDynamoDBMapper(FakeAmazonDynamoDB())
        }
    }

    class FakeDynamoDBMapper(dynamoDB: AmazonDynamoDB) : DynamoDBMapper(dynamoDB) {
        val saved: MutableList<ChannelComprehensionSummary> = ArrayList()
        override fun <T> load(clazz: Class<T>, hashKey: Any, rangeKey: Any, config: DynamoDBMapperConfig): T {
            return clazz.cast(ComprehendRDG.channelComprehensionSummary().next())
        }

        override fun <T> save(`object`: T, config: DynamoDBMapperConfig) {
            if (`object` is ChannelComprehensionSummary) {
                save(`object` as ChannelComprehensionSummary)
            } else {
                Assertions.fail<Any>("expecting summary but got $`object`")
            }
        }

        private fun save(summary: ChannelComprehensionSummary) {
            saved.add(summary)
        }
    }

    class FakeAmazonDynamoDB : AmazonDynamoDB {
        override fun setEndpoint(endpoint: String) {}
        override fun setRegion(region: Region) {}
        override fun batchGetItem(batchGetItemRequest: BatchGetItemRequest): BatchGetItemResult? {
            return null
        }

        override fun batchGetItem(requestItems: Map<String, KeysAndAttributes>, returnConsumedCapacity: String): BatchGetItemResult? {
            return null
        }

        override fun batchGetItem(requestItems: Map<String, KeysAndAttributes>): BatchGetItemResult? {
            return null
        }

        override fun batchWriteItem(batchWriteItemRequest: BatchWriteItemRequest): BatchWriteItemResult? {
            return null
        }

        override fun batchWriteItem(requestItems: Map<String, List<WriteRequest>>): BatchWriteItemResult? {
            return null
        }

        override fun createBackup(createBackupRequest: CreateBackupRequest): CreateBackupResult? {
            return null
        }

        override fun createGlobalTable(createGlobalTableRequest: CreateGlobalTableRequest): CreateGlobalTableResult? {
            return null
        }

        override fun createTable(createTableRequest: CreateTableRequest): CreateTableResult? {
            return null
        }

        override fun createTable(attributeDefinitions: List<AttributeDefinition>, tableName: String, keySchema: List<KeySchemaElement>, provisionedThroughput: ProvisionedThroughput): CreateTableResult? {
            return null
        }

        override fun deleteBackup(deleteBackupRequest: DeleteBackupRequest): DeleteBackupResult? {
            return null
        }

        override fun deleteItem(deleteItemRequest: DeleteItemRequest): DeleteItemResult? {
            return null
        }

        override fun deleteItem(tableName: String, key: Map<String, AttributeValue>): DeleteItemResult? {
            return null
        }

        override fun deleteItem(tableName: String, key: Map<String, AttributeValue>, returnValues: String): DeleteItemResult? {
            return null
        }

        override fun deleteTable(deleteTableRequest: DeleteTableRequest): DeleteTableResult? {
            return null
        }

        override fun deleteTable(tableName: String): DeleteTableResult? {
            return null
        }

        override fun describeBackup(describeBackupRequest: DescribeBackupRequest): DescribeBackupResult? {
            return null
        }

        override fun describeContinuousBackups(describeContinuousBackupsRequest: DescribeContinuousBackupsRequest): DescribeContinuousBackupsResult? {
            return null
        }

        override fun describeContributorInsights(describeContributorInsightsRequest: DescribeContributorInsightsRequest): DescribeContributorInsightsResult? {
            return null
        }

        override fun describeEndpoints(describeEndpointsRequest: DescribeEndpointsRequest): DescribeEndpointsResult? {
            return null
        }

        override fun describeGlobalTable(describeGlobalTableRequest: DescribeGlobalTableRequest): DescribeGlobalTableResult? {
            return null
        }

        override fun describeGlobalTableSettings(describeGlobalTableSettingsRequest: DescribeGlobalTableSettingsRequest): DescribeGlobalTableSettingsResult? {
            return null
        }

        override fun describeLimits(describeLimitsRequest: DescribeLimitsRequest): DescribeLimitsResult? {
            return null
        }

        override fun describeTable(describeTableRequest: DescribeTableRequest): DescribeTableResult? {
            return null
        }

        override fun describeTable(tableName: String): DescribeTableResult? {
            return null
        }

        override fun describeTableReplicaAutoScaling(describeTableReplicaAutoScalingRequest: DescribeTableReplicaAutoScalingRequest): DescribeTableReplicaAutoScalingResult? {
            return null
        }

        override fun describeTimeToLive(describeTimeToLiveRequest: DescribeTimeToLiveRequest): DescribeTimeToLiveResult? {
            return null
        }

        override fun getItem(getItemRequest: GetItemRequest): GetItemResult? {
            return null
        }

        override fun getItem(tableName: String, key: Map<String, AttributeValue>): GetItemResult? {
            return null
        }

        override fun getItem(tableName: String, key: Map<String, AttributeValue>, consistentRead: Boolean): GetItemResult? {
            return null
        }

        override fun listBackups(listBackupsRequest: ListBackupsRequest): ListBackupsResult? {
            return null
        }

        override fun listContributorInsights(listContributorInsightsRequest: ListContributorInsightsRequest): ListContributorInsightsResult? {
            return null
        }

        override fun listGlobalTables(listGlobalTablesRequest: ListGlobalTablesRequest): ListGlobalTablesResult? {
            return null
        }

        override fun listTables(listTablesRequest: ListTablesRequest): ListTablesResult? {
            return null
        }

        override fun listTables(): ListTablesResult? {
            return null
        }

        override fun listTables(exclusiveStartTableName: String): ListTablesResult? {
            return null
        }

        override fun listTables(exclusiveStartTableName: String, limit: Int): ListTablesResult? {
            return null
        }

        override fun listTables(limit: Int): ListTablesResult? {
            return null
        }

        override fun listTagsOfResource(listTagsOfResourceRequest: ListTagsOfResourceRequest): ListTagsOfResourceResult? {
            return null
        }

        override fun putItem(putItemRequest: PutItemRequest): PutItemResult? {
            return null
        }

        override fun putItem(tableName: String, item: Map<String, AttributeValue>): PutItemResult? {
            return null
        }

        override fun putItem(tableName: String, item: Map<String, AttributeValue>, returnValues: String): PutItemResult? {
            return null
        }

        override fun query(queryRequest: QueryRequest): QueryResult? {
            return null
        }

        override fun restoreTableFromBackup(restoreTableFromBackupRequest: RestoreTableFromBackupRequest): RestoreTableFromBackupResult? {
            return null
        }

        override fun restoreTableToPointInTime(restoreTableToPointInTimeRequest: RestoreTableToPointInTimeRequest): RestoreTableToPointInTimeResult? {
            return null
        }

        override fun scan(scanRequest: ScanRequest): ScanResult? {
            return null
        }

        override fun scan(tableName: String, attributesToGet: List<String>): ScanResult? {
            return null
        }

        override fun scan(tableName: String, scanFilter: Map<String, Condition>): ScanResult? {
            return null
        }

        override fun scan(tableName: String, attributesToGet: List<String>, scanFilter: Map<String, Condition>): ScanResult? {
            return null
        }

        override fun tagResource(tagResourceRequest: TagResourceRequest): TagResourceResult? {
            return null
        }

        override fun transactGetItems(transactGetItemsRequest: TransactGetItemsRequest): TransactGetItemsResult? {
            return null
        }

        override fun transactWriteItems(transactWriteItemsRequest: TransactWriteItemsRequest): TransactWriteItemsResult? {
            return null
        }

        override fun untagResource(untagResourceRequest: UntagResourceRequest): UntagResourceResult? {
            return null
        }

        override fun updateContinuousBackups(updateContinuousBackupsRequest: UpdateContinuousBackupsRequest): UpdateContinuousBackupsResult? {
            return null
        }

        override fun updateContributorInsights(updateContributorInsightsRequest: UpdateContributorInsightsRequest): UpdateContributorInsightsResult? {
            return null
        }

        override fun updateGlobalTable(updateGlobalTableRequest: UpdateGlobalTableRequest): UpdateGlobalTableResult? {
            return null
        }

        override fun updateGlobalTableSettings(updateGlobalTableSettingsRequest: UpdateGlobalTableSettingsRequest): UpdateGlobalTableSettingsResult? {
            return null
        }

        override fun updateItem(updateItemRequest: UpdateItemRequest): UpdateItemResult? {
            return null
        }

        override fun updateItem(tableName: String, key: Map<String, AttributeValue>, attributeUpdates: Map<String, AttributeValueUpdate>): UpdateItemResult? {
            return null
        }

        override fun updateItem(tableName: String, key: Map<String, AttributeValue>, attributeUpdates: Map<String, AttributeValueUpdate>, returnValues: String): UpdateItemResult? {
            return null
        }

        override fun updateTable(updateTableRequest: UpdateTableRequest): UpdateTableResult? {
            return null
        }

        override fun updateTable(tableName: String, provisionedThroughput: ProvisionedThroughput): UpdateTableResult? {
            return null
        }

        override fun updateTableReplicaAutoScaling(updateTableReplicaAutoScalingRequest: UpdateTableReplicaAutoScalingRequest): UpdateTableReplicaAutoScalingResult? {
            return null
        }

        override fun updateTimeToLive(updateTimeToLiveRequest: UpdateTimeToLiveRequest): UpdateTimeToLiveResult? {
            return null
        }

        override fun shutdown() {}
        override fun getCachedResponseMetadata(request: AmazonWebServiceRequest): ResponseMetadata? {
            return null
        }

        override fun waiters(): AmazonDynamoDBWaiters? {
            return null
        }
    }
}