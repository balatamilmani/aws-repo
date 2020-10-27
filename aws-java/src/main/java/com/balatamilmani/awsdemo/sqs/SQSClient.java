/*
 * Copyright (c) 2020. Balamurugan Tamilmani (balamurugan.leo@gmail.com). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 */
package com.balatamilmani.awsdemo.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

/**
 * @author Balamurugan Tamilmani
 * Demonstrates SQS message publishing
 *
 */
public class SQSClient {

	public static void main(String[] args) {
		String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789632/student-q";
		String messageBody = "{\"id\":\"1\", \"semester\":\"2\"}";
		SQSClient sqsc = new SQSClient();
		sqsc.sendMessage(messageBody, queueUrl);
	}

	public void sendMessage(String messageBody, String queueUrl) {
		//Create SQS Client
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		//Create Message request
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setQueueUrl(queueUrl);
		sendMessageRequest.setMessageBody(messageBody);

		//Publish the message
		SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
		System.out.println(sendMessageResult.getMessageId());
	}
}
