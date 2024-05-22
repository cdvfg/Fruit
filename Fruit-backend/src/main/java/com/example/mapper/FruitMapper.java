package com.example.mapper;

import com.example.entity.Fruit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FruitMapper extends BaseMapper<Fruit> {

    List<Fruit> listXml();

    boolean deleteXml(Integer id);

    Fruit findXml(Integer id);

    boolean updateXml(Fruit fruit);

    boolean addXml(Fruit fruit);
}
