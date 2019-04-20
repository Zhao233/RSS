//index.js
//获取应用实例
var url1 = "https://baike.baidu.com/pic/%E6%B6%AE%E7%BE%8A%E8%82%89/476036/0/3801213fb80e7bec3d233a53252eb9389a506b82?fr=lemma&ct=single#aid=0&pic=3801213fb80e7bec3d233a53252eb9389a506b82";

const app = getApp();

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),

    /*判断点击的侧栏目录是否为推荐或者其他一级菜单*/
    choose_status : 0,

    classfiyList: [//一级菜单
    ],

    recommend_circly:[//轮播展示的推荐菜的列表
    ],

    recommend_list: [//列表视图的推荐菜列表
    ],

    food_list : [
    ]
  },

  /* 获取一级菜单 */
  getMenuList: function () {
    var that = this;

    wx.request({
      url: "http://"+app.info.hostname+":"+app.info.port+"/customer/index/getMenuList",

      header: {
        'content-type': 'application/json' // 默认值
      },

      success(res) {
        if(res.data.status == "SUCCEED"){
        } else {
          app.showToast("网络请求失败");
          return ;
        }

        console.log(res);

        var temp_classfiyList = new Array;

        temp_classfiyList.push({ id: "", name: "推荐" });

        for (var x in res.data.menuList) {
          var rawData = res.data.menuList[x];

          var item = { id: rawData.id, name: rawData.name }

          temp_classfiyList.push(item);
        }

        that.setData({
          classfiyList: temp_classfiyList
        })

        console.log(res.data)
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })
  },

  /* 获取推荐菜品 */
  getRecommendFood : function() {
    var that = this;

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/index/getRecommendFoodList",

      header: {
        'content-type': 'application/json' // 默认值
      },

      success(res) {
        if (res.data.status == "SUCCEED") {
        } else {
          app.showToast("网络请求失败");
          return;
        }

        console.log(res);

        var temp_recommend_circly = new Array;
        var temp_recommend_list = new Array;

        for (var x in res.data.recommendFoodList) {
          var rawData = res.data.recommendFoodList[x];

          var item = { id: rawData.foodID, name: rawData.name, picUrl: rawData.picUrl, price: rawData.price }
          
          switch(rawData.type){
            case 1 ://列表
              temp_recommend_list.push(item);

              break;
            case 0 ://轮播
              temp_recommend_circly.push(item);
              break;
          }
        }

        that.setData({
          recommend_circly : temp_recommend_circly,
          recommend_list : temp_recommend_list,
        })

        console.log(res.data)
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })
  },

  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  onLoad: function () {
    this.getMenuList();
    this.getRecommendFood();
  },

  /* 点击左侧一级菜单，调整选中状态，并获取对应的菜品列表 */
  chooseMenuItem : function(e){
    var that = this;

    this.setData({
      choose_status: e.currentTarget.dataset.num
    });

    if(e.currentTarget.dataset.id == ""){//点击的是推荐
      return ;
    }

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/index/getFoodListByMenuID",
      data:{
        menuID:e.currentTarget.dataset.id
      },
      header: {
        'content-type': 'application/json' // 默认值
      },

      success(res) {
        if (res.data.status == "SUCCEED") {
        } else {
          app.showToast("网络请求失败");
          return;
        }

        console.log(res);

        var temp_food_list = new Array;

        for (var x in res.data.foodList) {
          var rawData = res.data.foodList[x];

          var item = { id: rawData.id, name: rawData.name, picUrl: rawData.picUrl, price: rawData.price }

          temp_food_list.push(item);
        }

        that.setData({
          food_list: temp_food_list
        })
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })


  },

  /* 添加菜品到购物车 */
  addFoodToCart : function(e){
    var id = e.currentTarget.dataset.foodid;
  
    var item={};

    for(var x in this.data.food_list){
      item = app.globalData.cartListRecord.get(id);

      if (item != undefined){
        break;
      }

      if (id == this.data.food_list[x].id){
        var temp_food = this.data.food_list[x];

        item = {"picUrl" : temp_food.picUrl,
                "name" : temp_food.name,
                "price" : temp_food.price,
                "num" : 1};

        break;
      }
    }

    if (app.globalData.cartListRecord.get(id) == undefined){
      app.globalData.cartListRecord.set(id, item);
    } else {
      var temp_item = app.globalData.cartListRecord.get(id);

      temp_item.num++;

      app.globalData.cartListRecord.set(id, temp_item);
    }

  }

})