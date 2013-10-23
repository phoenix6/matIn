/**
 * 
 */
package org.matin.client;

/**
 * These are error codes returned by the MatIN REST API
 * 
 * @author Dave Turner
 */
public enum MatINErrorCode {
	
	INTERNAL_SERVER_ERROR,
	MATERIAL_NOT_FOUND,
	MATERIAL_CREATION_FAILED,
	SAMPLE_NOT_FOUND, 
	SAMPLE_CREATION_FAILED, 
	DATAOBJECT_NOT_FOUND, 
	DATAOBJECT_CREATION_FAILED,
	DATAOBJECT_FILE_EXISTS, 
	DATAOBJECT_FILE_TRANSFER_FAIL,
	 	
}
