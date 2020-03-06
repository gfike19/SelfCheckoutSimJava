package com.gfike.SelfCheckoutSim.controllers;
import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("editCart")
public class EditCartController {

    @Autowired
    public ItemDao itemDao;

// old way below
//@RequestMapping(value ={"/editCart"}, method = RequestMethod.GET)
    @GetMapping
    public String editCartGet(Model model,  HttpSession session){

        List<Item> items;
        if (session.getAttribute("items") != null) {
            items = (List<Item>)session.getAttribute("cart");
        } else {
            items = itemDao.findAll();
        }

        HashMap<Item, Integer> cart;
        if (session.getAttribute("cart") != null){
            cart = (HashMap<Item, Integer>)session.getAttribute("cart");
        }
        else {
            cart = new HashMap<Item, Integer>();
        }

        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        model.addAttribute("items", items);

        String msg = (String)session.getAttribute("msg");
        model.addAttribute("msg", msg);
        return "editCart";
    }

    // old way below
    //    @RequestMapping(value="/editCart", params="add", method = RequestMethod.POST)
    @PostMapping(params = "add")
    public String addItem (ServletRequest request, HttpSession session, Model model) {
        HashMap<Item, Integer> cart= (HashMap<Item, Integer>)session.getAttribute("cart");
        String msg = "";
        if (request.getParameter("shelf").isEmpty()){
             msg = "Select an item to add to the cart!";
            model.addAttribute("msg", msg);
            session.setAttribute("msg", msg);
            return "redirect:/editCart";
        }

        int id = Integer.parseInt(request.getParameter("shelf"));
        Item i = itemDao.findById(id);

        if(cart.isEmpty() || !cart.containsKey(i)) {
            cart.put(i, 1);
        }
        else {
                int oldVal = cart.get(i);
                int newVal = oldVal + 1;
                cart.put(i, newVal);
            }
        
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        return "redirect:/editCart";
    }

    // @requestparam required = false is needed in the event the user doesn't select an item
    @PostMapping(params = "update")
    public String updateCart (@RequestParam(required = false) List<String> markedItem,Model model,
  HttpServletRequest request, HttpSession session,
  @RequestParam(required = false) List<Integer> currCount,
  @RequestParam(required = false) List<String> itemId) {
        String msg = "";
        HashMap<Item, Integer> cart = (HashMap<Item, Integer>)session.getAttribute("cart");
        HashMap<Item, Integer> clone = (HashMap<Item, Integer>) cart.clone();

            for (HashMap.Entry<Item, Integer> entry : cart.entrySet()) {
                Item item = entry.getKey();
                int id = item.getUid();
                int pos = itemId.indexOf(id + "");

                if (itemId.contains(id + "")) {
                    int oldVal = entry.getValue();
                    int change = currCount.get(pos);
                    int newVal = oldVal + change;

                    if(newVal == 0){
                        clone.remove(entry.getKey(), entry.getValue());
                    }

                    clone.replace(entry.getKey(), newVal);
                }

                if (markedItem != null && markedItem.contains(id + "")) {
                    clone.remove(entry.getKey(), entry.getValue());
                }

                currCount.remove(pos);
                itemId.remove(pos);
            }

        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);
        model.addAttribute("cart", clone);
        session.setAttribute("cart", clone);

        return "redirect:/editCart";
    }

}