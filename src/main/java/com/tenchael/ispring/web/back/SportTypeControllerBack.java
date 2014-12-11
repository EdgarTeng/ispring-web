package com.tenchael.ispring.web.back;

import static com.tenchael.ispring.common.Constants.CREATE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE_SIZE;
import static com.tenchael.ispring.common.Constants.EDIT;
import static com.tenchael.ispring.common.Constants.OPRT;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			// @PageableDefault(sort = "id", direction = Direction.DESC)
			// Pageable pageable,
			Model model) {
		Pageable pageable = new PageRequest(pageIndex, pageSize,
				Direction.DESC, "id");
		Page<SportType> pagedList = sportTypeService.findAll(pageable);
		log.info("List sportType list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/sportType/list";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		SportType sportType = sportTypeService.findById(id);
		log.info("Get a sportType={}", sportType);
		model.addAttribute("bean", sportType);
		return "back/sportType/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("To create a sportType...");
		SportType sportType = new SportType();
		model.addAttribute("bean", sportType);
		model.addAttribute(OPRT, CREATE);
		return "back/sportType/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		SportType sportType = sportTypeService.findById(id);
		log.info("To edit a sportType={}", sportType);
		model.addAttribute("bean", sportType);
		model.addAttribute(OPRT, EDIT);
		return "back/sportType/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid SportType sportType, BindingResult bindingResult,
			Model model) {
		log.info("Save sportType={}", sportType);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/sportType/list";
		}
		sportTypeService.save(sportType);
		return "redirect:/back/sportType/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id", required = true) Integer id) {
		log.info("Delete a sportType id={}", id);
		sportTypeService.delete(id);
		return "redirect:/back/sportType/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(
			@RequestParam(value = "condition", required = true) String condition,
			Model model) {
		log.info("Search sportTypes condition={}", condition);
		Pageable pageable = new PageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE,
				Direction.DESC, "id");
		Page<SportType> pagedList = sportTypeService.findByNameLike(condition,
				pageable);
		log.info("List sportType list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/sportType/list";
	}
}
