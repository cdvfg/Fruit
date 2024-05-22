package com.example.service;

import com.example.entity.Fruit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FruitService extends IService<Fruit> {

    List<Fruit> listXml();

    boolean deleteXml(Integer id);

    Fruit findXml(Integer id);

    boolean updateXml(Fruit fruit);

    boolean addXml(Fruit fruit);
}
