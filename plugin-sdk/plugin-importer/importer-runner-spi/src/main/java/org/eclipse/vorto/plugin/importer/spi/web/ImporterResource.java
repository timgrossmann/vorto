package org.eclipse.vorto.plugin.importer.spi.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.vorto.plugin.importer.FileUpload;
import org.eclipse.vorto.plugin.importer.IModelImporter;
import org.eclipse.vorto.plugin.importer.ImporterInfo;
import org.eclipse.vorto.plugin.importer.InvocationContext;
import org.eclipse.vorto.plugin.importer.UploadModelResult;
import org.eclipse.vorto.repository.client.ModelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/importers")
public class ImporterResource {

  @Autowired
  private IModelImporter importer;
	
  private static final String AUTHORIZATION = "Authorization";
	
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ImporterInfo info() {
    return importer.getInfo();
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public UploadModelResult upload(@RequestParam("file") MultipartFile file,final HttpServletRequest request) throws IOException {
	  final Optional<String> token = getAuthorization(request);
	  if (!token.isPresent()) {
		 throw new IllegalArgumentException("User Context is required in order to invoke Importers"); 
	  }
	  return importer.validateUpload(FileUpload.create(file.getOriginalFilename(), file.getBytes()),InvocationContext.create(token.get()));
  }
  
  private Optional<String> getAuthorization(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(AUTHORIZATION));
  }
  
  @RequestMapping(value = "/{handleId}", method = RequestMethod.PUT,
	      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ModelInfo> doImport(final @PathVariable String handleId,final HttpServletRequest request) {
	  final Optional<String> token = getAuthorization(request);
	  if (!token.isPresent()) {
		 throw new IllegalArgumentException("User Context is required in order to invoke Importers"); 
	  }
	  
	  return importer.doImport(handleId, InvocationContext.create(token.get()));
  }
}
