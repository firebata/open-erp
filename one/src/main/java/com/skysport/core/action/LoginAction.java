package com.skysport.core.action;

import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.login.service.ILoginService;
import com.skysport.inerfaces.constant.WebConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Scope("prototype")
@Controller
@RequestMapping("/")
public class LoginAction {

    @Resource(name = "loginService")
    private ILoginService loginService;

    /**
     * 1，用户名，如果session存在该用户，执行2，否则执行3
     * 2，检验用户是否锁定，如果没有锁定，返回6，锁定返回5；
     * 3，用用户名查询用户信息，如果有该用户，执行4，否则执行5
     * 4，校验密码，如果密码正确，执行2，如果密码错误，执行5
     * 5，返回登录页面
     * 6，返回成功
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    @SystemControllerLog(description = "用户登录")
    public ModelAndView login(UserInfo user) throws Exception {
        return loginService.login(user);

    }

    @RequestMapping(value = "/loginout")
    @ResponseBody
    @SystemControllerLog(description = "用户登出")
    public ModelAndView loginout(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute(WebConstants.CURRENT_USER);
        return new ModelAndView("login");
    }

    @RequestMapping("icon/{cateogry}")
    public void getIcon(@PathVariable("cateogry") String cateogry,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(cateogry)) {
            cateogry = "";
        }

        String fileName = request.getSession().getServletContext().getRealPath("/")
                + "resource\\icons\\auth\\"
                + cateogry.toUpperCase().trim() + ".png";

        File file = new File(fileName);

        //判断文件是否存在如果不存在就返回默认图标
        if (!(file.exists() && file.canRead())) {
            file = new File(request.getSession().getServletContext().getRealPath("/")
                    + "resource/icons/auth/root.png");
        }

        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        int length = inputStream.read(data);
        inputStream.close();

        response.setContentType("image/png");

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }

}
