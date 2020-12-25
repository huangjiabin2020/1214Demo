package com.binge.handler;

import com.binge.exception.MyException;
import com.binge.webentity.AxiosResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: JiaBin Huang
 * @date: 2020年 12月17日
 * @description:
 **/
@RestControllerAdvice
public class MyExceptionHandler {
    /**
     * 控制层参数校验
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected AxiosResult validator(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        ArrayList<Map> result = new ArrayList<>();
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(error -> {
                HashMap<String, String> map = new HashMap<>(10);
                if (error instanceof FieldError){
                    FieldError fieldError = (FieldError) error;
                    //哪个属性错了
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    String code = fieldError.getCode();
                    map.put("field",field);
                    map.put("defaultMessage",defaultMessage);
                    map.put("code",code);
                    result.add(map);
                }
            });
        }
        return AxiosResult.error(result);
    }

    @ExceptionHandler(MyException.class)
    public AxiosResult myExceptionHandler(MyException ex){
        Integer code = ex.getCode();
        String msg = ex.getMsg();
        Object data = ex.getData();
        return AxiosResult.error(new HashMap<String ,Object>(){{
            put("code",code);
            put("msg",msg);
            put("data",data);
        }});
    }
}
