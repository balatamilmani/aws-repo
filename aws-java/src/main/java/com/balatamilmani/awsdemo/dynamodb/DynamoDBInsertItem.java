/*
 * Copyright (c) 2020. Balamurugan Tamilmani (balamurugan.leo@gmail.com). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 */
package com.balatamilmani.awsdemo.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * @author Balamurugan Tamilmani 
 * Demonstrates simple Insert operation in DynamoDB Table
 */
public class DynamoDBInsertItem {
	public static void main(String[] args) {
		DynamoDBInsertItem ddbInsert = new DynamoDBInsertItem();
		String dynamoDBTable = "student";
		int id = 120;
		int semester = 1;
		String course = "MIS 6300 Business Data Warehousing";
		boolean isRemote = false;
		try {
			ddbInsert.insertItem(dynamoDBTable, id, semester, course, isRemote);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertItem(String dynamoDBTable, int id, int semester, String course, boolean isRemote) {
		// Build DynamoDB Client
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		// Create DynamoDB Object using the Client
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

		// Retrieve the Table
		Table table = dynamoDB.getTable(dynamoDBTable);
		// Create new Item
		Item item = new Item().withPrimaryKey("id", id).withNumber("semester", 1).withString("course", course)
				.withBoolean("isRemote", isRemote);
		// Insert the Item into Table
		table.putItem(item);
	}
}
