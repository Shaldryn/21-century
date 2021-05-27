package com.example.test_task.controllers;

import com.example.test_task.dto.Foo;
import com.example.test_task.entities.Goods;
import com.example.test_task.entities.Order;
import com.example.test_task.repositories.GoodsRepository;
import com.example.test_task.services.GoodsService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/items-list")
public class GoodsController {

    private GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping
    public String showGoodsWithFilter(Model model, @RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            model.addAttribute("items", goodsService.getAllGoods());
        } else {
            model.addAttribute("items", goodsService.getAllGoodsById(id));
        }
        model.addAttribute("item", new Goods());
        model.addAttribute("id", id);
        return "items-list";
    }

    @GetMapping("/add")
    public String showAddGoodsForm(Model model) {
        Goods goods = new Goods();
        model.addAttribute("item", goods);
        return "item-edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditGoodsForm(Model model, @PathVariable("id") Long id) {
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("item", goods);
        return "item-edit";
    }

    @PostMapping("/edit")
    public String modifyGoods(@ModelAttribute(value = "item") Goods item) {
        goodsService.addGoods(item);
        return "redirect:/items-list";
    }

    @DeleteMapping("/{id}")
    public String removeGoods(@PathVariable("id") Long id) {
        goodsService.deleteGoods(id);
        return "redirect:/items-list";
    }

    @GetMapping("/select")
    public String showSelectGoods(Model model, @RequestParam(value = "orderid", required = false) Long id,
                                  @ModelAttribute(value = "order") Order order) {
        Foo foo = new Foo();
        model.addAttribute("items", goodsService.getAllGoods());
        model.addAttribute("order", order);
        model.addAttribute("foo", foo);
        return "items-select";
    }

}
