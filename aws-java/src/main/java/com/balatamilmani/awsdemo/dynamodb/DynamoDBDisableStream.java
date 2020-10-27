/*
 * Copyright (c) 2020. Balamurugan Tamilmani (balamurugan.leo@gmail.com). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 */
package com.balatamilmani.awsdemo.dynamodb;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;

/**
 * @author Balamurugan Tamilmani 
 * Demonstrates Disabling/Enabling DynamoDB Stream
 */
public class DynamoDBDisableStream {
	public static void main(String[] args) {
		DynamoDBDisableStream ddds = new DynamoDBDisableStream();
		String dynamoDBTable = "students";
		boolean enable = false; // set to true to enable and false to disable
		ddds.enableDisableDynamoDbStream(enable, dynamoDBTable);
		System.out.println("stream modified");
	}

	/**
	 * @param enable true to enable and false to Disable the Stream
	 * @param dynamoDBTable Name of the DynamoDB Table
	 */
	public void enableDisableDynamoDbStream(boolean enable, String dynamoDBTable) {
		// Create Async client
		AmazonDynamoDBAsync ddb = AmazonDynamoDBAsyncClientBuilder.defaultClient();

		// Create StreamSpecification
		StreamSpecification streamSpecification = new StreamSpecification();
		streamSpecification.setStreamEnabled(enable);

		// StreamViewType is needed only for enabling
		if (enable) {
			streamSpecification.setStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES);
		}

		// Create update request using the StreamSpecification
		UpdateTableRequest updateTableRequest = new UpdateTableRequest();
		updateTableRequest.setTableName(dynamoDBTable);
		updateTableRequest.setStreamSpecification(streamSpecification);

		// Execute the update request Asynchronously
		Future<UpdateTableResult> updateResultFuture = ddb.updateTableAsync(updateTableRequest);
		UpdateTableResult updateresult = null;
		try {
			// Blocked until result is available
			updateresult = updateResultFuture.get();
		} catch (InterruptedException e) {
			// Handle exception
			System.out.println(e);
		} catch (ExecutionException e) {
			// Handle exception
			System.out.println(e);
		}
		if (updateresult != null && updateresult.getTableDescription() != null) {
			System.out.println("updateresult " + updateresult);
		}
	}
}
