package cloudInterfaces;



import com.amazonaws.services.s3.AmazonS3;

public interface CloudContentInterface {
	
	public String listBuckets();
	
	public String listObjects(int bucketChoice);
	
	public String listAllObjects();

}
