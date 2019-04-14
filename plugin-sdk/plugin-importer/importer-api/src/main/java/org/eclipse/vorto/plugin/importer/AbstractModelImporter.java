package org.eclipse.vorto.plugin.importer;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.eclipse.vorto.repository.client.IRepositoryClient;
import org.eclipse.vorto.repository.client.ModelInfo;
import org.eclipse.vorto.repository.core.IUserContext;
import org.eclipse.vorto.repository.importer.FileUpload;
import org.eclipse.vorto.repository.importer.ValidationReport;

public abstract class AbstractModelImporter implements IModelImporter {
	
	private IRepositoryClient repositoryClient;
	
	public static final long TTL_TEMP_STORAGE_INSECONDS = 60 * 5;

	private static Logger logger = Logger.getLogger(AbstractModelImporter.class);

	@Override
	public UploadModelResult validateUpload(FileUpload fileUpload, InvocationContext context) {
		 if (!this.getInfo().getSupportedFileExtensions().equals(fileUpload.getFileExtension())) {
		      return new UploadModelResult(null, new StatusMessage("Invalid file type for this importer.", MessageSeverity.ERROR));
		} else {
			StatusMessage message = doValidateUpload(fileUpload, context);
			if (message.getSeverity() != MessageSeverity.ERROR) {
				return new UploadModelResult(createUploadHandle(fileUpload), message);
			} else {
				return new UploadModelResult(null, message);
			}
		}
	}

	protected abstract StatusMessage doValidateUpload(FileUpload fileUpload, InvocationContext context);
	
	private String createUploadHandle(FileUpload fileUpload) {
	    final String handleId = UUID.randomUUID().toString();
	    return this.uploadStorage.store(handleId, fileUpload, TTL_TEMP_STORAGE_INSECONDS).getKey();
	  }

	@Override
	public List<ModelInfo> doImport(String uploadHandleId, InvocationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
