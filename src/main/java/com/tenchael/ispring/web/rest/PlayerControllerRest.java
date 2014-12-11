package com.tenchael.ispring.web.rest;

import static com.tenchael.ispring.common.Constants.CODE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE;
import static com.tenchael.ispring.common.Constants.DEFAULT_PAGE_SIZE;
import static com.tenchael.ispring.common.Constants.MESSAGE;
import static com.tenchael.ispring.common.Constants.OP_SUCCESS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
@RequestMapping(value = { "/rest/player", "/test/rest/player" })
public class PlayerControllerRest {

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
		Pageable pageable = new PageRequest(page, DEFAULT_PAGE_SIZE,
				Direction.DESC, "id");
		List<Player> players = playerService.findByNameLike(name, pageable)
				.getContent();
		return players;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody List<Player> search(
			@RequestParam(value = "condition", required = true) String condition,
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		log.info("search players with restful way");
		Sort sort = new Sort(Direction.DESC, "id");
		int size = (null != pageSize) ? pageSize : DEFAULT_PAGE_SIZE;
		Pageable page = new PageRequest(pageIndex, size, sort);
		List<Player> players = playerService.search(condition, page)
				.getContent();
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
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phone", required = false) String phone,
			Model model) {
		log.info("save a player,name=[]", name);
		Player player = new Player(name, phone);
		playerService.save(player);
		return "redirect:/rest/player/list/" + DEFAULT_PAGE;
	}

	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") Integer id) throws NotFoundException {
		log.debug("delete id={}", id);
		playerService.delete(id);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put(CODE, OP_SUCCESS);
		retMap.put(MESSAGE, settings.getProperty("message.delete.success"));
		return retMap;
	}

}
