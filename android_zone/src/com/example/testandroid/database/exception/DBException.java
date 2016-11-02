package com.example.testandroid.database.exception;

public class DBException extends RuntimeException {

	public DBException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public DBException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}
}
