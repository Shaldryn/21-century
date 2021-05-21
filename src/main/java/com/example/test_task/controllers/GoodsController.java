package com.example.test_task.controllers;

import com.example.test_task.entities.Goods;
import com.example.test_task.repositories.GoodsRepository;
import com.example.test_task.services.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items-list")
public class GoodsController {

    private GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

//    @GetMapping
//    public String showGoods(Model model) {
//        model.addAttribute("items", goodsService.getAllGoods());
//        model.addAttribute("item", new Goods());
//        return "items-list";
//    }

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

    @PostMapping("/add")
    public String newGoods(Goods item) {
        goodsService.addGoods(item);
        return "redirect:/items-list";
    }

    @DeleteMapping("/{id}")
    public String removeGoods(@PathVariable("id") Long id) {
        goodsService.deleteGoods(id);
        return "redirect:/items-list";
    }

}
