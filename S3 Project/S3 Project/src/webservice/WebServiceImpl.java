package webservice;

import java.io.File;
import java.io.IOException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;

import cloudContents.CloudContent;
import cloudInitialisation.CreateBuckets;
import cloudInitialisation.ServiceClient;
import cloudInitialisation.UploadObjects;
import cloudInterfaces.ServiceClientInterface;
import cloudManipulation.BucketSplit;
import cloudManipulation.BucketsMerge;
import cloudManipulation.Deletion;
import cloudManipulation.ObjectMovement;
@WebService(endpointInterface="webservice.MyWebService")
@SOAPBinding(style=Style.RPC)
public class WebServiceImpl implements MyWebService {
	
	private static AmazonS3 s3;
	
//	public void listBuckets(){
//	CloudContentsInterface obj = new CloudContent();
//	obj.listBuckets();
//}

	@Override
	public void generateBuckets(int buckets) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		CreateBuckets cb = new CreateBuckets(s3);
		
		cb.generateBuckets(buckets);
	}

	@Override
	public void uploadMethod(int bucketChoice) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		CreateBuckets cb = new CreateBuckets(s3);
		
		UploadObjects uo = new UploadObjects(s3);
		uo.uploadMethod(bucketChoice);
	}
	

	
	@Override
	public String listObjects(int bucketChoice) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		String list = "";
		
		CloudContent cc = new CloudContent(s3);
		list = cc.listObjects(bucketChoice);
		
		return list;
		
	}

	@Override
	public String listAllObjects() {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		String list = "";
		
		CloudContent cc = new CloudContent(s3);
		list = cc.listAllObjects();
		
		return list;
	}

	@Override
	public void singleDeletion(int bucketChoice, int objectChoice) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		
		Deletion dd = new Deletion(s3);
		dd.singleDeletion(bucketChoice, objectChoice);
	}

	@Override
	public void deleteBucket(int bucketChoice) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		
		Deletion dd = new Deletion(s3);
		dd.deleteBucket(bucketChoice);
	}

	@Override
	public void deleteAllBuckets() {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		
		Deletion dd = new Deletion(s3);
		dd.deleteAllBuckets();
	}

	@Override
	public void moveObject(int sourceBucket, int objectChoice, int targetBucket) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		
		ObjectMovement om = new ObjectMovement(s3);
		om.moveObject(sourceBucket, objectChoice, targetBucket);
	}

	@Override
	public void bucketMerge(int bucketOne, int bucketTwo) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		
		BucketsMerge bm = new BucketsMerge(s3);
		bm.bucketMerge(bucketOne, bucketTwo);
		
	}

	@Override
	public void bucketSplit(int bucketChoice) {
		
		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		
		BucketSplit bs = new BucketSplit(s3);
		bs.bucketSplit(bucketChoice);
	}

	@Override
	public String listBuckets() {

		ServiceClient sc = new ServiceClient();
		sc.initialiseCloud();
		s3 = sc.getS3();
		String list = "";
		
		CloudContent cc = new CloudContent(s3);
		list = cc.listBuckets();
		
		return list;
	}





}
