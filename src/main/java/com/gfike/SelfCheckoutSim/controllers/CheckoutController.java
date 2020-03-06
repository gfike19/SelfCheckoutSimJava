package com.gfike.SelfCheckoutSim.controllers;

import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.daos.OrdersDao;
import com.gfike.SelfCheckoutSim.models.Item;
import com.gfike.SelfCheckoutSim.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.Order;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    OrdersDao ordersDao;
    // TODO: add warning about navigating away from screen?

    @GetMapping
    public String checkoutGet(Model model, HttpSession session) {
//        Orders order;
//
//        if(session.getAttribute("orders") =! null) {
//            order = (Orders)session.getAttribute("order");
//        }
//        else {
//            order = new Orders();
//        }
//        HashMap<Item, Integer> cart =  (HashMap<Item, Integer>)session.getAttribute("cart");
//
//        model.addAttribute("order", order);
//        model.addAttribute("cart", cart);
        return "checkout";
    }

//    @PostMapping
//    public String checkoutPost(ServletRequest request, HttpSession session, Model model){
//
//    }


}
