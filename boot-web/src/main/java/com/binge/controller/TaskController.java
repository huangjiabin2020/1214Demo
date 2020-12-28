package com.binge.controller;

import com.binge.entity.SpringScheduledCron;
import com.binge.service.ISpringScheduledCronService;
import com.binge.webentity.AxiosResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月23日
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("task")
public class TaskController {

    @Autowired
    @Qualifier("myTaskScheduler")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    ISpringScheduledCronService iSpringScheduledCronService;

    private Map<Integer, SpringScheduledCron> map = new ConcurrentHashMap<>();

    /**
     * 构造方法结束后执行，可以进行一些初始化操作
     */
    @PostConstruct
    public void init() {

    }


    /**
     * 获取当前map的所有定时任务及其运行情况
     *
     * @return
     */
    @GetMapping
    public ResponseEntity getCurrentScheduleTask() {
        return ResponseEntity.ok(map);
    }

    @GetMapping("{id}")
    public AxiosResult findById(@PathVariable int id) {
        return AxiosResult.success(iSpringScheduledCronService.getById(id));
    }

    @GetMapping("execute/{id}")
    public AxiosResult executeById(@PathVariable int id) {

        return AxiosResult.success(iSpringScheduledCronService.getById(id));
    }

    @DeleteMapping("{id}")
    public String stop(@PathVariable Integer id) {
        ScheduledFuture future = map.get(id).getScheduledFuture();
        //true表示直接干死  不管你执行到了哪一步
        //false 表示把后续操作执行完 再一耙子怼死
        future.cancel(false);
        map.remove(id);
        return "success";
    }


    @PostMapping("fileTask")
    public AxiosResult fileTask(@RequestPart Part part, String cronExpression) throws IOException, ClassNotFoundException {
        SpringScheduledCron thirdPart = new SpringScheduledCron()
                .setCronExpression(cronExpression)
                .setCronKey("thirdPart");
        iSpringScheduledCronService.save(thirdPart);
        ObjectInputStream ois = new ObjectInputStream(part.getInputStream());
        Object o = ois.readObject();
        if (o instanceof Runnable) {
            Runnable runnable = (Runnable) o;
            threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cronExpression));
            map.put(thirdPart.getCronId(), thirdPart);
        }
        return AxiosResult.success();
    }

    @PutMapping
    public String edit(@RequestBody SpringScheduledCron cron) throws Exception {
        iSpringScheduledCronService.updateById(cron);
        executeCron(cron);
        return "success";
    }

    /**
     * 通过文件上传来的任务对象，修改不能通过类名拿到对象，所以必须再传一次这个对象
     * @param cron
     * @param part
     * @return
     * @throws Exception
     */
    @PutMapping("thirdPart")
    public String editThirdPart(@RequestBody SpringScheduledCron cron,@RequestPart Part part) throws Exception {

        iSpringScheduledCronService.updateById(cron);
        stop(cron.getCronId());

        ObjectInputStream ois = new ObjectInputStream(part.getInputStream());
        Object o = ois.readObject();
        if (o instanceof Runnable) {
            Runnable runnable = (Runnable) o;
            threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron.getCronExpression()));
            map.put(cron.getCronId(), cron);
        }
        executeCron(cron);
        return "success";
    }

    /**
     * 先停掉原来的，再执行新的 Cron
     * @param cron
     * @throws Exception
     */
    private void executeCron(SpringScheduledCron cron) throws Exception {
        SpringScheduledCron springScheduledCron = map.get(cron.getCronId());
        ScheduledFuture future = null;
        if (springScheduledCron != null) {
            future = springScheduledCron.getScheduledFuture();
        }

//        先把原来的停掉
        if (future != null) {
            future.cancel(false);
        }

//        再加入新的
        Method method = getMethod(cron.getCronKey());
        Class<?> aClass = Class.forName(cron.getCronKey());
        ScheduledFuture<?> newFuture = threadPoolTaskScheduler.schedule(
                new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        //todo  根据cron的className，反射获取其方法，再包装成runnable执行
                        method.invoke(aClass.newInstance());
                    }
                }, new CronTrigger(cron.getCronExpression()));
        cron.setScheduledFuture(newFuture);
        map.put(cron.getCronId(), cron);
    }


    private Method getMethod(String cronKey) throws Exception {
        Class<?> aClass = Class.forName(cronKey);
        Method run = aClass.getDeclaredMethod("run");
        return run;

    }

    @PostMapping
    public String create(@RequestBody SpringScheduledCron cron) {
        iSpringScheduledCronService.save(cron);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, new CronTrigger(cron.getCronExpression()));
        cron.setScheduledFuture(future);
        map.put(cron.getCronId(), cron);
        return "success";
    }


    @PreDestroy
    public void destroy() {
        map = null;
    }


}
