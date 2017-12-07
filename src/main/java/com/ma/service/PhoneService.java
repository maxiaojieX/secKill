package com.ma.service;

import com.ma.entity.Phone;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */
public interface PhoneService {

    /**
     * 添加商品
     * @param phone
     * @param inputStream
     */
    void saveProduct(Phone phone, InputStream inputStream);

    /**
     * 查询全部
     * @return
     */
    List<Phone> findAll();

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Phone findById(Integer id);

    /**
     * 秒杀
     * @param id
     * @return
     */
    boolean secKill(Integer id);
}
