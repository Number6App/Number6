
read -r -p 'test acct: ' test_acct
read -r -p 'prod acct: ' prod_acct
read -r -p 'AWS test profile: ' test_profile
read -r -p 'AWS prod profile: ' prod_profile
read -r -p 'AWS region: ' region
read -r -s -p 'Slack OAuth token: ' slack
read -r -s -p 'GitHub access token: ' github
read -r -p 'GitHub Owner: ' owner
read -r -p 'GitHub Repo Name: ' repo
read -r -p 'GitHub Branch Name: ' branch

aws cloudformation create-stack --profile $test_profile --region $region --stack-name Number6-CMK --template-body file://cmk.yml --parameters ParameterKey=TestAccount,ParameterValue=$test_acct ParameterKey=ProductionAccount,ParameterValue=$prod_acct
echo "creating stack Number6-CMK..."
aws cloudformation wait stack-create-complete --profile $test_profile --region $region --stack-name Number6-CMK
echo "stack Number6-CMK completed"

aws cloudformation create-stack --profile $test_profile --region $region --stack-name Number6-DevRoles --template-body file://dev-roles.yml --parameters ParameterKey=GitHubToken,ParameterValue=$github ParameterKey=SlackOAuthToken,ParameterValue=$slack --capabilities CAPABILITY_NAMED_IAM
echo "creating stack Number6-DevRoles..."
aws cloudformation wait stack-create-complete --profile $test_profile --region $region --stack-name Number6-DevRoles
echo "stack Number6-DevRoles completed"

read -r -p 'Name of the S3 Bucket created by the CMK Stack: ' s3
read -r -p 'ARN of the CMK in KMS: ' cmk

aws cloudformation create-stack --profile $prod_profile --region $region --stack-name Number6-ProdRoles --template-body file://prod-roles.yml --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=S3Bucket,ParameterValue=$s3 ParameterKey=CMKARN,ParameterValue=$cmk ParameterKey=TestAccount,ParameterValue=$test_acct ParameterKey=SlackOAuthToken,ParameterValue=$slack
echo "creating stack Number6-ProdRoles..."
aws cloudformation wait stack-create-complete --profile $prod_profile --region $region --stack-name Number6-ProdRoles
echo "stack Number6-ProdRoles completed"

aws cloudformation create-stack --profile $test_profile --region $region --stack-name Number6-Pipeline --template-body file://pipeline.yml --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=ProductionAccount,ParameterValue=$prod_acct ParameterKey=GitHubOwner,ParameterValue=$owner ParameterKey=GitHubRepoName,ParameterValue=$repo ParameterKey=GitHubBranch,ParameterValue=$branch
echo "creating stack Number6-Pipeline..."
aws cloudformation wait stack-create-complete --profile $test_profile --region $region --stack-name Number6-Pipeline
echo "stack Number6-Pipeline completed"