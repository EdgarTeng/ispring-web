package com.tenchael.ispring.rest;

import static com.tenchael.ispring.common.Constants.CODE;
import static com.tenchael.ispring.common.Constants.MESSAGE;
import static com.tenchael.ispring.common.Constants.OP_SUCCESS;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE_SIZE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenchael.ispring.common.Settings;
import com.tenchael.ispring.domain.Player;
import com.tenchael.ispring.exception.NotFoundException;
import com.tenchael.ispring.service.PlayerService;

@Controller
@RequestMapping("/rest/player")
public class PlayerController2 {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlayerService playerService;

	@Autowired
	private Settings settings;

	@RequestMapping("/get/{id}")
	public @ResponseBody Player findById(@PathVariable(value = "id") Integer id) {
		log.info("list players with restful way");
		Player player = playerService.findById(id);
		return player;
	}

	@RequestMapping("/find/{pageIndex}/{name}")
	public @ResponseBody List<Player> find(
			@PathVariable("pageIndex") Integer pageIndex,
			@PathVariable("name") String name) {
		log.info("list players with restful way");
		int page = (pageIndex != null) ? pageIndex : DEFAULT_PAGE;
		List<Player> players = playerService.findByNameLike(name, page,
				DEFAULT_PAGE_SIZE).getContent();
		return players;
	}

	@RequestMapping(value = { "/", "/list/{pageIndex}" })
	public @ResponseBody List<Player> list(
			@PathVariable("pageIndex") Integer pageIndex) {
		int page = (pageIndex != null) ? pageIndex : DEFAULT_PAGE;
		List<Player> list = playerService.findAll(page, DEFAULT_PAGE_SIZE)
				.getContent();
		return list;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			Model model) {
		log.info("save a player,name=[]", name);
		Player player = new Player(name, phone);
		playerService.insert(player);
		return "redirect:/rest/player/list/" + DEFAULT_PAGE;
	}

	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") Integer id) throws NotFoundException {
		log.debug("delete id={}", id);
		playerService.deleteById(id);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put(CODE, OP_SUCCESS);
		retMap.put(MESSAGE, settings.getProperty("message.delete.success"));
		return retMap;
	}

}
