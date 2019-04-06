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

    @RequestMapping("/admin/discountManagement")
    public ModelAndView toDisCountManagement(){
        return new ModelAndView("console/activityManagement/discountManagement");
    }

    @RequestMapping("/admin/preferenceManagement")
    public ModelAndView toPreferenceManagement(){
        return new ModelAndView("console/activityManagement/preferenceManagement");
    }

    @RequestMapping("/admin/recommendManagement")
    public ModelAndView tpRecommendManagement(){
        return new ModelAndView("console/foodManagement/foodManagement");
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

    @RequestMapping("/admin/frequentlyUsedMenuManagement")
    public ModelAndView toFrequentlyUsedMenuManagement(){
        return new ModelAndView("console/peopleManagement/frequentlyUsedMenuManagement");
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
