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
    protected ItemDao itemDao;
	
	@RequestMapping(value = "/startOrder",method = RequestMethod.GET)
	public String editCartGet(Model model, HttpSession session){
		List<Item> items = itemDao.findAll();
		model.addAttribute("items", items);
		try {
			List<Item> cart = (List<Item>) session.getAttribute("cart");
            model.addAttribute("cart",cart);
		} catch (Exception e) {
			List<Item> cart = null;
			model.addAttribute("cart",cart);
		}
		
		return "editCart";
	}
	
	@RequestMapping(value = "/startOrder",method = RequestMethod.POST)
	public String editCartPost (Model model, ServletRequest request, HttpSession session) {
		int id = Integer.parseInt(request.getParameter("shelf"));
		String action = request.getParameter("action");
		
		if (action.equals("add")) {
			List<Item> cart = null;
			Item i = itemDao.findById(id);
			cart.add(i);
			session.setAttribute("cart", cart);
			model.addAttribute("msg", i.getName() + "item was added to the cart!");
		}
//
//		if (action == "remove") {
//
//		}
		
		
		return "redirect:startOrder";
	}
}
