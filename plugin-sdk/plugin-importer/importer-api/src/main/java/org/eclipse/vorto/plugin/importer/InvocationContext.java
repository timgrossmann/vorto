package org.eclipse.vorto.plugin.importer;

public class InvocationContext {

	private String userOAuthToken;
	
	public static InvocationContext create(String token) {
		return new InvocationContext(token);
	}
	
	private InvocationContext(String token) {
		this.userOAuthToken = token;
	}

	public String getUserOAuthToken() {
		return userOAuthToken;
	}

	public void setUserOAuthToken(String userOAuthToken) {
		this.userOAuthToken = userOAuthToken;
	}
	
	
}
