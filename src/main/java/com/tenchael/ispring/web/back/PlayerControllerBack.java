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
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			// @PageableDefault(sort = "id", direction = Direction.DESC)
			// Pageable pageable,
			Model model) {
		Pageable pageable = new PageRequest(pageIndex, pageSize,
				Direction.DESC, "id");
		Page<Player> pagedList = playerService.findAll(pageable);
		log.info("List player list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/player/list";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		Player player = playerService.getById(id);
		log.info("Get a player={}", player);
		model.addAttribute("bean", player);
		return "back/player/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		log.info("To create a player...");
		Player player = new Player();
		model.addAttribute("bean", player);
		model.addAttribute(OPRT, CREATE);
		return "back/player/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "id", required = true) Integer id,
			Model model) {
		Player player = playerService.getById(id);
		log.info("To edit a player={}", player);
		model.addAttribute("bean", player);
		model.addAttribute(OPRT, EDIT);
		return "back/player/form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid Player player, BindingResult bindingResult,
			Model model) {
		log.info("Save player={}", player);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "back/player/list";
		}
		playerService.save(player);
		return "redirect:/back/player/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value = "id", required = true) Integer id) {
		log.info("Delete a player id={}", id);
		playerService.delete(id);
		return "redirect:/back/player/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(
			@RequestParam(value = "condition", required = true) String condition,
			Model model) {
		log.info("Search sportTypes condition={}", condition);
		Pageable pageable = new PageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE,
				Direction.DESC, "id");
		Page<Player> pagedList = playerService.search(condition,
				pageable);
		log.info("List player list={}", pagedList.getContent());
		model.addAttribute("pagedList", pagedList);
		return "back/player/list";
	}
}
