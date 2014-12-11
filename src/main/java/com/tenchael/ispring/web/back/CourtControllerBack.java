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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenchael.ispring.domain.Court;
import com.tenchael.ispring.domain.CourtStatus;
import com.tenchael.ispring.domain.Player;
import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.service.CourtService;
import com.tenchael.ispring.service.CourtStatusService;
import com.tenchael.ispring.service.PlayerService;
import com.tenchael.ispring.service.SportTypeService;

@Controller
@RequestMapping("/back/court")
public class CourtControllerBack {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourtService courtService;

	@Autowired
	private SportTypeService sportTypeService;

	@Autowired
	private CourtStatusService courtStatusService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			// @PageableDefault(sort = "id", direction = Direction.DESC)
			// Pageable pageable,
			Model model) {
		Pageable pageable = new PageRequest(pageIndex, pageSize,
				Direction.DESC, "id");
		Page<Court> pagedList = courtService.findAll(pageable);
		log.info("List court list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/court/list";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		Court court = courtService.findById(id);
		log.info("Get a court={}", court);
		model.addAttribute("bean", court);
		return "back/court/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("To create a court...");
		Court court = new Court();
		List<SportType> sportTypeList = sportTypeService.findAll();
		List<CourtStatus> courtStatusList = courtStatusService.findAll();
		model.addAttribute("sportTypeList", sportTypeList);
		model.addAttribute("courtStatusList", courtStatusList);
		model.addAttribute("bean", court);
		model.addAttribute(OPRT, CREATE);
		return "back/court/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		Court court = courtService.findById(id);
		log.info("To edit a court={}", court);
		model.addAttribute("bean", court);
		model.addAttribute(OPRT, EDIT);
		return "back/court/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid Court court, BindingResult bindingResult,
			Model model) {
		log.info("Save court={}", court);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/court/list";
		}
		courtService.save(court);
		return "redirect:/back/court/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id", required = true) Integer id) {
		log.info("Delete a court id={}", id);
		courtService.delete(id);
		return "redirect:/back/court/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(
			@RequestParam(value = "condition", required = true) String condition,
			Model model) {
		log.info("Search sportTypes condition={}", condition);
		Pageable pageable = new PageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE,
				Direction.DESC, "id");
		Page<Court> pagedList = courtService.search(condition, pageable);
		log.info("List court list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/court/list";
	}
}
