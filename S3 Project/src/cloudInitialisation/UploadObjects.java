package cloudInitialisation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import cloudInterfaces.UploadObjectsInterface;

public class UploadObjects implements UploadObjectsInterface {

	private AmazonS3 s3;

	public UploadObjects(AmazonS3 s3) {

		this.s3 = s3;
		
	}
	
	
	
	
	//object file generator from s3 sample code
	public File createSampleFile() throws IOException {
		File file = File.createTempFile("aws-java-sdk-", ".txt");
		file.deleteOnExit();

		Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		writer.write("abcdefghijklmnopqrstuvwxyz\n");
		writer.write("01234567890112345678901234\n");
		writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
		writer.write("01234567890112345678901234\n");
		writer.write("abcdefghijklmnopqrstuvwxyz\n");
		writer.close();

		return file;
	}

	public void uploadMethod(int bucketChoice, AmazonS3 s3, String key) throws IOException {
		/*
		 * Upload an object to your bucket - You can easily upload a file to S3,
		 * or upload directly an InputStream if you know the length of the data
		 * in the stream. You can also specify your own metadata when uploading
		 * to S3, which allows you set a variety of options like content-type
		 * and content-encoding, plus additional metadata specific to your
		 * applications.
		 */

		File xFile = createSampleFile();

		System.out.println("Uploading a new object '" + key + "' to S3 from a file\n");
		String bucketName = s3.listBuckets().get(bucketChoice).getName();
		s3.putObject(new PutObjectRequest(bucketName, key, xFile));
	}




	@Override
	public void uploadMethod(int bucketChoice) {
		// TODO Auto-generated method stub
		try {
			
			String key = "MyObjectKey";
			UUID id = UUID.randomUUID();
			key = key + "-" + id;
			
			File xFile = createSampleFile();
			String bucketName = s3.listBuckets().get(bucketChoice).getName();
			s3.putObject(new PutObjectRequest(bucketName, key, xFile));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
