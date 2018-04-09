package cloudManipulation;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import cloudContents.CloudContent;

public class BucketsMerge {
	
	private AmazonS3 s3;
	
	public BucketsMerge(AmazonS3 s3) {

		this.s3 = s3;

	}
	
	public void bucketMerge(int bucketOne, int bucketTwo) {
		
		String bucketName = "my-first-s3-bucket-";
		bucketName = bucketName + UUID.randomUUID();
		s3.createBucket(bucketName);

		String cBucketOne = s3.listBuckets().get(bucketOne).getName();
		String cBucketTwo = s3.listBuckets().get(bucketTwo).getName();
		
		ObjectMovement om = new ObjectMovement(s3);
		
		om.moveObjectMerge(cBucketOne, bucketName);
		om.moveObjectMerge(cBucketTwo, bucketName);
		
		s3.deleteBucket(cBucketOne);
		s3.deleteBucket(cBucketTwo);
		
	}
	
	
	
	
//	//bucket merge - 
//	//list buckets, takes user input selection of which buckets to merge, creates new target bucket with UUID, then calls moveObject method from Object movement class on each
//	//once all objects are moved and deleted from source buckets, the source buckets are deleted
//	public void bucketMerge() throws IOException {
//		
//		CloudContent cc = new CloudContent(s3);
//		
//		cc.listBuckets();
//		String bucketName = "my-first-s3-bucket-";
//		
//		System.out.println("Please enter the no. of the first bucket to merge: ");
//		String bucketOne = s3.listBuckets().get(cc.choice()).getName();
//		System.out.println("Please enter the no. of the second bucket to merge: ");
//		String bucketTwo = s3.listBuckets().get(cc.choice()).getName();
//		
//		bucketName = bucketName + UUID.randomUUID();
//		s3.createBucket(bucketName);
//		
//		ObjectMovement om = new ObjectMovement(s3);
//		
//		om.moveObjectMerge(bucketOne, bucketName);
//		om.moveObjectMerge(bucketTwo, bucketName);
//		
//        System.out.println("Deleting bucket " + bucketOne + "\n");
//        s3.deleteBucket(bucketOne);
//        System.out.println("Deleting bucket " + bucketTwo + "\n");
//        s3.deleteBucket(bucketTwo);
//				
//		
//		
//	}
	
}
