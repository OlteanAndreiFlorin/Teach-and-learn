package ro.sci.group2.service;

import java.util.Arrays;

@SuppressWarnings("serial")
public class ValidationException extends Exception {
	private String[] causes;

	public ValidationException(String... causes) {
		this.causes = causes;
	}

	@Override
	public String getMessage() {
		return causes != null ? Arrays.toString(causes) : "NO CAUSE";

	}

}
