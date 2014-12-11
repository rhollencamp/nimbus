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
