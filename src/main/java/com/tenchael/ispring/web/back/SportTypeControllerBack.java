package com.tenchael.ispring.web.back;

import static com.tenchael.ispring.common.Constants.CREATE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE_SIZE;
import static com.tenchael.ispring.common.Constants.EDIT;
import static com.tenchael.ispring.common.Constants.OPRT;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.service.SportTypeService;

@Controller
@RequestMapping("/back/sportType")
public class SportTypeControllerBack {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SportTypeService sportTypeService;

	@RequestMapping(value = "/list/{pageIndex}", method = RequestMethod.GET)
	public String list(
			@PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		log.info("list sport type");
		int page = (pageIndex != null) ? pageIndex : DEFAULT_PAGE;
		int size = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
		Pageable pageable = new PageRequest(page, size, new Sort(
				Direction.DESC, "id"));
		Page<SportType> sportTypesPage = sportTypeService.findAll(pageable);
		List<SportType> sportTypes = sportTypesPage.getContent();
		model.addAttribute("sportTypes", sportTypes);
		model.addAttribute("totalPages", sportTypesPage.getTotalPages());
		return "back/sportType/list";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String get(@PathVariable("id") Integer id, Model model) {
		log.info("get a sportType");
		SportType sportType = sportTypeService.findById(id);
		model.addAttribute("sportType", sportType);
		return "back/sportType/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("create a sportType");
		SportType sportType = new SportType();
		model.addAttribute("sportType", sportType);
		model.addAttribute(OPRT, CREATE);
		return "back/sportType/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid SportType sportType, BindingResult bindingResult,
			Model model) {
		log.info("save sportType={}", sportType);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/sportType/list";
		}
		sportTypeService.save(sportType);
		return "redirect:/back/sportType/list/" + DEFAULT_PAGE;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		SportType sportType = sportTypeService.findById(id);
		model.addAttribute(sportType);
		model.addAttribute(OPRT, EDIT);
		return "back/sportType/form";
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value = "page", required = false) Integer page) {
		log.debug("delete id={}", id);
		sportTypeService.delete(id);
		return "redirect:/back/sportType/list/" + DEFAULT_PAGE;
	}
}
