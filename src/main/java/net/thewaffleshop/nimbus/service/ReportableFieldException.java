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
