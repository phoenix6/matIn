package org.matin.client;

public class MatINException extends RuntimeException {
	  
		int errorCode;
	
		public MatINException(String message) {
			super(message);
		}
  
		public MatINException(String message, Exception ex) {
	        super(message, ex);
	    }
		
		public MatINException(String message, int errorCode) {
			super(message);
			this.errorCode = errorCode;
		}
  
		public MatINException(String message, int errorCode, Exception ex) {
	        super(message, ex);
	        this.errorCode = errorCode;
	    }

		public int getCode() {
			return errorCode;
		}
	  
}
