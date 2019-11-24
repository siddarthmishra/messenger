package org.siddarth.javabrains.messenger.model;

public class ErrorMessage {

	private String errorMessage;
	private int errorCode;
	private String additionalInfo;
	private StackTraceElement stackTraceElement;

	public ErrorMessage() {
	}

	public ErrorMessage(String errorMessage, int errorCode, String additionalInfo,
			StackTraceElement stackTraceElement) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.additionalInfo = additionalInfo;
		this.stackTraceElement = stackTraceElement;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public StackTraceElement getStackTraceElement() {
		return stackTraceElement;
	}

	public void setStackTraceElement(StackTraceElement stackTraceElement) {
		this.stackTraceElement = stackTraceElement;
	}
}
