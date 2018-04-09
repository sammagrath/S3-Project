package cloudInterfaces;

import java.io.IOException;

public interface DeletionInterface {
	
	public void singleDeletion(int bucketChoice, int objectChoice);
	
	public void deleteBucket(int bucketChoice);
	
	public void deleteAllBuckets();

}
