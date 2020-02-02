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

@Controller
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    OrdersDao ordersDao;

    @GetMapping
    public String checkoutGet(Model model){
        Orders orders = new Orders();
        model.addAttribute("orders", orders);
        return "checkout";
    }

//    @PostMapping
//    public String checkoutPost(@ModelAttribute Orders orders, Model model){
//
//    }


}
