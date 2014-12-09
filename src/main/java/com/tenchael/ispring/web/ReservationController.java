package com.tenchael.ispring.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenchael.ispring.domain.Reservation;
import com.tenchael.ispring.service.ReservationService;

@Controller
@RequestMapping("/reserv")
public class ReservationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReservationService reservationService;

	@RequestMapping(value = { "/", "/list" })
	public String list(Model model) {
		log.info("list reservations");
		List<Reservation> reservations = reservationService.list();
		model.addAttribute("list", reservations);
		return "list";
	}

	/**
	 * Access the resources with restful way
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping("/rest/player/{name}")
	public @ResponseBody List<Reservation> find(
			@PathVariable("name") String name, Model model) {
		log.info("list reservations with restful way");
		List<Reservation> reservations = reservationService
				.findByPlayerName(name);
		// model.addAttribute("list", reservations);
		return reservations;
	}

}
