package client;

import java.io.IOException;
import java.util.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

import cloudContents.CloudContent;
import cloudInitialisation.CreateBuckets;
import cloudInitialisation.ServiceClient;
import cloudManipulation.BucketSplit;
import cloudManipulation.BucketsMerge;
import cloudManipulation.Deletion;
import cloudManipulation.ObjectMovement;

public class Main {
	
	private static AmazonS3 s3;
	private static ServiceClient sc;
	private static CloudContent cc;
	private static Deletion dd;
	private static ObjectMovement om;
	private static BucketsMerge bm;
	private static BucketSplit bs;
	private static boolean initialised = false;
	
	public static void printMenu() {

		System.out.println("1. Generate Buckets");
		System.out.println("2. Upload an object");
		System.out.println("3. List bucket contents");
		System.out.println("4. List all bucket contents");
		System.out.println("5. Delete an object");
		System.out.println("6. Delete a bucket");
		System.out.println("7. Delete all buckets");
		System.out.println("8. Move object to different bucket");
		System.out.println("9. Merge buckets");
		System.out.println("10. Split bucket");
		System.out.println("11. Exit");
		System.out.println();

	}

	public static void userSelection() throws IOException {

		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println("Please make a selection between 1-9 ");
		String selection = scan.next();
		int asciiVal = (int) selection.charAt(0);
		
		 if (!(asciiVal < 49) && (asciiVal > 57)) {
		 System.out.println();
		 userSelection();
		
		 }
		int i = Integer.valueOf(selection);
		switch (i) {
		
		//cloud initialisation case - option given to generate buckets if cloud already initialised
		case 1:
			
			sc = new ServiceClient();
			CreateBuckets cb = new CreateBuckets(s3);
			if (initialised) {
				System.out.println("Cloud is already initialised\n");
				cb.bucketInitialisation(s3);
				anythingElse();
			}

	        System.out.println("===========================================");
	        System.out.println("Initialising Cloud");
	        System.out.println("===========================================");
	        System.out.println();
	        initialised = true;
	        s3 = sc.getS3();
	        
			cb.bucketInitialisation(s3);
			System.out.println();
			anythingElse();
			break;
		
		//list a bucket's contents case
		case 2:
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			
			else {
				System.out.println(i + ". List Bucket Contents selected\n");
			
			cc = new CloudContent(s3);
			cc.listObjects();
			anythingElse();
			break;
			}
		
		//list all bucket contents case
		case 3:
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			else {
			System.out.println(i + ". List All Buckets selected\n");
			cc = new CloudContent(s3);
			cc.listAllObjects();
			anythingElse();
			break;
			}
		
		//delete an object case
		case 4:
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			else {
			System.out.println(i + ". delete an object selected\n");
			dd = new Deletion(s3);
			dd.singleDeletion();
			anythingElse();
			break;
			}
		
			
		//delete a bucket case
		case 5:
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			else {
			System.out.println(i + ". Delete a Bucket selected\n");
			dd = new Deletion(s3);
			dd.deleteBucket();
			anythingElse();
			break;
			}
		
		//delete all buckets case
		case 6:
			System.out.println(i + ". Delete All Buckets selected\n");
			if (!initialised) {
				System.out.println("Please initialise cloud first.\n");
				anythingElse();
			}
			dd = new Deletion(s3);
			dd.deleteAllBuckets();
			anythingElse();
			break;
		
		//object move case
		case 7:
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			else {
			System.out.println(i + ". Object Move selected\n");
			om = new ObjectMovement(s3);
			om.moveObject();
			anythingElse();
			break;
			}
		
		//bucket merge case
		case 8:
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			else {
			System.out.println(i + ". Bucket Merge selected\n");
			bm = new BucketsMerge(s3);
			bm.bucketMerge();
			anythingElse();
			break;
			}
			
		//bucket split case
		case 9:
			
			if (!initialised) {
				System.out.println("Please initialise cloud first before performing this operation.\n");
				anythingElse();
			}
			else{
				System.out.println(i + ". Bucket Split selected\n");
				bs = new BucketSplit(s3);
				bs.bucketSplitter();
				anythingElse();
				break;
			}
		
		//exit case for terminating operation
		case 10:
			System.out.println(i + ". Quit selected\n");
			System.out.println("Goodbye.");
			break;
			
		default:
			System.out.println("invalid selection");
			userSelection();
		}
	}
	
	public static void anythingElse() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Would you like to perform any other operations Y/N?");
		String confirm = sc.next();
		if (confirm.equals("Y") || confirm.equals("y")) {
			printMenu();
			userSelection();
		}
		else if (confirm.equals("N") || confirm.equals("n")) {
			System.out.println("Thank you, goodbye.");
		}
		else {
			System.out.println("Please select either Y(Yes) or N(No)\n");
			anythingElse();
		}
			
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		printMenu();
		userSelection();
	}

}
