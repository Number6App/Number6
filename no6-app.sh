
read -r -p 'AWS Credentials profile: ' PROFILE
read -r -p 'AWS Region: ' REGION
read -r -p 'S3 Bucket name: ' S3_BUCKET

echo "creating stack Number6 App..."

rm -rf deploy/
mvn clean package shade:shade
mkdir deploy
mkdir deploy/sentiment
mkdir deploy/reader
mkdir deploy/poster
mkdir deploy/entity
mkdir deploy/phrases
mv slack-reader/target/slack-reader-1.0-SNAPSHOT.jar ./deploy/reader
mv msg-sentiment/target/msg-sentiment-1.0-SNAPSHOT.jar ./deploy/sentiment
mv msg-entity/target/msg-entity-1.0-SNAPSHOT.jar ./deploy/entity
mv slack-poster/target/slack-poster-1.0-SNAPSHOT.jar ./deploy/poster
mv msg-key-phrases/target/msg-key-phrases-1.0-SNAPSHOT.jar ./deploy/phrases
cp No6ApplicationTemplate.yml ./deploy
cp No6AppTemplateConfig-test.json ./deploy
cp No6AppTemplateConfig-prod.json ./deploy
cd ./deploy/reader || exit
unzip slack-reader-1.0-SNAPSHOT.jar
rm slack-reader-1.0-SNAPSHOT.jar
cd ../sentiment || exit
unzip msg-sentiment-1.0-SNAPSHOT.jar
rm msg-sentiment-1.0-SNAPSHOT.jar
cd ../entity || exit
unzip msg-entity-1.0-SNAPSHOT.jar
rm msg-entity-1.0-SNAPSHOT.jar
cd ../poster || exit
unzip slack-poster-1.0-SNAPSHOT.jar
rm slack-poster-1.0-SNAPSHOT.jar
cd ../phrases || exit
unzip msg-key-phrases-1.0-SNAPSHOT.jar
rm msg-key-phrases-1.0-SNAPSHOT.jar
cd ..
aws cloudformation package --template No6ApplicationTemplate.yml --s3-bucket $S3_BUCKET --output-template template-export.yml --profile $PROFILE --region $REGION