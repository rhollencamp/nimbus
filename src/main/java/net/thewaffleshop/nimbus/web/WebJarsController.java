/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.thewaffleshop.nimbus.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.webjars.WebJarAssetLocator;


/**
 * This controller allows us to not have to specify the version of web frameworks in the URL
 *
 * @see http://www.webjars.org/documentation#springmvc
 * @author rhollencamp
 */
@Controller
public class WebJarsController
{
	@Autowired
	private WebJarAssetLocator assetLocator;

	@ResponseBody
	@RequestMapping("/webjarslocator/{webjar}/**")
	public ResponseEntity locateWebjarAsset(@PathVariable String webjar, HttpServletRequest request)
	{
		try {
			String mvcPrefix = "/webjarslocator/" + webjar + "/";
			String mvcPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			String fullPath = assetLocator.getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));
			return new ResponseEntity(new ClassPathResource(fullPath), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
