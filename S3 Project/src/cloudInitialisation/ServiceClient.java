package cloudInitialisation;

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

public class ServiceClient {
	
//	public ServiceClient () {
//		initialiseCloud();
//		
//	}
	private AWSCredentials credentials;
	private static AmazonS3 s3;
	private Region region;
	

	public ServiceClient() {
		initialiseCloud();
		
	}
	
	public AmazonS3 getS3() {
		return s3;
	}
	
	public AWSCredentials getCredentials() {
		return credentials;
	}
	
	public Region getRegion() {
		return region;
	}
	
	//cloud initialisation - generates and sets credentials, region and client.
	public void initialiseCloud() {

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (C:\\Users\\magratsam\\.aws\\credentials).
         */
        credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\magratsam\\.aws\\credentials), and is in valid format.",
                    e);
        }

        s3 = new AmazonS3Client(credentials);
        region = Region.getRegion(Regions.AP_SOUTHEAST_2);
        s3.setRegion(region);
        
	}

}
