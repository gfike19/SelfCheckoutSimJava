package com.gfike.SelfCheckoutSim.controllers;

import com.gfike.SelfCheckoutSim.daos.CustOrderDao;
import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.CustOrder;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("cart")
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    CustOrderDao custOrderDao;
    // TODO: add warning about navigating away from screen?
    @Autowired
    ItemDao itemDao;

    @GetMapping
    public String checkoutGet(Model model, HttpSession session) {
        CustOrder order;

        if(session.getAttribute("order") != null) {
            order = (CustOrder)session.getAttribute("order");
        }
        else {
            order = new CustOrder();
        }
        HashMap<Item, Integer> cart =  (HashMap<Item, Integer>)session.getAttribute("cart");
        Set<Item> itemsOnly = cart.keySet();

        model.addAttribute("order", order);
        model.addAttribute("cart", itemsOnly);

        return "checkout";
    }

    @PostMapping
    public String checkoutPost(ServletRequest request, HttpSession session, Model model){

        int id = Integer.parseInt(request.getParameter("custCart"));
        Item i = itemDao.findById(id);

        CustOrder order = (CustOrder)session.getAttribute("order");
        order.update(i);

        return "redirect:/editCart";
    }


}
