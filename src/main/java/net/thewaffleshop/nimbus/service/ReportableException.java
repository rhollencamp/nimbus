package net.thewaffleshop.nimbus.service;

/**
 *
 * @author rhollencamp
 */
public class ReportableException extends RuntimeException
{
	public ReportableException()
	{
		super();
	}

	public ReportableException(String message)
	{
		super(message);
	}

	public ReportableException(Throwable cause)
	{
		super(cause);
	}

	public ReportableException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
