package com.example.test_task.services;

import com.example.test_task.entities.Goods;
import com.example.test_task.repositories.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    private GoodsRepository goodsRepository;

    @Autowired
    public void setGoodsRepository(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    public void addGoods(Goods item) {
        goodsRepository.save(item);
    }

    public void deleteGoods(Long id) {
        goodsRepository.deleteById(id);
    }

    public List<Goods> getAllGoodsById(Long id) {
        return goodsRepository.findById(id).stream().collect(Collectors.toList());
    }

    // согласен, костыльно выглядит...
    public Goods getGoodsById(Long id) {
        return goodsRepository.findById(id).get();
    }
}
