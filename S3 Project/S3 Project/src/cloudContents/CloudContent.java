package cloudContents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import cloudInterfaces.CloudContentInterface;

public class CloudContent implements CloudContentInterface {
	
	private AmazonS3 s3;
	
	//Constructor
	public CloudContent(AmazonS3 s3) {
		
		this.s3 = s3;
	}
	
	public String listBuckets() {
		
		String bucketList = "";
		
		for (int i = 0; i < s3.listBuckets().size(); i++) {
			
			String name = s3.listBuckets().get(i).getName();
			bucketList += i + ". " + name + "\n";
			
		}
		
		return bucketList;
	}
	
	
	public String listObjects(int bucketChoice) {
		
		String objectlist = "";
		int i = 0;
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(s3.listBuckets().get(bucketChoice).getName())
                .withPrefix("My"));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
        	
        	objectlist += i + ". " + " - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ") \n";
        	i++;
        }
		
		return objectlist;
		
	}
	
	
	
	public String listObjects(String bucketName) {
		
		String objectlist = "Bucket Name: " + bucketName + "\n";
		int i = 0;
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("My"));
        
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
        	
        	objectlist += i + ". " + " - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ") \n";
        	i++;
        }
		
		return objectlist;
		
	}
	
	
	
	public String listAllObjects() {
		
		
		String allobjects = "";
		
		for (int i = 0; i < s3.listBuckets().size(); i++) {
			String bucketName = s3.listBuckets().get(i).getName();
			
			allobjects += i + ". " + listObjects(bucketName) + "\n";
		}
		
		return allobjects;
	}
	
	
	
	
	
	
	
	
	
	
//	//List objects method - takes user input choice and list objects for that particular bucket by finding it's name based on index value.
//	public void listObjects() {
//		
//		this.listBuckets();
//				
//		System.out.println("Which bucket no. would you like to list objects from?");
//		
//		int bucketChoice = this.choice();
//		
//		/*
//         * List objects in your bucket by prefix - There are many options for
//         * listing the objects in your bucket.  Keep in mind that buckets with
//         * many objects might truncate their results when listing their objects,
//         * so be sure to check if the returned object listing is truncated, and
//         * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
//         * additional results.
//         */
//        System.out.println("Listing objects of " + s3.listBuckets().get(bucketChoice).getName());
//        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
//                .withBucketName(s3.listBuckets().get(bucketChoice).getName())
//                .withPrefix("My"));
//        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//            System.out.println(" - " + objectSummary.getKey() + "  " +
//                               "(size = " + objectSummary.getSize() + ")");
//        }
//        System.out.println();
//	}
//	
//	//overloaded listObjects - takes actual bucketName as an argument rather than and index value - for use in listing objects from all buckets
//	public void listObjects(String bucketName, AmazonS3 s3) {
//				
//		/*
//         * List objects in your bucket by prefix - There are many options for
//         * listing the objects in your bucket.  Keep in mind that buckets with
//         * many objects might truncate their results when listing their objects,
//         * so be sure to check if the returned object listing is truncated, and
//         * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
//         * additional results.
//         */
//        System.out.println("Listing objects of " + bucketName);
//        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
//                .withBucketName(bucketName)
//                .withPrefix("My"));
//        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//            System.out.println(" - " + objectSummary.getKey() + "  " +
//                               "(size = " + objectSummary.getSize() + ")");
//        }
//        System.out.println();
//	}
//	
//	//List ALL objects method - iterates through list of buckets and then calls overloaded listObject method on each to list objects from all buckets
////	public void listAllObjects() {
////		
////		for (int i = 0; i < s3.listBuckets().size(); i++) {
////			String name = s3.listBuckets().get(i).getName();
////			listObjects(name, s3);
////			
////		}
////		
////	}
//	
//	
////	//List buckets method - iterates through bucket list to give user ability to select which bucket to operate on
////	public void listBuckets() {
////		
////		for (int i = 0; i < s3.listBuckets().size(); i++) {
////			String name = s3.listBuckets().get(i).getName();
////			
////			System.out.println(i + ". " + name);
////		}
////	}
//	
////	public void listBuckets(){
////		CloudContentsInterface obj = new CloudContent();
////		obj.listBuckets();
////	}
//	
//	
//	
//	//choice method for selecting buckets object by an integer/index value rather than keying in entire name
//	public int choice() {
//		
//		Scanner sc = new Scanner(System.in);
//		String bucketChoice = sc.next();
//		int asciiVal = (int) bucketChoice.charAt(0);
//
//		if (!(asciiVal < 49) && (asciiVal > 57)) {
//			System.out.println();
//			choice();
//
//		}
//		int choice = Integer.valueOf(bucketChoice);
//		
//		return choice;
//	}



}
