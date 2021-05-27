package com.example.test_task.controllers;

import com.example.test_task.dto.Foo;
import com.example.test_task.entities.Order;
import com.example.test_task.entities.OrderLine;
import com.example.test_task.services.GoodsService;
import com.example.test_task.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private Set<OrderLine> orderLines = new HashSet<>();
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrdersWithFilter(Model model, @RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            model.addAttribute("orders", orderService.findAll());
        } else {
            model.addAttribute("orders", orderService.findById(id));
        }
        model.addAttribute("id", id);
        return "orders-list";
    }

    @GetMapping("/add")
    public String createGood(Model model) {
        Order order = new Order();
        orderLines.forEach(orderLine -> orderLine.setOrder(order));
        order.setOrderLines(orderLines);
        model.addAttribute("order", order);
       // model.addAttribute("orderLines", orderLines);
        return "order-edit";
    }

    @GetMapping("/edit")
    public String showEditGoodsForm(Model model, @RequestParam(value = "id") Long id) {
        Order order = orderService.findById(id);
        orderLines.forEach(orderLine -> orderLine.setOrder(order));
        order.setOrderLines(orderLines);
        model.addAttribute("order", order);
        return "order-edit";
    }

    @PostMapping("/orderlines")
    public String getOrderLines(@RequestParam(value = "id", required = false) Long id,
                                @ModelAttribute(value = "foo") Foo items) {
        orderLines.clear();
        items.getCheckedItems().stream()
                .forEach(entry -> {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setGoods(goodsService.getGoodsById(entry));
                    orderLines.add(orderLine);
                });
        if (id != null) {
            return "redirect:/orders/edit";
        } else {
            return "redirect:/orders/add";
        }
    }

    @PostMapping("/save")
    public String modifyGoods(@ModelAttribute(value = "order") Order order) {
        orderService.save(order);
        return "redirect:/orders";
    }

    @DeleteMapping("/delete/{id}")
    public String removeGoods(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
