package org.eclipse.vorto.plugin.importer;

public class UploadModelResult {

	private String handleId = null;
	
	private StatusMessage uploadMessage; 

	public UploadModelResult(String handleId, StatusMessage uploadMessage) {
		super();
		this.handleId = handleId;
		this.uploadMessage = uploadMessage;
	}

	protected UploadModelResult() {

	}
	
	public boolean isValidUpload() {
		return this.uploadMessage.getSeverity() != MessageSeverity.ERROR;
	}

	public String getHandleId() {
		return handleId;
	}

	public StatusMessage getUploadMessage() {
		return uploadMessage;
	}

	public void setUploadMessage(StatusMessage uploadMessage) {
		this.uploadMessage = uploadMessage;
	}

	public void setHandleId(String handleId) {
		this.handleId = handleId;
	}

	
}
