package com.tenchael.ispring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tenchael.ispring.common.Settings;
import com.tenchael.ispring.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Settings settings;

	/**
	 * This method returns the view name as a string, and passes a model map to the view.
	 * The view name is resolved using the viewResolver defined in servlet-context.xml.
	 * Attribute values in the model are accessible by referencing the name of the attribute (e.g. mode).
	 *
	 * @param model contains values to be passed through to the view
	 * @return name of view to resolve
	 */
	@RequestMapping(value={"/", "/home"})
	public String actionHome(Model model) {
		log.info("actionHome");

		try {
			model.addAttribute("mode", settings.getMode());
		} catch (NotFoundException ex) {
			model.addAttribute("mode", "unknown");
		}
		model.addAttribute("returnType", "String");
		return "home";
	}

	/**
	 * This method returns a ModelAndView object, which contains the view name, and a model map.
	 * The view name is resolved using the viewResolver defined in servlet-context.xml.
	 * Attribute values in the model are accessible in the view by referencing the name (e.g. model.mode), or simply by referencing the name of the attribute (e.g. mode).
	 * @param model contains values to be passed through to the view
	 * @return ModelAndView object, which contains the view and model
	 */
	@RequestMapping(value="/home/modelAndView")
	public ModelAndView actionHomeModelAndView(Model model) {
		log.info("actionHomeModelAndView");

		try {
			model.addAttribute("mode", settings.getMode());
		} catch (NotFoundException ex) {
			model.addAttribute("mode", "unknown");
		}
		model.addAttribute("returnType", "ModelAndView");
		return new ModelAndView("home", "model", model);
	}

	/**
	 * This method uses the @ResponseBody annotation to automagically use Jackson to return the map in JSON format.
	 * @return json response
	 */
	@RequestMapping(value="/home.json")
	public @ResponseBody Map<String, String> actionHomeJson() {
		log.info("actionHomeJson");

		Map<String, String> retMap = new HashMap<String, String>();
		try {
			retMap.put("mode", settings.getMode());
		} catch (NotFoundException ex) {
			retMap.put("mode", "unknown");
		}
		return retMap;
	}

}
