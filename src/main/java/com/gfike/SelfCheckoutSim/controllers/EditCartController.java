package com.gfike.SelfCheckoutSim.controllers;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.AbstractController;

import com.gfike.SelfCheckoutSim.Helpers;
import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class EditCartController {
	
	@Autowired
	ItemDao itemDao;
	
	@RequestMapping(value = "/startOrder",method = RequestMethod.GET)
	public String editCartGet(Model model){
		List<Item> items = itemDao.findAll();
		model.addAttribute("items", items);
		//Object test = null;
		//model.addAttribute("test", test);
		return "editCart";
	}
	
	@RequestMapping(value = "/startOrder",method = RequestMethod.POST)
	public String editCartPost (Model model, ServletRequest request) {
		int id = Integer.parseInt(request.getParameter("shelf"));
		String action = request.getParameter("action");
		List <Item> cart = null;
		
		Item test = itemDao.findById(id);
		
		//model.addAttribute("test", test);
		model.addAttribute("msg", "item was added");
		return "redirect:startOrder";
	}
}
