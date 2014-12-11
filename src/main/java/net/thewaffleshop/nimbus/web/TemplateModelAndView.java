package net.thewaffleshop.nimbus.web;

import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author rhollencamp
 */
public class TemplateModelAndView extends ModelAndView
{
	public TemplateModelAndView(String viewName)
	{
		super("template");
		getModel().put("_view", viewName);
	}
}
