package cloudManipulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
import cloudInitialisation.CreateBuckets;
import cloudInitialisation.ServiceClient;
import cloudInterfaces.DeletionInterface;

public class Deletion  implements DeletionInterface {

	private AmazonS3 s3;

	public Deletion(AmazonS3 s3) {

		this.s3 = s3;
	}
	
	public void singleDeletion(int bucketChoice, int objectChoice) {
		
		ObjectListing objectListing = s3.listObjects(
				new ListObjectsRequest().withBucketName(s3.listBuckets().get(bucketChoice).getName()).withPrefix("My"));
		
		s3.deleteObject(s3.listBuckets().get(bucketChoice).getName(), objectListing.getObjectSummaries().get(objectChoice).getKey());
	
	}
	
	public void deleteBucket(int bucketChoice) {
		
		String bucketName = s3.listBuckets().get(bucketChoice).getName();
		deleteAllObjects(bucketName);
		s3.deleteBucket(bucketName);
		
	}
	
	public void deleteAllBuckets() {
		
		ArrayList<String> deletion = new ArrayList();
		for (int i = 0; i < s3.listBuckets().size(); i++) {
			String bucketName = s3.listBuckets().get(i).getName();
			deletion.add(bucketName);
		}
		for (String bucketName : deletion) {
			deleteAllObjects(bucketName);
			deleteBucketSimple(bucketName);
		}
		
	}
	
	//Delete object method for deleting bucket - accepts String argument of actual bucket name - is called by deleteAllBuckets method to delete contents of buckets before deletion of bucket
	public void deleteAllObjects(String bucketName) {

		/*
		 * Delete an object - Unless versioning has been turned on for your
		 * bucket, there is no way to undelete an object, so use caution when
		 * deleting objects.
		 */
		ObjectListing objectListing = s3
				.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("My"));
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			String objectKey = objectSummary.getKey();
			s3.deleteObject(bucketName, objectKey);
		}
	}
	
	//Individual bucket deletion method for deleting all buckets - called by deleteAllBuckets method after contents of bucket have been deleted.
	public void deleteBucketSimple(String bucketName) {
		/*
		 * Delete a bucket - A bucket must be completely empty before it can be
		 * deleted, so remember to delete any objects from your buckets before
		 * you try to delete them.
		 */
		System.out.println("Deleting bucket " + bucketName + "\n");
		s3.deleteBucket(bucketName);
		
	}
	

	
	
	
//	//individual object deletion where user specifies both bucket and object deletion choices
//	//NEED TO ACCOUNT FOR CHOICE WHEN NO OBJECTS EXIST
//	public void singleDeletion() {
//		
//		//lists buckets and accepts user choice
//		CloudContent cc = new CloudContent(s3);
//		cc.listBuckets();
//		System.out.println("Which bucket no. would you like to delete an object from?");
//		int bucketChoice = cc.choice();
//		
//		//lists objects from user's chosen bucket
//		System.out.println("Listing objects of " + s3.listBuckets().get(bucketChoice).getName());
//		ObjectListing objectListing = s3.listObjects(
//				new ListObjectsRequest().withBucketName(s3.listBuckets().get(bucketChoice).getName()).withPrefix("My"));
//		
//		//generates index value for user to choose which object to delete
//		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//			
//			int i = 0;
//			System.out.println(i + ". " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
//			i++;
//		}
//		System.out.println();
//		
//		//takes user input for which object to delete, calls s3 deletion methd on that object.
//		System.out.println("Which object no. would you like to delete?");
//		int objectChoice = cc.choice();
//		s3.deleteObject(s3.listBuckets().get(bucketChoice).getName(), objectListing.getObjectSummaries().get(objectChoice).getKey());
//		System.out.println("Object '" + objectListing.getObjectSummaries().get(objectChoice).getKey() + "' deleted from bucket\n "
//				+ s3.listBuckets().get(bucketChoice).getName());
//		System.out.println("Object deleted.\n");
//	}
	
//	//individual bucket deletion where user specifies bucket choice
//	//NEED TO ACCOUNT FOR CHOICE WHEN NO OBJECTS EXIST
//	public void deleteBucket() {
//		
//		//list buckets and accept user input
//		CloudContent cc = new CloudContent(s3);
//		cc.listBuckets();
//		System.out.println("Which bucket would you like to delete?");
//		int bucketChoice = cc.choice();
//		
//		/*
//		 * Delete a bucket - A bucket must be completely empty before it can be
//		 * deleted, so remember to delete any objects from your buckets before
//		 * you try to delete them.
//		 */
//		//iterates through objects in chosen bucket and calls s3 deletion method on each
//		ObjectListing objectListing = s3
//				.listObjects(new ListObjectsRequest().withBucketName(s3.listBuckets().get(bucketChoice).getName()).withPrefix("My"));
//		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//			String objectKey = objectSummary.getKey();
//			s3.deleteObject(s3.listBuckets().get(bucketChoice).getName(), objectKey);
//		}
//		//once deletion of all items is completed, bucket deletion method is called on bucket
//		System.out.println("Deleting bucket " + s3.listBuckets().get(bucketChoice).getName() + "\n");
//		s3.deleteBucket(s3.listBuckets().get(bucketChoice).getName());
//		System.out.println("Bucket deleted.\n");
//	}
	
	//method for deleting all buckets - 
	//asks for user confirmation before continuing
	//iterates through bucket list and adds all bucket names to a separate array for deletion
	//iterates through deletion array and deletes contents of each bucket and then deletes the bucket itself
//	public void deleteAllBuckets() {
//		
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Are you sure you want to delete all buckets? This cannot be undone. Y/N?");
//		String confirm = sc.next();
//		
//		if (confirm.equals("Y") || confirm.equals("y")) {
//			
//			ArrayList<String> deletion = new ArrayList();
//			for (int i = 0; i < s3.listBuckets().size(); i++) {
//				String name = s3.listBuckets().get(i).getName();
//				deletion.add(name);
//			}
//			for (String name : deletion) {
//				deleteAllObjects(name);
//				deleteBucketSimple(name);
//			}
//			
//			System.out.println("All buckets deleted.\n");
//		}
//		else if (confirm.equals("N") || confirm.equals("n")) {
////			Main.anythingElse();
//		}
//		
//		else {
//			System.out.println("Please select either Y(Yes) or N(No)/n");
//			deleteAllBuckets();
//		}
//		
//		
//	}
	
}
