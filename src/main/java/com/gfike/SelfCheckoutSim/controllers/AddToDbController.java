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
public class AddToDbController {
	@Autowired
	ItemDao itemDao;
	//private HttpSession session;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index(Model model){
		List<Item> items = itemDao.findAll();
		model.addAttribute("items", items);
		return "index";
	}
	
	
	@RequestMapping(value= {"/addtodb", "redirect:addtodb"}, method = RequestMethod.GET)
	public String CreateNewItemGet (HttpSession session, Model model) {
		
		if (session.getAttribute("msg") != "") {
			String msg = (String)session.getAttribute("msg");
			model.addAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		return "addtodb";
	}
	
	@RequestMapping(value="/addtodb", method = RequestMethod.POST)
	public String CreateNewItemPost (HttpServletRequest request, HttpSession session) {
		
		//get values from form
		String name = request.getParameter("name");
		float price = Float.parseFloat(request.getParameter("price"));
		String plu = request.getParameter("plu");
		
		if (plu.isEmpty()) {
			plu = Helpers.createPlu(name);
		}
		
		boolean pLb = false, fs = false;
		
		
		try {
			String tempPlb = request.getParameter("pLb");
			
			if (tempPlb.equals("true")) {
				pLb = true;
			}
		} catch (Exception e) {
			fs = false;
		}
		
		try {
			String tempFs = request.getParameter("fs");
			if (tempFs.equals("true")) {
				fs = true;
			}
		} catch (Exception e) {
			fs = false;
		}
		
		
		Item i = new Item(name,price, pLb, plu, fs);
		itemDao.save(i);
		String msg = "Item " + name + " has been successfully added to the database!";
		session.setAttribute("msg", msg);
		return "redirect:addtodb";
	}
	
	
	
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	public String error () {
		return "error";
	}
}
