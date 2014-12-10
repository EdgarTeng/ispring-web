package com.tenchael.ispring.web.fore;

import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE_SIZE;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/player")
public class PlayerController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlayerService playerService;

	@RequestMapping("/find/{pageIndex}/{name}")
	public String find(@PathVariable("pageIndex") Integer pageIndex,
			@PathVariable("name") String name, Model model) {
		log.info("list players with restful way");
		int page = (pageIndex != null) ? pageIndex : DEFAULT_PAGE;
		List<Player> players = playerService.findByNameLike(name, page,
				DEFAULT_PAGE_SIZE).getContent();
		model.addAttribute("list", players);
		return "/player/list";
	}

	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "page", defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		log.info("list players");
		int size = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
		List<Player> paging = playerService.findAll(pageIndex, size)
				.getContent();
		model.addAttribute("list", paging);
		return "/player/list";
	}

	@RequestMapping(value = "/create")
	public String create(Model model) {
		log.info("create a player");
		Player player = new Player();
		model.addAttribute("player", player);
		return "player/form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String createOnSubmit(@Valid Player player,
			BindingResult bindingResult, Model model) {
		log.debug("create person={}", player);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "/player/list";
		}
		playerService.save(player);
		return "redirect:/player/list";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		Player player = playerService.findById(id);
		model.addAttribute(player);
		return "/player/form";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editOnSubmit(@Valid Player player,
			BindingResult bindingResult, Model model) {
		log.debug("edit person={}", player);
		if (bindingResult.hasErrors()) {
			log.warn("validation error={}", bindingResult.getModel());
			model.addAllAttributes(bindingResult.getModel());
			return "/player/list";
		}
		playerService.save(player);
		return "redirect:/player/list";
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(
			@RequestParam(value = "page", required = false) Integer page,
			@PathVariable("id") Integer id) {
		log.debug("delete id={}", id);
		playerService.delete(id);

		return "redirect:/player/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phone", required = false) String phone) {
		log.debug("add a player name={}", name);
		Player player = new Player(name, phone);
		player = playerService.save(player);

		return "redirect:/player/list";
	}

}
