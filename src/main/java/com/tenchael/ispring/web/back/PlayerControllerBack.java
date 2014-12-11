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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenchael.ispring.domain.Player;
import com.tenchael.ispring.service.PlayerService;

@Controller
@RequestMapping("/back/player")
public class PlayerControllerBack {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlayerService playerService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			@PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		log.info("list players");
		int page = (pageIndex != null) ? pageIndex : DEFAULT_PAGE;
		int size = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
		Page<Player> playersPage = playerService.findAll(page, size);
		List<Player> players = playersPage.getContent();
		model.addAttribute("players", players);
		model.addAttribute("totalPages", playersPage.getTotalPages());
		return "back/player/list";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String get(@PathVariable("id") Integer id, Model model) {
		log.info("get a player");
		Player player = playerService.findById(id);
		model.addAttribute("player", player);
		return "back/player/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("create a player");
		Player player = new Player();
		model.addAttribute("player", player);
		model.addAttribute(OPRT, CREATE);
		return "back/player/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid Player player, BindingResult bindingResult,
			Model model) {
		log.info("save person={}", player);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/player/list";
		}
		playerService.save(player);
		return "redirect:/back/player/list/" + DEFAULT_PAGE;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		Player player = playerService.findById(id);
		model.addAttribute(player);
		model.addAttribute(OPRT, EDIT);
		return "back/player/form";
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value = "page", required = false) Integer page) {
		log.debug("delete id={}", id);
		playerService.delete(id);
		return "redirect:/back/player/list/" + DEFAULT_PAGE;
	}
}
