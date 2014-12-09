package com.tenchael.ispring.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
