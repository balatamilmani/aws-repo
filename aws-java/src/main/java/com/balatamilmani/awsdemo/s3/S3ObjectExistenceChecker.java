/*
 * Copyright (c) 2020. Balamurugan Tamilmani (balamurugan.leo@gmail.com). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 */
package com.balatamilmani.awsdemo.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * @author Balamurugan Tamilmani Demonstrates S3 object existence check
 */
public class S3ObjectExistenceChecker {
	public static void main(String[] args) {
		S3ObjectExistenceChecker s3ObjectChecker = new S3ObjectExistenceChecker();
		String bucketName = "balatamilmani";
		String objectName = "com/balatamilmani/test-s3.txt";
		boolean fileExists = s3ObjectChecker.isObjectExists(bucketName, objectName);
		System.out.println(fileExists);
	}

	/**
	 * @param bucketName Name of the S3 Bucket
	 * @param objectName Complete path of the Object being checked for existence
	 * @return
	 */
	public boolean isObjectExists(String bucketName, String objectName) {
		// Build S3 Client
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
		// Check whether Object exists
		boolean fileExists = amazonS3.doesObjectExist(bucketName, objectName);
		return fileExists;
	}
}
