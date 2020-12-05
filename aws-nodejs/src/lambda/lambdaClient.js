let AWS = require('aws-sdk');
const util = require('util');


AWS.config.update({ region: 'us-east-1' });

let lambda = new AWS.Lambda();

//Promisify the Lambda
let promisifiedLambda = util.promisify(lambda.invoke.bind(lambda));

/**
 * This function invokes Lambda
 * @param {*} functionName Name of the Lambda function
 * @param {*} payload Payload to be passed onto Lambda
 */
let lambdaInvoker = async (functionName, payload) => {
    let params = {
        FunctionName: functionName,
        Payload: `{ "body" : ${JSON.stringify(payload)} }`
    }
    let res;
    try {
        //Use this format if Lambds is not promisified
        res = await lambda.invoke(params).promise();

        //Use this format if Lambda is promisified
        //res = await promisifiedLambda(params);
    } catch (err) {
        console.error(`err:: ${err}`);
    }
    console.log(`Lambda invocation successful`);
    console.log(res);
    return res.Payload;
}

//Let's invoke the Lambda
let lambdaFunctionName = 'cli-target-lambda';
let payload = { name: "abc", age: 20 };

//Invoke lambdaInvoker using IIFE
(async () => {
    try {
        let response = await lambdaInvoker(lambdaFunctionName, payload);
        console.log(response);
    } catch (err) {
        console.error(`err:: ${err}`);
    }
})();