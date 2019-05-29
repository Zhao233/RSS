//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    frequentlyUsedFoodList:new Map,
    frequentlyUsedFoodList_list : [],
    userInfo: {},

    isEmpty : false,

    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  getFrequentlyUsedFoodListData : function (e){
    var that = this;

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/quickService/getFrequentlyUsedFoodList",

      header: {
        'content-type': 'application/json' // 默认值
      },

      data: {
        "userID": app.globalData.userInfo.openid
      },

      success(res) {
        app.internetResponseHandler(res);

        var frequentlyUsedFoodList = new Map;

        var frequentlyUsedModel = res.data.FrequentlyUsedFood;

        var foodList = frequentlyUsedModel.foodList;
        
        if(foodList.length == 0){
          that.setData({
            isEmpty : true
          })
        }

        var styleList = frequentlyUsedModel.styleList;
        var numList = frequentlyUsedModel.numList;

        for (var x in foodList) {

          var item = {
            "id": foodList[x].id,
            "styleID" : styleList[x], 
            "picUrl": foodList[x].picUrl,
            "name": foodList[x].name,
            "price": foodList[x].price,
            "num": numList[x]
          };

          frequentlyUsedFoodList.set(item.id, item);
        }

        that.setData({
          frequentlyUsedFoodList: frequentlyUsedFoodList
        })

        that.getFrequentlyUsedFoodList();
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })
  },

  getFrequentlyUsedFoodList : function(e) {
    var list = [];
    
    for(var x of this.data.frequentlyUsedFoodList){
      list.push(x[1]);
    }

    this.setData({
      frequentlyUsedFoodList_list : list
    })
  },
  //将购物车的商品加一
  addOne : function(e) {
    var id = e.currentTarget.dataset.foodid;
    var temp = this.getOneFromfrequentlyUsedFoodListList(id);

    var index = temp.index;
    var item_cart = temp.data;

    item_cart.num++;

    this.updatefrequentlyUsedFoodListData(id, index, item_cart,0);
  }, 

  //将购物车的商品减一
  removeOne : function(e) {
    var id = e.currentTarget.dataset.foodid;
    var temp = this.getOneFromfrequentlyUsedFoodListList(id);

    var index = temp.index;
    var item_frequentlyUsedFood = temp.data;

    if(item_frequentlyUsedFood.num == 1){//将减少至0，则清除这条记录
      this.updatefrequentlyUsedFoodListData(id, index, item_frequentlyUsedFood, 1);
    } else {
      item_frequentlyUsedFood.num--;

      this.updatefrequentlyUsedFoodListData(id, index, item_frequentlyUsedFood, 0);
    }
  }, 

  getOneFromfrequentlyUsedFoodListList : function(id){
    for (var x in this.data.frequentlyUsedFoodList_list) {
      var temp = this.data.frequentlyUsedFoodList_list[x];

      if (temp.id == id){
        return {
          "index" : x,
          "data" : temp
        };
      }
    }
  },

  updatefrequentlyUsedFoodListData : function(id, index, data, type){//type: 0 : 修改，1 : 删除
    switch(type){
      case 0 : // 修改
        this.setData({
          ["frequentlyUsedFoodList_list[" + index + "]"]: data
        });

        break; 
      case 1 : // 删除
        this.data.frequentlyUsedFoodList.delete(id);

        this.getFrequentlyUsedFoodList();

        break;
    }
  },

  submitfrequentlyUsedFoodList : function(e){
    var foodIDs = new Array;
    var styleIds = new Array;
    var nums = new Array;

    if (this.data.isEmpty ){
      app.showToast("没有常用菜品");

      return;
    }

    for(var x of  this.data.frequentlyUsedFoodList){
      foodIDs.push(x[1].id);
      styleIds.push(x[1].styleID.id);
      nums.push(x[1].num);
    }

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/quickService/addFrequentlyUsedFoodList",

      header: {
        'content-type': 'application/json' // 默认值
      },

      data: {
        "openID": app.globalData.userInfo.openid,
        "foodIDs": foodIDs,
        "styleIDs" : styleIds,
        "nums" : nums
      },

      success(res) {
        app.internetResponseHandler(res, function(){
          app.showToast("保存成功");
        });
      },

      fail(res) {
        app.showToast("网络请求失败")
      }

    })
  },

  onLoad: function () {
    this.getFrequentlyUsedFoodListData();
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})