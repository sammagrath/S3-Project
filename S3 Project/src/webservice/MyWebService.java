package webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import cloudInterfaces.BucketMergeInterface;
import cloudInterfaces.BucketSplitInterface;
import cloudInterfaces.CloudContentInterface;
import cloudInterfaces.CreateBucketsInterface;
import cloudInterfaces.DeletionInterface;
import cloudInterfaces.ObjectMovementInterface;
import cloudInterfaces.UploadObjectsInterface;

@WebService
@SOAPBinding(style=Style.RPC)
public interface MyWebService extends CreateBucketsInterface, UploadObjectsInterface, CloudContentInterface, DeletionInterface, ObjectMovementInterface, BucketMergeInterface, BucketSplitInterface {
	
}