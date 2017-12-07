package com.ma.controller;

import com.ma.entity.Phone;
import com.ma.service.PhoneService;
import com.ma.util.AjaxStateJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */
@Controller
public class HomeController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/")
    public String home(Model model) {
        List<Phone> phoneList = phoneService.findAll();
        model.addAttribute("phoneList",phoneList);
        return "home";
    }


    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/save")
    public String save(Phone phone, String sTime, String eTime, MultipartFile image)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date startTime = sdf.parse(sTime);
            Date endTime = sdf.parse(eTime);
            phone.setStarttime(startTime);
            phone.setEndtime(endTime);

            phoneService.saveProduct(phone,image.getInputStream());

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期格式异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/phone/detil/{id:\\d+}")
    public String detil(@PathVariable Integer id, Model model) {
        Phone phone = phoneService.findById(id);
        model.addAttribute("phone",phone);
        return "detil";
    }

    @GetMapping("/phone/secKill")
    @ResponseBody
    public AjaxStateJson secKill(Integer id) {

        if(phoneService.secKill(id)) {
            return new AjaxStateJson("success","秒杀成功");
        }else{
            return new AjaxStateJson("error","抢光了");
        }
    }

}
