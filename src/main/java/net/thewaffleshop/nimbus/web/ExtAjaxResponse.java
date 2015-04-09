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

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author rhollencamp
 */
public class ExtAjaxResponse
{
	private boolean success;
	private String msg;
	private Object data;
	private Map<String, String> errors;

	public ExtAjaxResponse()
	{
	}

	public ExtAjaxResponse(boolean success)
	{
		this.success = success;
	}

	public ExtAjaxResponse(boolean success, String msg)
	{
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public Map<String, String> getErrors()
	{
		return errors;
	}

	public void setErrors(Map<String, String> errors)
	{
		this.errors = errors;
	}

	public void addError(String key, String message)
	{
		if (errors == null) {
			errors = new HashMap<>();
		}
		errors.put(key, message);
	}
}
