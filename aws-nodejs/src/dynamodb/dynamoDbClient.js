let AWS = require('aws-sdk');

// Set the region
AWS.config.update({ region: 'us-east-1' });

// Create the DynamoDB service object
const dynamoDb = new AWS.DynamoDB();

//Create DocumentClient object
const docClient = new AWS.DynamoDB.DocumentClient();

/**
 * This function creates DynamoDB Table
 * @param {*} params Parameters to create DynamoDB Table
 */
let createTable = async (params) => {
    let res;
    try {
        res = await dynamoDb.createTable(params).promise();
    } catch (err) {
        console.error(`err creating table:: ${err}`);
    }
    return res;
}

/**
 * This function adds Item
 * @param {*} params Parameters to put Item into DynamoDB
 */
let putItem = async (params) => {
    try {
        await docClient.put(params).promise();
        console.log('Iteam added successfully');
    } catch (err) {
        console.error(`err adding Item:: ${err}`);
    }
}

/**
 * This function retrieves Item
 * @param {*} params Parameters to read Item from DynamoDB
 */
let readItem = async (params) => {
    let res;
    try {
        res = await docClient.get(params).promise();
        console.log(res);
    } catch (err) {
        console.error(`err reading Item:: ${err}`);
    }
    return res.Item;
}

/**
 * Method to call createTable
 */
let callCreateTable = async () => {
    const params = {
        TableName: 'student',
        KeySchema: [
            { AttributeName: "studentId", KeyType: "HASH" },  //Partition key
            { AttributeName: "age", KeyType: "RANGE" }  //Sort key
        ],
        AttributeDefinitions: [
            { AttributeName: "studentId", AttributeType: "N" },
            { AttributeName: "age", AttributeType: "N" }
        ],
        ProvisionedThroughput: {
            ReadCapacityUnits: 5,
            WriteCapacityUnits: 5
        }
    };
    try {
        let response = await createTable(params);
        console.log(response);
    } catch (err) {
        console.error(`err creating table:: ${err}`);
    }
}

/**
 * Method to call putIteam
 */
let callPutItem = async () => {
    const params = {
        TableName: 'student',
        Item: {
            'studentId': 124,
            'age': 23,
            'name': 'Richard',
            'address': '{"street":"123 Market st","city":  "Charlotte","state":"NC","zip":"27855" }'
        }
    };
    try {
        await putItem(params);
    } catch (err) {
        console.error(`err adding Item:: ${err}`);
    }
}

/**
 * Method to call readItem
 */
let callReadItem = async () => {
    let params = {
        TableName: 'student',
        Key: {
            "studentId": 125,
            "age": 23
        }
    };
    try {
        let response = await readItem(params);
        if (response) {
            console.log(response);
            console.log(`street = ${JSON.parse(response.address).street}`);
        }
    } catch (err) {
        console.error(`err reading Item:: ${err}`);
    }
}
//Invoke methods using IIFE
(async () => {
    //callCreateTable();
    //callPutItem();
    callReadItem();
})();