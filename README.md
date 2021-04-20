## Cloudformation sample
<br/>

### **Command to create DynamoDB Table**

#### From the directory where you have the file dynamodb-table.json issue the following command
`aws cloudformation create-stack --stack-name student-table-creation --template-body file://dynamodb-table.json`

<br/>

### **Command to drop the DynamoDB Table**
`aws cloudformation delete-stack --stack-name student-table-creation`

<br/>
Note: Make sure the User with which you execute the above commands has the following permission attached


```json 
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "cloudformation:CreateStack",
                "cloudformation:DeleteStack",
                "cloudformation:UpdateStack"
            ],
            "Resource": "arn:aws:cloudformation:us-east-1:1234035085247:stack/student-table-creation/*"
        }
    ]
}
```