package cloudManipulation;

import java.io.IOException;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import cloudContents.CloudContent;
import cloudInterfaces.BucketSplitInterface;

public class BucketSplit implements BucketSplitInterface {
	
	private AmazonS3 s3;
	
	public BucketSplit(AmazonS3 s3) {

		this.s3 = s3;

	}
	
	public void bucketSplit(int bucketChoice) {
		
		String bucketSource = s3.listBuckets().get(bucketChoice).getName();
		
		String bucketName = "my-first-s3-bucket-";
		String bucketOne = bucketName + UUID.randomUUID();
		String bucketTwo = bucketName + UUID.randomUUID();
		
		s3.createBucket(bucketOne);
		s3.createBucket(bucketTwo);
		
		ObjectMovement om = new ObjectMovement(s3);
		om.moveObjectSplit(bucketSource, bucketOne, bucketTwo);
		
		System.out.println("Deleting source bucket...\n");
		s3.deleteBucket(bucketSource);
		
	}
	
	
	
	
	
	
	
	
	
	
	//bucket split method -
	
	//lists existing buckets, accepts user input for selecting which bucket to split
	//generates two new buckets with UUIDs
	//calls objectMoveSplit method from Object Movement class, to iterate through 
	//and transfer objects to target buckets based on object list size of source bucket
	//once object transfer is completed, the source bucket is deleted.
	
/*	public void bucketSplitter() {
		
		CloudContent cc = new CloudContent(s3);
		ObjectMovement om = new ObjectMovement(s3);
		cc.listBuckets();
		
		System.out.println("Which bucket no. you would like to split?");
		
		String bucketSource = s3.listBuckets().get(cc.choice()).getName();
		
		
		
		String bucketName = "my-first-s3-bucket-";
		String bucketOne = bucketName + UUID.randomUUID();
		String bucketTwo = bucketName + UUID.randomUUID();
		
		System.out.println("Creating new buckets...\n");
		
		s3.createBucket(bucketOne);
		s3.createBucket(bucketTwo);
		
//		int size = 0;
//		ObjectListing objectListing = s3.listObjects(
//				new ListObjectsRequest().withBucketName(bucketSource).withPrefix("My"));
//		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//			size++;
//			
//		}
//		System.out.println("size: " + size);
		System.out.println("Splitting bucket...\n");
		
		om.moveObjectSplit(bucketSource, bucketOne, bucketTwo);
	
		System.out.println("Deleting source bucket...\n");
		s3.deleteBucket(bucketSource);
	}*/
}
