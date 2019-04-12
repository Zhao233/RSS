package com.example.demo.controller.adminController.foodInfoCntroller;


import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.foodInfo.Style;
import com.example.demo.domain.user.CookerRole;
import com.example.demo.model.Food_All;
import com.example.demo.repository.foodInfo.MenuDao;
import com.example.demo.repository.foodInfo.StyleDao;
import com.example.demo.repository.user.CookerRoleDao;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.util.COSUtil;
import com.example.demo.util.DateTranslator;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private StyleDao styleDao;

    @Autowired
    private CookerRoleDao cookerRoleDao;


    /**
     * 根据menuId获取一级菜单下所有的菜品
     * */
    @ResponseBody
    @RequestMapping(value="/getFoodsByMenuId")
    public Map<String, Object> getFoodsByMenuId(@RequestParam(name = "menuId", required = true) long menuId,
                                                @RequestParam(name = "search", defaultValue = "") String search,
                                                @RequestParam(name = "offset") int offset,
                                                @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Food> page;

        page = foodService.getFoodList(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCEED");
        return map;
    }

    /**
     *获取创建菜品时所需的信息
     */
    @ResponseBody
    @RequestMapping(value = "/getFoodInfo")
    public Map<String, Object> getFoodInfo(){
        Map<String, Object> map = new HashMap();

        List<Menu> list_menu = menuDao.findAll();
        List<Style> list_style = styleDao.findAll();
        List<CookerRole> list_role = cookerRoleDao.findAll();

        map.put("list_menu", list_menu);
        map.put("list_style", list_style);
        map.put("list_role", list_role);

        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}")
    public Map<String, Object> getOneById(@PathVariable String id)  {
        HashMap<String, Object> map = new HashMap<>();

        Food food = foodService.getOne(Long.parseLong(id));

        map.put("food",food);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getFoodList")
    public Map<String, Object> getFoodList(@RequestParam(name = "search") String search,
                                           @RequestParam(name = "offset") int offset,
                                           @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Food> page;
        List<Food_All> page_all = new LinkedList<>();//界面所需要展示

        page = foodService.getFoodList(search, pageable);

        for(Food food : page.getContent()){
            long id = food.getId();
            String picUrl = food.getPicUrl();
            String foodName = food.getName();
            String menuName = menuDao.getMenuNameById(food.getMenuId());

            //获取style的名字合集
            List<String> styles = new LinkedList<>();
            String[] styleIDs = food.getStylesId().split("_");
            for(String styleID : styleIDs){
                styles.add(styleDao.getStyleNameByStyleID(Long.parseLong(styleID)));
            }

            //获取厨师身份名字
            String roleName = cookerRoleDao.getRoleNameByID(food.getRole());
            int enable = food.getEnable();
            Timestamp createTime = food.getCreateTime();
            Timestamp updateTime = food.getCreateTime();


            page_all.add(new Food_All(id, picUrl, foodName, menuName, styles, roleName, enable, createTime, updateTime));
        }

        map.put("total", page != null ? page_all.size() : 0);
        map.put("rows", page != null ? page_all : "");

        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Map<String, Object> addFood(MultipartHttpServletRequest request,
                                       @RequestParam(name = "menuId") long menuId,
                                       @RequestParam(name = "name") String name,
                                       @RequestParam(name = "role") String role,
                                       @RequestParam(name = "styles") String styles,
                                       @RequestParam(name = "enable") int enable){
        Map<String, Object> map = new HashMap();
        MultipartFile multipartFile = request.getFile("file");

        File file = null;
        String file_url = "";

        file = new File(multipartFile.getOriginalFilename());

        try {
            byte[] bytes = multipartFile.getBytes();

            OutputStream outputStream = new FileOutputStream(file);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            bufferedOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file_url = COSUtil.uploadFile(file);

        Food food = new Food();
        food.setPicUrl(file_url);
        food.setName(name);
        food.setMenuId(menuId);
        food.setRole(Integer.valueOf(role));
        food.setStylesId(styles);
        food.setEnable(enable);
        food.setCreateTime(TimeUtil.getTimeNow());

        foodService.addFood(food);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> updateFood(@RequestParam(name = "id") long id,
                                          MultipartHttpServletRequest request,
                                          @RequestParam(name = "menuId") long menuId,
                                          @RequestParam(name = "name") String name,
                                          @RequestParam(name = "role") int role,
                                          @RequestParam(name = "createTime") String createTime,
                                          @RequestParam(name = "styles") String styles,
                                          @RequestParam(name = "picName") String picName,
                                          @RequestParam(name = "enable") int enable){
        Map<String, Object> map = new HashMap();

        MultipartFile multipartFile = request.getFile("file");

        File file = null;
        String file_url = "";

        file = new File(multipartFile.getOriginalFilename());

        try {
            byte[] bytes = multipartFile.getBytes();

            OutputStream outputStream = new FileOutputStream(file);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            bufferedOutputStream.write(bytes);

            file_url = COSUtil.uploadFile(file);//上传更新的照片

            COSUtil.deleteFile(picName);//删除原有图片
        } catch (Exception e) {
            e.printStackTrace();
        }


        Food food = new Food();
        food.setId(id);
        food.setPicUrl(file_url);
        food.setName(name);
        food.setMenuId(menuId);
        food.setRole(role);
        food.setStylesId(styles);
        food.setCreateTime(TimeUtil.StringToTimeStamp(createTime));
        food.setUpdateTime(TimeUtil.getTimeNow());
        food.setEnable(enable);

        foodService.updateFood(food);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String, Object> deleteFood(@RequestParam(name = "list_ID") List<Long> list_ID){
        Map<String, Object> map = new HashMap();

        for(Long id : list_ID){
            foodService.deleteFood(id);
        }

        map.put("status","SUCCEED");

        return map;
    }
}
