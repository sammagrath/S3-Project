package cloudInitialisation;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;

import cloudInterfaces.CreateBucketsInterface;

public class CreateBuckets implements CreateBucketsInterface {
	
	private AmazonS3 s3;
	//Constructor
	public CreateBuckets(AmazonS3 s3) {
		
		this.s3 = s3;
		
	}
	
	//Bucket generator method - asks for user input, generates selected number of buckets with unique IDs, and then calls upload method, which generates and uploads object into each.
	//Each object has UUID appended to it's key.
	public void generateBuckets(int buckets) {
		
		
		
		for (int i = 0; i < buckets; i++) {
			
			String bucketName = "my-first-s3-bucket-";
			String key = "MyObjectKey";
			
			UUID id = UUID.randomUUID();
			bucketName = "my-first-s3-bucket-" + id;
			id = UUID.randomUUID();
			key = key + "-" + id;
			s3.createBucket(bucketName);
			
//			UploadObjects upload = new UploadObjects(bucketName);
//			upload.uploadMethod(bucketName, s3, key);
		}
		
	}

//	public void generateBuckets(int buckets) throws IOException {
//		
//		for (int i = 0; i < buckets; i++) {
//			String bucketName = "my-first-s3-bucket-";
//			String key = "MyObjectKey";
//			UUID id = UUID.randomUUID();
//			bucketName = "my-first-s3-bucket-" + id;
//			System.out.println("Creating bucket " + bucketName + "\n");
//			id = UUID.randomUUID();
//			key = key + "-" + id;
////			s3.createBucket(bucketName);
////			UploadObjects upload = new UploadObjects(bucketName);
////			upload.uploadMethod(bucketName, s3, key);
//		}
//	}
	
	public void bucketInitialisation(AmazonS3 s3) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Generate buckets Y/N?");
		String confirm = sc.next();
		
		if (confirm.equals("Y") || confirm.equals("y")) {
			this.generateBuckets(s3);
		}
		else if (confirm.equals("N") || confirm.equals("n")) {
			System.out.println("No buckets generated");
		}
		else {
			System.out.println("Please select either Y(Yes) or N(No)/n");
			bucketInitialisation(s3);
		}
			
		
	}

}
