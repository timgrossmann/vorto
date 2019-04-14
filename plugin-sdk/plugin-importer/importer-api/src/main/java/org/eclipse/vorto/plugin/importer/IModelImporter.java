package org.eclipse.vorto.plugin.importer;

import java.util.List;

import org.eclipse.vorto.repository.client.ModelInfo;


public interface IModelImporter {

	/**
	   * 
	   * @return unique service key of the importer, e.g. lwm2m
	   */
	  String getServiceKey();

	  /**
	   * 
	   * @return meta information about the importer
	   */
	  ImporterInfo getInfo();

	  /**
	   * Uploads model content and validates it. If the model is valid, the returned upload handle is
	   * used to import the model into the repository via
	   * {@link IModelImporter#doImport(String)}}
	   * 
	   * @param fileUpload
	   * @return result about information of the uploaded content and the upload handle.
	   */
	  UploadModelResult validateUpload(FileUpload fileUpload, InvocationContext context);

	  /**
	   * @pre {@link UploadModelResult#isValid() == true}}
	   *
	   * Imports the uploaded source models with the given upload handle ID to Vorto compliant models
	   * @param uploadHandle
	   * @return list of vorto models that have been imported to the repository
	   */
	  List<ModelInfo> doImport(String uploadHandleId, InvocationContext context);

}
