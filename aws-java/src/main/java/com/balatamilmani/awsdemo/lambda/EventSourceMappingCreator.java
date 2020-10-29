/*
 * Copyright (c) 2020. Balamurugan Tamilmani (balamurugan.leo@gmail.com). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 */
package com.balatamilmani.awsdemo.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreamsClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ListStreamsRequest;
import com.amazonaws.services.dynamodbv2.model.ListStreamsResult;
import com.amazonaws.services.dynamodbv2.model.Stream;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.CreateEventSourceMappingRequest;
import com.amazonaws.services.lambda.model.CreateEventSourceMappingResult;

/**
 * @author Balamurugan Tamilmani
 * Adds Lambda as Trigger for DynamoDB Stream
 */
public class EventSourceMappingCreator {

	public static void main(String[] args) {
		String functionName = "studentProcessorLambda";
		String dynamoDBTable = "student";
		EventSourceMappingCreator esmc = new EventSourceMappingCreator();
		//Retrieve DynamoDB Stream ARN
		String eventSourceArn = esmc.getStreamArn(dynamoDBTable);
		//Add Lambda trigger for the Stream
		esmc.createEventSourceMapping(eventSourceArn, functionName);
	}

	/**
	 * @param dynamoDBTable DynamoDB Table name
	 * @return Stream ARN of the Table
	 */
	public String getStreamArn(String dynamoDBTable) {
		String streamArn = null;
		AmazonDynamoDBStreams dynamoDBStreamsClient = AmazonDynamoDBStreamsClientBuilder.standard().build();
		// Create request
		ListStreamsRequest listStreamRequest = new ListStreamsRequest();
		listStreamRequest.setTableName(dynamoDBTable);
		// Fetch Streams
		ListStreamsResult response = dynamoDBStreamsClient.listStreams(listStreamRequest);
		// Get the StreamARN
		if (response != null && response.getStreams() != null && response.getStreams().size() > 0) {
			Stream stream = response.getStreams().get(0);
			streamArn = stream.getStreamArn();
		}
		return streamArn;
	}

	/**
	 * @param eventSourceArn Stream ARN
	 * @param functionName   Name of the Lambda function
	 */
	public void createEventSourceMapping(String eventSourceArn, String functionName) {
		// create EventSourceMapping Request
		CreateEventSourceMappingRequest createEventSourceMappingRequest = new CreateEventSourceMappingRequest();
		// Set the Trigger enabled
		createEventSourceMappingRequest.setEnabled(true);
		// Set the Source of the Stream, in our case DynamoDB
		createEventSourceMappingRequest.setEventSourceArn(eventSourceArn);
		// Set the Starting position of the Stream to be consumed
		createEventSourceMappingRequest.setStartingPosition("LATEST");
		// Name of the Lambda function that gets triggered
		createEventSourceMappingRequest.setFunctionName(functionName);

		// Create Lambda Client
		AWSLambda lambdaClient = AWSLambdaClientBuilder.standard().build();
		// Execute SourceMapping request
		CreateEventSourceMappingResult createEventSourceMappingResult = lambdaClient
				.createEventSourceMapping(createEventSourceMappingRequest);
		System.out.println(createEventSourceMappingResult);
	}

}
