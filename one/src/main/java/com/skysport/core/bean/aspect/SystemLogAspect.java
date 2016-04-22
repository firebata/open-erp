package com.skysport.core.bean.aspect;

import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.annotation.SystemServiceLog;
import com.skysport.core.bean.LogInfo;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.model.log.LogService;
import com.skysport.core.utils.JsonUtils;
import com.skysport.core.utils.UserUtils;
import com.skysport.core.utils.UuidGeneratorUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * 说明:日志拦截
 * Created by zhangjh on 2016/1/11.
 */
@Aspect
@Component
public class SystemLogAspect {
    //注入Service用于把日志保存数据库
    @Autowired
    private LogService logService;
    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    //Service层切点
    @Pointcut("@annotation(com.skysport.core.annotation.SystemServiceLog)")
    public void serviceAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(com.skysport.core.annotation.SystemControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        UserInfo user = UserUtils.getUserFromSession(session);
        //请求的IP
        String ip = request.getRemoteAddr();
        try {
            //*========控制台输出=========*//
            String description = getControllerMethodDescription(joinPoint);
            String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
            String username = null;
            //未登录的情况
            if (null == user) {
                String currentMethod = request.getServletPath();
                if ("/login".equals(currentMethod)) {
                    username = request.getParameter("username");
                }
            } else {
                username = user.getAliases();
            }

            logger.debug("=====前置通知开始=====");
            logger.info("请求方法:" + method);
            logger.info("方法描述:" + description);
            logger.info("请求人:" + username);
            logger.debug("请求IP:" + ip);
            //*========数据库日志=========*//
            LogInfo log = SpringContextHolder.getBean("ilog");
            log.setNatrualkey(UuidGeneratorUtils.getNextId());
            log.setDescription(description);
            log.setMethod(method);
            log.setType("0");
            log.setRequestIp(ip);
            log.setExceptionCode(null);
            log.setExceptionDetail(null);
            log.setParams(null);
            log.setCreateBy(username);
//            log.setCreateDate(DatesUtil.getCurrentDate());
            //保存数据库
            logService.add(log);
            logger.debug("=====前置通知结束=====");
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        UserInfo user = UserUtils.getUserFromSession(session);
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params += JsonUtils.toJson(joinPoint.getArgs()[i]) + ";";
            }
        }
        try {
            String description = getControllerMethodDescription(joinPoint);
            String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
              /*========控制台输出=========*/
            logger.info("=====异常通知开始=====");
            logger.info("异常代码:" + e.getClass().getName());
            logger.info("异常信息:" + e.getMessage());
            logger.info("异常方法:" + method);
            logger.info("方法描述:" + description);
            logger.info("请求人:" + user.getName());
            logger.info("请求IP:" + ip);
            logger.info("请求参数:" + params);
               /*==========数据库日志=========*/
            LogInfo log = SpringContextHolder.getBean("ilog");
            log.setNatrualkey(UuidGeneratorUtils.getNextId());
            log.setDescription(description);
            log.setExceptionCode(e.getClass().getName());
            log.setType("1");
            log.setExceptionDetail(e.getMessage());
            log.setMethod(method);
            log.setParams(params);
            log.setCreateBy(user.getAliases());
//            log.setCreateDate(DateUtil.getCurrentDate());
            log.setRequestIp(ip);
            //保存数据库
            logService.add(log);
            logger.info("=====异常通知结束=====");
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
         /*==========记录本地异常日志==========*/
        Object[] arr = {joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params};
        String infoMsg = MessageFormat.format("异常方法:{0}异常代码:{1}异常信息:{2}参数:{3}", arr);
        logger.error(infoMsg);

    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
