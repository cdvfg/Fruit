package com.example.service.impl;

import com.example.entity.Fruit;
import com.example.mapper.FruitMapper;
import com.example.service.FruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl extends ServiceImpl<FruitMapper, Fruit> implements FruitService {

    @Autowired
    private FruitMapper fruitMapper;

    @Override
    public List<Fruit> listXml() {
        return fruitMapper.listXml();
    }

    @Override
    public boolean deleteXml(Integer id) {
        return fruitMapper.deleteXml(id);
    }

    @Override
    public Fruit findXml(Integer id) {
        return fruitMapper.findXml(id);
    }

    @Override
    public boolean updateXml(Fruit fruit) {
        return fruitMapper.updateXml(fruit);
    }

    @Override
    public boolean addXml(Fruit fruit) {
        return fruitMapper.addXml(fruit);
    }
}
