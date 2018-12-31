package com.gfike.SelfCheckoutSim.controllers;
import java.util.ArrayList;
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
    public ItemDao itemDao;
	
	@RequestMapping(value = {"/startOrder", "/addItem", "/removeItem", "/editCart"},method = RequestMethod.GET)
	public String editCartGet(Model model, HttpSession session){

        String msg = (String)session.getAttribute("msg");
        model.addAttribute("msg", msg);

	    List<Item> items = itemDao.findAll();
		model.addAttribute("items", items);

		try {
			List<Item> cart = (List<Item>) session.getAttribute("cart");
            model.addAttribute("cart",cart);
		} catch (Exception e) {
		    List<Item> cart = new ArrayList<Item>();
		    session.setAttribute("cart", cart);
			model.addAttribute("cart","");
		}

		return "editCart";
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public String addItem (Model model, ServletRequest request, HttpSession session) {

        int id = Integer.parseInt(request.getParameter("shelf"));
        String msg = "";
        List<Item> cart = (List<Item>) session.getAttribute("cart");


//        try {
//            cart = (List<Item>) session.getAttribute("cart");
//
//        } catch (Exception e) {
//            cart = new ArrayList<Item>();
//        }

        Item i = itemDao.findById(id);
        cart.add(i);
        session.setAttribute("cart", cart);
        msg = "Item has been added to cart!";
        session.setAttribute("msg",msg);
        model.addAttribute("msg", msg);
        return "redirect:startOrder";
    }

    @RequestMapping(value="/removeItem", method=RequestMethod.POST)
    public String removeItem (Model model, ServletRequest request, HttpSession session) {

        int id = Integer.parseInt(request.getParameter("cart"));
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        Item i = itemDao.findById(id);
        cart.remove(i);
        session.setAttribute("cart", cart);
        String msg = "Item has been removed from the cart.";

        session.setAttribute("msg",msg);
        model.addAttribute("msg", msg);
        return "redirect:startOrder";
    }

}
