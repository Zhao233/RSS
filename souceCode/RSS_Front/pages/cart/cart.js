//index.js
//获取应用实例
const app = getApp()

var url_pic = 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2903730956,4222895621&fm=27&gp=0.jpg';

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),

    result: [],

    food_cart : [
    ],
    food_accounts:0
  },

  getCartList: function () {//获取购物车卡片列表
    var temp_food_cart = new Array;

    for (var x of app.globalData.cartListRecord) {
      var item = app.globalData.cartListRecord.get(x[0]);

      temp_food_cart.push(item);
    }

    this.setData({
      food_cart: temp_food_cart
    });
  },

  getAccounts : function(){
    var account = 0;

    for (var x of app.globalData.cartListRecord) {
      var temp_item = x[1];

      account+=(temp_item.price*temp_item.num);
    }

    account*=100;

    this.setData({
      food_accounts : account
    })
  }, 

  updateCartData : function(id, index, data, type){//type: 0 : 修改，1 : 删除
    switch(type){
      case 0 : // 修改
        app.globalData.cartListRecord.set(id, data);//将全局的数据也一并更新

        this.setData({
          ["food_cart[" + index + "]"]: data
        });

        break; 
      case 1 : // 删除
        app.globalData.cartListRecord.delete(id);//将全局的数据也一并更新

        this.getCartList();

        break;
    }

    
  },

  getOneFromFoodCartList(id){
    for (var x in this.data.food_cart) {
      var temp = this.data.food_cart[x];

      if (temp.id == id){
        return {
          "index" : x,
          "data" : temp
        };
      }
    }
  },

  //将购物车的商品加一
  addOne : function(e) {
    var id = e.currentTarget.dataset.foodid;
    var temp = this.getOneFromFoodCartList(id);

    var index = temp.index;
    var item_cart = temp.data;

    item_cart.num++;

    this.updateCartData(id, index, item_cart,0);
    this.getAccounts();
  }, 

  //将购物车的商品减一
  removeOne : function(e) {
    var id = e.currentTarget.dataset.foodid;
    var temp = this.getOneFromFoodCartList(id);

    var index = temp.index;
    var item_cart = temp.data;

    if(item_cart.num == 1){//将减少至0，则清除这条记录
      this.updateCartData(id, index, item_cart, 1);
    } else {
      item_cart.num--;

      this.updateCartData(id, index, item_cart, 0);
    }

    
    this.getAccounts();
  }, 

  addToQuickOrder : function(e){
    var foodID = e.target.dataset.foodid;
    var openid = app.globalData.userInfo.openid;

    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/quickService/addFoodToFrequentlyUsedFoodList",

      header: {
        'content-type': 'application/json' // 默认值
      },
      
      data: {
        openID: openid,
        foodID : foodID,
        styleID : 1
      },

      success(res) {
        app.internetResponseHandler(res, function(){
          app.showToast("添加成功");
        });
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })
  },

  //提交订单
  onSubmit : function (e) {
      if(app.globalData.cartListRecord.size == 0){
        app.showToast("购物车中无商品")

        return ;
      }

      var true_account = this.data.food_accounts / 100;

    var data = "account=" + true_account + "&foodList=" + this.data.food_cart;

    wx.navigateTo({
      url: 'confirmOrder/confirmOrder?' + data
    })
  },

  onLoad: function () {
    
  },

  onShow : function () {
    this.getCartList();
    this.getAccounts();
  },

  onChange(event) {
    console.log(event.detail);

    this.setData({
      result: event.detail
    });
  }
})