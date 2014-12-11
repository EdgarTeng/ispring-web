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
import com.tenchael.ispring.domain.Player;
import com.tenchael.ispring.domain.Reservation;
import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.service.CourtService;
import com.tenchael.ispring.service.PlayerService;
import com.tenchael.ispring.service.ReservationService;
import com.tenchael.ispring.service.SportTypeService;

@Controller
@RequestMapping("/back/reservation")
public class ReservationControllerBack {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private CourtService courtService;

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
		Page<Reservation> pagedList = reservationService.findAll(pageable);
		log.info("List reservation list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/reservation/list";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		Reservation reservation = reservationService.findById(id);
		log.info("Get a reservation={}", reservation);
		model.addAttribute("bean", reservation);
		return "back/reservation/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("To create a reservation...");
		Reservation reservation = new Reservation();
		List<Player> playerList = playerService.findAll();
		List<SportType> sportTypeList = sportTypeService.findAll();
		List<Court> courtList = courtService.findAll();
		model.addAttribute("bean", reservation);
		model.addAttribute("playerList", playerList);
		model.addAttribute("sportTypeList", sportTypeList);
		model.addAttribute("courtList", courtList);
		model.addAttribute(OPRT, CREATE);
		return "back/reservation/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		Reservation reservation = reservationService.findById(id);
		log.info("To edit a reservation={}", reservation);
		List<Player> playerList = playerService.findAll();
		List<SportType> sportTypeList = sportTypeService.findAll();
		List<Court> courtList = courtService.findAll();
		model.addAttribute("bean", reservation);
		model.addAttribute("playerList", playerList);
		model.addAttribute("sportTypeList", sportTypeList);
		model.addAttribute("courtList", courtList);
		model.addAttribute(OPRT, EDIT);
		return "back/reservation/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid Reservation reservation,
			BindingResult bindingResult, Model model) {
		log.info("Save reservation={}", reservation);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/reservation/list";
		}
		reservationService.save(reservation);
		return "redirect:/back/reservation/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id", required = true) Integer id) {
		log.info("Delete a reservation id={}", id);
		reservationService.delete(id);
		return "redirect:/back/reservation/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(
			@RequestParam(value = "condition", required = true) String condition,
			Model model) {
		log.info("Search sportTypes condition={}", condition);
		Pageable pageable = new PageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE,
				Direction.DESC, "id");
		Page<Reservation> pagedList = reservationService.findByPlayerName(
				condition, pageable);
		log.info("List reservation list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/reservation/list";
	}
}
