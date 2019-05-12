package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class consoleController {
    @RequestMapping("/admin/getMenu")
    public ModelAndView getMenu(){
        return new ModelAndView("console/menu");
    }

    /**
     * 活动管理
     * */
    @RequestMapping("/admin/index")
    public ModelAndView toIndex(){
        return new ModelAndView("console/index");
    }

    /**
     * 活动管理
     * */
    @RequestMapping("/admin/activityManagement")
    public ModelAndView toActivityManagement(){
        return new ModelAndView("console/activityManagement/activityManagement");
    }

    /**
     * 菜单管理
     * */

    @RequestMapping("/admin/foodManagement")
    public ModelAndView toFoodManagement(){
        return new ModelAndView("console/foodManagement/foodManagement");
    }

    @RequestMapping("/admin/menuManagement")
    public ModelAndView toMenuManagement(){
        return new ModelAndView("console/foodManagement/menuManagement");
    }

    /**
     * 人员管理
     * */

    @RequestMapping("/admin/cookerManagement")
    public ModelAndView toCookerManagement(){
        return new ModelAndView("console/peopleManagement/cookerManagement");
    }

    @RequestMapping("/admin/userManagement")
    public ModelAndView toUserManagement(){
        return new ModelAndView("console/peopleManagement/userManagement");
    }

    @RequestMapping("/admin/waiterManagement")
    public ModelAndView toUserMenuManagement(){
        return new ModelAndView("console/peopleManagement/waiterManagement");
    }




}
