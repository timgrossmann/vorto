/**
 * Copyright (c) 2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.vorto.repository.web.api.v1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.vorto.repository.core.IUserContext;
import org.eclipse.vorto.repository.core.ModelInfo;
import org.eclipse.vorto.repository.core.impl.UserContext;
import org.eclipse.vorto.repository.web.AbstractRepositoryController;
import org.eclipse.vorto.repository.web.core.ModelDtoFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Alexander Edelmann - Robert Bosch (SEA) Pte. Ltd.
 */
@Api(value = "/search")
@RestController("modelSearchController")
@RequestMapping(value = "/api/v1/search")
public class ModelSearchController extends AbstractRepositoryController {

  @ApiOperation(value = "Finds models by free-text search expressions",
		  notes = "This method call allows the user to do free-text search on the existing models in this repository.<br/>"
		  		+ "* Please note that this search works on the model's 'displayname' or 'name' and NOT on the 'namespace' or"
		  		+ " 'version' properties. It also works on model's 'description' property. This value can be set through"
		  		+ " the Model Editor in the Model Details page.<br/>"
		  		+ "		Example: If there are models with names: 'Light' and 'Lightbulp'<br/>"
		  		+ "Searching for 'light' will fetch both the models<br/>"
		  		+ "	* If you want to search the non-released models, you need to login.")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successful retrieval of search result"),
          @ApiResponse(code = 400, message = "Malformed search expression")})
  @RequestMapping(value = "/models", method = RequestMethod.GET,
	      produces = "application/json")
  @PreAuthorize("hasRole('ROLE_USER')")
  public List<ModelInfo> searchByExpression(
      @ApiParam(value = "a free-text search expression",
          required = true) @RequestParam("expression") String expression)
      throws UnsupportedEncodingException {
    IUserContext userContext =
        UserContext.user(SecurityContextHolder.getContext().getAuthentication().getName());
    List<ModelInfo> modelResources = modelRepository.search(URLDecoder.decode(expression, "utf-8"));
    return modelResources.stream().map(resource -> ModelDtoFactory.createDto(resource, userContext))
        .sorted(new Comparator<ModelInfo>() {

          @Override
          public int compare(ModelInfo o1, ModelInfo o2) {
            return o1.getCreationDate().after(o2.getCreationDate()) ? -1 : +1;
          }

        }).collect(Collectors.toList());
  }
}
