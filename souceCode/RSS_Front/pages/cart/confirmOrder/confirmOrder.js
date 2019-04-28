let Pormise = require('../../../api/es6-promise.min.js')
// pages/order/confirm-order/confirm-order.js
let app = getApp()
Page({

    /**
     * 页面的初始数据
     */
  data: {
      screenHeight:0,
      screenWidth:0,

      disocunt:[],

      choosedDiscount : undefined,//类型: Object 选中的discount

      //向用户展示的打折信息
      isChoosed : false,
      chosedDiscountType : '',
      choosedDiscountTitle:'',

      account : 0,//原价
      account_final : 0,//结算价格
      account_discount : 0,//已优惠的价格

      show_chooseDiscount : false
  },
    
    //关闭弹出层
  onClose_popup() {
    this.setData({ show_chooseDiscount: false });
  },

  toChooseDiscount : function(e){
    this.setData({
      show_chooseDiscount : true
    })
  },

    //获取优惠卷列表
  getDiscount : function(account, that){
    wx.request({
      url: "http://" + app.info.hostname + ":" + app.info.port + "/customer/cart/getDiscountList",

      header: {
          'content-type': 'application/json' // 默认值
      },
      data: {
          "account": account
      },

      success(res) {

        app.internetResponseHandler(res);

        var data_discount = res.data.discountList;

        that.setData({
          discount: data_discount
        })
      },
      fail(res) {
        app.showToast("网络请求失败")
      }
    })
  },

    //选择折扣
  chooseDiscount : function(e) {
    var index = e.target.dataset.num;

    var discount = this.data.discount[index];

    var account_final = this.data.account * discount.discount;
    var account_discount = this.data.account - account_final; 

    account_final = account_final.toFixed(2);
    account_discount = account_discount.toFixed(2);

    this.setData({
      choosedDiscount: discount,

      account_final: account_final,
      account_discount : account_discount,

      isChoosed : true,
    })

    this.onClose_popup();
  },

  chooseNoDiscount : function (e){
    var account = this.data.account;

    this.setData({
      account_final: account,
      account_discount: 0,

      discountID : undefined,
      discount_num : 1,

      choosedDiscount : undefined,

      isChoosed : false
    })

    this.onClose_popup();
  },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (e) {
      var account = e.account;

      this.setData({
        account: account,
        account_final: account,
        account_discount: 0
      })

      this.getDiscount(account,this);

      48*6

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        var that = this;

        wx.getSystemInfo({
            success: function (res) {
              that.setData({
                screenHeight : res.windowHeight-110,
                screenWidth :  res.windowWidth-110
              })
            }
        });
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {
        console.log('yincang')
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {
        
    }
})