package org.eclipse.vorto.plugin.importer;

public class ImporterInfo {

	private String label;
	private String shortDescription;
	private String supportedFileExtensions;
	
	public ImporterInfo(String label, String shortDescription, String supportedFileExtensions) {
		super();
		this.label = label;
		this.shortDescription = shortDescription;
		this.supportedFileExtensions = supportedFileExtensions;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getSupportedFileExtensions() {
		return supportedFileExtensions;
	}

	public void setSupportedFileExtensions(String supportedFileExtensions) {
		this.supportedFileExtensions = supportedFileExtensions;
	}
	
	
	
	
}
