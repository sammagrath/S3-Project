package cloudManipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import cloudContents.CloudContent;
import cloudInterfaces.ObjectMovementInterface;

public class ObjectMovement implements ObjectMovementInterface {
	
	private AmazonS3 s3;
	
	public ObjectMovement(AmazonS3 s3) {

		this.s3 = s3;		
		
	}
	
	public void moveObject(int sourceBucket, int objectChoice, int targetBucket) {
		
		String cSourceBucket = s3.listBuckets().get(sourceBucket).getName();
		String cTargetBucket = s3.listBuckets().get(targetBucket).getName();
		
		ObjectListing objectListing = s3.listObjects(
				new ListObjectsRequest().withBucketName(cSourceBucket).withPrefix("My"));
		s3.copyObject(cSourceBucket, objectListing.getObjectSummaries().get(objectChoice).getKey(), cTargetBucket, objectListing.getObjectSummaries().get(objectChoice).getKey());
		
		s3.deleteObject(cSourceBucket, objectListing.getObjectSummaries().get(objectChoice).getKey());
	}
	
	

	
	//overloaded/simplified method for bucket merge - takes String arguments for source and target bucket, then iterates through objects in source bucket, calls s3 copy object method on each one and then deletes them.
	public void moveObjectMerge(String bucketSource, String bucketTarget) {
		
		CloudContent cc = new CloudContent(s3);
		ObjectListing objectListing = s3.listObjects(
				new ListObjectsRequest().withBucketName(bucketSource).withPrefix("My"));
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			String objectKey = objectSummary.getKey();
			
			s3.copyObject(bucketSource, objectKey, bucketTarget, objectKey);
			s3.deleteObject(bucketSource, objectKey);
		}
	}
	
	//bucket split method - takes String argument for source and target buckets - iterates through items in source bucket, if index value of object is lower than half the size of source bucket's object list,
	//it is transferred to first target bucket. Once the index value of objects exceed this threshold, they are transferred to the second target bucket.
	public void moveObjectSplit(String bucketSource, String bucketOne, String bucketTwo) {
		
		int i = 0;
		CloudContent cc = new CloudContent(s3);
		ObjectListing objectListing = s3.listObjects(
				new ListObjectsRequest().withBucketName(bucketSource).withPrefix("My"));
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			
			String objectKey = objectSummary.getKey();
			if (i < (objectListing.getObjectSummaries().size()/2)) {
				s3.copyObject(bucketSource, objectKey, bucketOne, objectKey);
				s3.deleteObject(bucketSource, objectKey);
			}
			else {
				s3.copyObject(bucketSource, objectKey, bucketTwo, objectKey);
				s3.deleteObject(bucketSource, objectKey);
			}
			i++;
		}
	}
	
//	//move object method - accepts user input after listing all buckets
//	public void moveObject() {
//		
//		
//		
//		
//		
//		CloudContent cc = new CloudContent(s3);
//		cc.listBuckets();
//		System.out.println("Which bucket no. would you like to move an object from?");
//		int bucketSource = cc.choice();
//		System.out.println("Which bucket no. would you like to move an object to?");
//		int bucketTarget = cc.choice();
//		
//		
//		//list contents of source bucket, accepts user input for choosing which object to move
//		System.out.println("Listing objects of " + s3.listBuckets().get(bucketSource).getName());
//				
//		ObjectListing objectListing = s3.listObjects(
//				new ListObjectsRequest().withBucketName(s3.listBuckets().get(bucketSource).getName()).withPrefix("My"));
//		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//			
//			int i = 0;
//			System.out.println(i + ". " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
//			i++;
//		}
//		System.out.println();
//		System.out.println("Which object no. would you like to move?");
//		int objectChoice = cc.choice();
//		
//		//calls s3 copy object method to move to target bucket and then deletes object from source bucket
//		s3.copyObject(s3.listBuckets().get(bucketSource).getName(), objectListing.getObjectSummaries().get(objectChoice).getKey(), s3.listBuckets().get(bucketTarget).getName(), objectListing.getObjectSummaries().get(objectChoice).getKey());
//		
//		s3.deleteObject(s3.listBuckets().get(bucketSource).getName(), objectListing.getObjectSummaries().get(objectChoice).getKey());
//		System.out.println("Object '" + objectListing.getObjectSummaries().get(objectChoice).getKey() + "' moved from bucket\n '"
//				+ s3.listBuckets().get(bucketSource).getName() + "' to '" + s3.listBuckets().get(bucketSource).getName() + ".'");
//		
//	
//	}
	
}
