package com.qzybj.libraryimageload.exception;

/**
 * Custom Exception
 */
public class LoadImageException extends RuntimeException {
	public LoadImageException(String errorMsg) {
		super(errorMsg);
	}
}
