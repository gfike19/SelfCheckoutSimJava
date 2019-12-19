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

    /*
    TODO remove items from all items list that have already been put in cart
     */
    @Autowired
    public ItemDao itemDao;

// old way below
//@RequestMapping(value ={"/editCart"}, method = RequestMethod.GET)
    @GetMapping
    public String editCartGet(Model model,  HttpSession session){

        HashMap<Item, Integer> cart;
        List<Item> items;

        if (session.getAttribute("cart") != null){
            cart = (HashMap<Item, Integer>)session.getAttribute("cart");
            items = itemDao.findAll();
            Set<Item> presentItems = cart.keySet();
            //TODO convert set to array to find items that are in cart and remove from allitemss

            for(int i = 0; i < presentItems.size(); i++){
                if(items.contains(presentItems.){

                }
            }
        }
        else{
            cart = new HashMap<Item, Integer>();
            items = itemDao.findAll();
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

        cart.put(i, 1);

        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        return "redirect:/editCart";
    }

    /* TODO need to fix issues where you cannot remove one item and increment another
    *   TODO fix incrementing*/
    // @requestparam required = false is needed in the event the user doesn't select an item
    @PostMapping(params = "update")
    public String updateCart (@RequestParam(required = false) List<String> markedItem,Model model,
  HttpServletRequest request, HttpSession session,
  @RequestParam(required = false) List<Integer> currCount,
  @RequestParam(required = false) List<String> itemId) {
        String msg = "";
        HashMap<Item, Integer> cart = (HashMap<Item, Integer>)session.getAttribute("cart");

        //unsure why try catch prevents issues
        try {
        Iterator cartIt = cart.entrySet().iterator();

        if(markedItem != null && markedItem.size() > 0) {
            while (cartIt.hasNext()) {
                Map.Entry entry = (Map.Entry)cartIt.next();
                Item item = (Item)entry.getKey();
                String id = "" + item.getUid();

                // removes item from cart and from lists
                if (markedItem.contains(id)){
                    cart.remove(item);
                }

                if (itemId.contains(id)){
                    int pos = itemId.indexOf(id);
                    currCount.remove(pos);
                    itemId.remove(pos);
                }
            }
        }

            while (cartIt.hasNext()) {

                Map.Entry entry = (Map.Entry) cartIt.next();
                Item item = (Item) entry.getKey();
                int oldVal = cart.get(item);
                int newVal = oldVal + (int) entry.getValue();

                if(newVal >= 0){
                    cart.replace(item, oldVal, newVal);
                }
            }
        } catch (Exception e){
        }

        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        return "redirect:/editCart";
    }

}