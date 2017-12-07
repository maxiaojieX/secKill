package com.ma.job;

import com.ma.util.MySpringContext;
import org.quartz.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(jobDataMap.get("message"));
        Integer  phoneId = (Integer) jobDataMap.get("message");
        System.out.println(">>>>>>这是发送端的存值: "+phoneId);

        JmsTemplate jmsTemplate = (JmsTemplate) MySpringContext.wantBean("jmsTemplate");

        jmsTemplate.send("secKill-Quere", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String message = "{\"id\":\""+phoneId+"\"}";
                TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
            }
        });



    }
}
