package com.tenchael.ispring.web.back;

import static com.tenchael.ispring.common.Constants.CREATE;
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

import com.tenchael.ispring.domain.CourtStatus;
import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.service.CourtStatusService;
import com.tenchael.ispring.service.SportTypeService;

@Controller
@RequestMapping("/back/courtStatus")
public class CourtStatusControllerBack {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourtStatusService courStatustService;

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
		Page<CourtStatus> pagedList = courStatustService.findAll(pageable);
		log.info("List courtStatus list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/courtStatus/list";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(@RequestParam(value = "id", required = true) Short id,
			Model model) {
		CourtStatus courtStatus = courStatustService.findById(id);
		log.info("Get a courtStatus={}", courtStatus);
		model.addAttribute("bean", courtStatus);
		return "back/courtStatus/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("To create a courtStatus...");
		CourtStatus courtStatus = new CourtStatus();
		List<SportType> sportTypeList = sportTypeService.findAll();
		model.addAttribute("sportTypeList", sportTypeList);
		model.addAttribute("bean", courtStatus);
		model.addAttribute(OPRT, CREATE);
		return "back/courtStatus/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Short id,
			Model model) {
		CourtStatus courtStatus = courStatustService.findById(id);
		log.info("To edit a courtStatus={}", courtStatus);
		model.addAttribute("bean", courtStatus);
		model.addAttribute(OPRT, EDIT);
		return "back/courtStatus/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid CourtStatus courtStatus,
			BindingResult bindingResult, Model model) {
		log.info("Save courtStatus={}", courtStatus);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/courtStatus/list";
		}
		courStatustService.save(courtStatus);
		return "redirect:/back/courtStatus/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id", required = true) Short id) {
		log.info("Delete a courtStatus id={}", id);
		courStatustService.delete(id);
		return "redirect:/back/courtStatus/list";
	}

}
