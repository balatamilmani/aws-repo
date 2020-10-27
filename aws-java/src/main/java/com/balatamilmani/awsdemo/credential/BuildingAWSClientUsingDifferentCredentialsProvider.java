/*
 * Copyright (c) 2020. Balamurugan Tamilmani (balamurugan.leo@gmail.com). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 */
package com.balatamilmani.awsdemo.credential;

import java.util.Iterator;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.ListFunctionsResult;
import com.amazonaws.services.lambda.model.ServiceException;

/**
 * @author Balamurugan Tamilmani The main class to bootstrap the Application
 */
public class BuildingAWSClientUsingDifferentCredentialsProvider {

	public static void main(String[] args) {
		ListFunctionsResult functionResult = null;

		try {
			// Builds client using default credential and config from ~/.aws/credentials file
			// AWSLambda awsLambda = DefaultClient.defaultClientBuilder();

			// Builds client using named profile exist in credential file in the folder ~/.aws
			// AWSLambda awsLambda = DefaultClient.profileClientBuilder();

			// Builds client using credential from classpath's properties file
			// AWSLambda awsLambda = DefaultClient.classpathCredentialClientBuilder();

			// Builds client using credentials provided in code directly
			AWSLambda awsLambda = BuildingAWSClientUsingDifferentCredentialsProvider.basicCredentialClientBuilder();
			
			//Retrieve Lambdas
			functionResult = awsLambda.listFunctions();

			List<FunctionConfiguration> list = functionResult.getFunctions();

			for (Iterator<FunctionConfiguration> iter = list.iterator(); iter.hasNext();) {
				FunctionConfiguration config = iter.next();

				System.out.println("The function name is " + config.getFunctionName());
			}

		} catch (ServiceException e) {
			System.out.println(e);
		}
	}

	private static AWSLambda defaultClientBuilder() {
		AWSLambda awsLambda = AWSLambdaClientBuilder.defaultClient();
		return awsLambda;
	}

	private static AWSLambda profileClientBuilder() {
		String profileName = "lambdaUser";
		AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(profileName);
		AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withCredentials(credentialsProvider).build();
		return awsLambda;
	}

	private static AWSLambda classpathCredentialClientBuilder() {
		// Create a CredentilsProvider object using the AwsCredentials.properties in the classpath
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		// Build the Client using CredentialsProvider Object
		AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withCredentials(credentialsProvider).build();
		return awsLambda;
	}

	private static AWSLambda basicCredentialClientBuilder() {
		// Access and Secret keys of User
		String accessKey = "<YOUR_ACCESS_KEY>";
		String secretKey = "<YOUR_SECRET_KEY>";
		// Build a Credential object
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		// Create a CredentilsProvider object using the Credential object
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		// Build the Client using CredentialsProvider Object
		AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withCredentials(credentialsProvider)
		//To set Region explicitly
		// .withRegion(Regions.US_EAST_1)
		.build();
		return awsLambda;
	}

}
