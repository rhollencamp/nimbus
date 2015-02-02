package net.thewaffleshop.nimbus.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author rhollencamp
 */
@Controller
public class DashboardController
{
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard()
	{
		return new TemplateModelAndView("dashboard");
	}
}
