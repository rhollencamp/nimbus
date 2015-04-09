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
package net.thewaffleshop.nimbus.service;

/**
 *
 * @author rhollencamp
 */
public class ReportableFieldException extends ReportableException
{
	private final String field;

	public ReportableFieldException(String field, String message)
	{
		// @todo use a message formatter
		super(message);

		this.field = field;
	}

	public ReportableFieldException(String field, String message, Throwable cause)
	{
		// @todo use a message formatter
		super(message, cause);

		this.field = field;
	}

	public String getField()
	{
		return field;
	}
}
