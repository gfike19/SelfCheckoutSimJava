package com.gfike.SelfCheckoutSim.controllers;

import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.daos.OrdersDao;
import com.gfike.SelfCheckoutSim.models.Item;
import com.gfike.SelfCheckoutSim.models.Orders;
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
    OrdersDao ordersDao;
    // TODO: add warning about navigating away from screen?

    @GetMapping
    public String checkoutGet(Model model, HttpSession session) {
        Orders order;

        if(session.getAttribute("orders") != null) {
            order = (Orders)session.getAttribute("order");
        }
        else {
            order = new Orders();
        }
        HashMap<Item, Integer> cart =  (HashMap<Item, Integer>)session.getAttribute("cart");
        Set<Item> itemsOnly = cart.keySet();

        model.addAttribute("order", order);
        model.addAttribute("cart", itemsOnly);

        return "checkout";
    }

//    @PostMapping
//    public String checkoutPost(ServletRequest request, HttpSession session, Model model){
//
//    }


}
