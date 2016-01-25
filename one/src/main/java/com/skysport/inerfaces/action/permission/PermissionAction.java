package com.skysport.inerfaces.action.permission;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.*;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.permission.IPermissionService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 类说明:权限分配
 * Created by zhangjh on 2015/8/17.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/permission")
public class PermissionAction extends BaseAction {

    @Resource(name = "permissionService")
    private IPermissionService permissionService;

    @RequestMapping(value = "/show")
    @ResponseBody
    @SystemControllerLog(description = "点击权限页面")
    public ModelAndView show() {
        ModelAndView mav = new ModelAndView("system/permission/permission");
        return mav;
    }

    @RequestMapping(value = "/user-tab/{tabNo}")
    @ResponseBody
    public ModelAndView userTab(@PathVariable String tabNo) {

        ModelAndView mav = new ModelAndView("/system/permission/user-tab");
        mav.addObject("tabNo", tabNo);

        return mav;
    }

    /**
     * @return 返回所有角色信息
     */
    @RequestMapping(value = "/queryAllRole/{userId}", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "查询所有角色")
    public List<ZTreeNode> queryAllRole(@PathVariable String userId) {
        return permissionService.queryAllRole(userId);
    }

    /**
     * @return 返回所有权限信息
     */
    @RequestMapping(value = "/queryAllRes/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "查询所有资源")
    public List<ZTreeNode> queryAllRes(@PathVariable String roleId) {
        return permissionService.queryAllRes(roleId);
    }


    /**
     * @return 返回所有角色信息
     */
    @RequestMapping(value = "/saveRoleUser", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "分配角色")
    public Map<String, Object> saveRoleUser(@RequestBody RoleUser saveRoleUser) {
        permissionService.saveRoleUser(saveRoleUser);
        return rtnSuccessResultMap("分配角色成功");
    }

    /**
     * @return 返回所有资源信息
     */
    @RequestMapping(value = "/saveResourceRole", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "分配资源")
    public Map<String, Object> saveResourceRole(@RequestBody ResourceRole resourceRole) {
        permissionService.saveResourceRole(resourceRole);
        return rtnSuccessResultMap("分配资源成功");
    }


    /**
     * @return 返回所有资源信息
     */
    @RequestMapping(value = "/qrMenu", method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> selectMenu(HttpServletRequest request,HttpServletResponse response) {
        UserInfo user = (UserInfo) request.getSession().getAttribute(WebConstants.CURRENT_USER);
        String userId = user.getNatrualkey();
        String menuSessionKey = userId + "menusTotle";
        List<Menu> menusTotle = (List<Menu>) request.getSession().getAttribute(menuSessionKey);
        if (null == menusTotle || menusTotle.isEmpty()) {
            menusTotle = permissionService.selectMenu(userId);
            request.getSession().setAttribute(menuSessionKey, menusTotle);
        }
        return menusTotle;
    }

}
