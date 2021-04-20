const fs = require('fs');
const path = require('path');

// Load the SDK for JavaScript
const AWS = require('aws-sdk');

// Set the Region 
AWS.config.update({ region: 'us-east-1' });

// Create S3 service object
s3 = new AWS.S3({ apiVersion: '2006-03-01' });

/**
 * 
 * @param {*} bucketName Name of the S3 Bucket
 * @param {*} fileName The Name of the file to be uploaded
 * @returns File upload operation response
 */
const uploadObject = async (bucketName, fileName) => {
    // call S3 to retrieve upload file to specified bucket
    let uploadParams = { Bucket: bucketName };

    // Configure the file stream and obtain the upload parameters
    let fileStream = fs.createReadStream(fileName);
    fileStream.on('error', function (err) {
        console.log('File Error', err);
        throw 'Error reading file';
    });
    uploadParams.Body = fileStream;
    uploadParams.Key = path.basename(fileName);
    const response = await s3.upload(uploadParams).promise();
    return response;
}

/**
 * @param {*} bucketName Name of the S3 Bucket
 * @param {*} objectName The name of the Object to be deleted
 * @returns Object deletion response
 */
const deleteObject = async (bucketName, objectName, objectVersion) => {
    let params = {
        Bucket: bucketName,
        Key: objectName
    };
    if (objectVersion) {
        params['VersionId'] = objectVersion;
    }
    const response = await s3.deleteObject(params).promise();
    return response;
}

/**
 * 
 * @param {*} bucketName Name of the Bucket to be created
 * @param {*} locationConstraint 
 * @returns Bucket creation response
 */
const createS3Bucket = async (bucketName, locationConstraint) => {
    var params = {
        Bucket: bucketName,
        ACL: 'private'
    };
    //Specifies the Region where the bucket will be created
    //Default is us-east-1, the value can't be default value
    if(locationConstraint !== 'us-east-1'){
        params['CreateBucketConfiguration'] = {'LocationConstraint' : locationConstraint};
    }
    let response = await s3.createBucket(params).promise();
    return response;
}

/**
 * 
 * @param {*} bucketName Name of the Bucket for enabling versioning
 * @returns putBucketVersioning method of S3 is not returning anything
 */
const enableBucketVersioning = async (bucketName) => {
    var params = {
        Bucket: bucketName,
        VersioningConfiguration: {
            Status: "Enabled"
        }
    };
    let response = await s3.putBucketVersioning(params).promise();
    return response;
}
//Invoke functions using IIFE
(async () => {
    let locationConstraint = 'us-east-1';//Default region is us-east-1
    let fileName = 'abcd.txt';
    let bucketName = 'bbalaversiontest-bucket';
    let versionId = '8wM.btrNW8_UePX1G4nDIgrtokCWSTWW';
    let response;
    try {
        //Create Bucket
        response = await createS3Bucket(bucketName, locationConstraint);
        //Enable versioning
        //response = await enableBucketVersioning(bucketName);
        //Upload Object
        //response = await uploadObject(bucketName, fileName);
        //Deletes particular version of Object if versionId is passed, 
        //otherwise creates a new Object and marks it as deleted
        //response = await deleteObject(bucketName, fileName, versionId);
        console.log(response);
    } catch (err) {
        console.error(`err:: ${err}\n, ${response}`);
    }
})();