package com.ma.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ma.entity.Phone;
import com.ma.entity.PhoneExample;
import com.ma.job.MyJob;
import com.ma.mapper.PhoneMapper;
import com.ma.service.PhoneService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import javafx.beans.DefaultProperty;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */
@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneMapper phoneMapper;
    @Value("${qiniu.ak}")
    private String ak;
    @Value("${qiniu.sk}")
    private String sk;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void saveProduct(Phone phone, InputStream inputStream) {

        if(inputStream != null){
            String qiNiuName = uploadToQiNiu(inputStream);
            phone.setProductImage(qiNiuName);
        }

        phoneMapper.insert(phone);

        //添加定时任务到quartz，时间为活动结束时间，任务为更改数据库库存
        addScheduler(phone.getEndtime(),phone.getId());

        //新增list，用于抢购的原子操作
        Integer count = Integer.parseInt(phone.getOther());
        try(Jedis jedis = jedisPool.getResource()) {
            for(int i = 0;i<count;i++) {
                jedis.lpush("phone:"+phone.getId()+"list", String.valueOf(i));
            }
        }

    }

    /**
     * 添加任务调度
     * @param endTime
     * @param id
     */
    public void addScheduler(Date endTime,Integer id) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message",id);

        //根据job类动态创建一个jobDetail
        JobDetail jobDetail = JobBuilder
                .newJob(MyJob.class)
                .setJobData(jobDataMap)
                .withIdentity(new JobKey("phoneID:"+id,"sendMessageGroup"))
                .build();

        //根据提醒时间拼装出cron表达式
        Date date = endTime;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatDate = df.format(date);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime dateTime = formatter.parseDateTime(formatDate);

        StringBuilder cron = new StringBuilder("0")
                .append(" ")
                .append(dateTime.getMinuteOfHour())
                .append(" ")
                .append(dateTime.getHourOfDay())
                .append(" ")
                .append(dateTime.getDayOfMonth())
                .append(" ")
                .append(dateTime.getMonthOfYear())
                .append(" ? ")
                .append(dateTime.getYear());


        //创建Trigger
        ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

        //把创建好的job和trigger交给调度者
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException("添加任务调度失败");
        }
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Phone> findAll() {
        return phoneMapper.selectByExample(new PhoneExample());
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Phone findById(Integer id) {

        Phone phone;
        //先从redis查询
        try(Jedis jedis = jedisPool.getResource()) {

            String json = jedis.get("phone:"+id);
            if(json == null) {
                //如果redis没有，在从数据库查，
                phone = phoneMapper.selectByPrimaryKey(id);
                //放入redis
                jedis.set("phone:"+id, JSON.toJSONString(phone));
                return phone;
            }else {
                phone = JSON.parseObject(json,Phone.class);
                return phone;
            }

        }
    }

    @Override
    public boolean secKill(Integer id) {

        try(Jedis jedis = jedisPool.getResource()) {
            String result = jedis.lpop("phone:"+id+"list");
            if(result == null) {
                System.out.println("》》》》》》error<<<<<");
                return false;
            }else{
                System.out.println("√√√√√√√√√√√√√√√√√√√");
                return true;
            }
        }
    }

    /**
     * 上传文件到七牛
     * @param inputStream
     * @return
     */
    private String uploadToQiNiu(InputStream inputStream) {
//        七牛空间指定
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

//        根据ak sk获取token
        Auth auth = Auth.create(ak,sk);
        String uploadToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream),null,uploadToken);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            return defaultPutRet.key;
        } catch (IOException e) {
            throw  new RuntimeException("上传七牛云异常");
        }
    }




}
