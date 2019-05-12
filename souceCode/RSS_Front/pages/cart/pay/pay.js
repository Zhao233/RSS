let Pormise = require('../../api/es6-promise.min.js')
// pages/pay/pay.js
let app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderId: '',                //测试用数据
    orderSn: '',                //测试用数据
    info: {},          // 店铺信息
    selectIndex: 0,  // 支付方式
    thisTime: '',   // 现在时间
    firstNum: 1,    // 倒计时展示数据
    secondNum: 5,
    thirdNum: 0,
    fourthNum: 0,
    isNew: false,    // 是否为新订单
    paying: false,   // 是否在支付
    isDisabled: false,   // btn是否可点
    isFromreserve: '',  // 是否为预定订单
    timerId: ''  // 倒计时句柄
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    // 150689", order_sn: "081000001506899922"
    this.setData({
      orderId: options.order_id,
      orderSn: options.order_sn,
      isNew: options.isNew,
      isFromreserve: options.flag           // reserve
    })
    if (options.time) {
      // console.log(options.time)
      this._countDown(options.time)
    } else {
      this._countDown(new Date().getTime())
    }
    app.globalData.wtApi.apiStore_payType({order_id: this.data.orderId}).then((data) => {
      this.setData({
        info: data.data
      })
    })
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

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    clearInterval(this.data.timerId)
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearInterval(this.data.timerId)
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  orderAutoCancel: function () {
    let that = this
    let params = {
      'order_id': this.data.orderId
    }
    let queryObject = {
      'order_id': this.data.orderId
    }
    app.globalData.wtApi.apiStore_orderAutoCancel(params, queryObject).then((response) => {
      wx.showModal({
        title: '提示',
        content: '订单已超时，请重新下单',
        showCancel: false,
        success: function (res) {
          if (res.confirm) {
            // wx.switchTab({
            //     url: '/pages/order/index'
            // })
            let storeId = wx.getStorageSync('store_id')
            wx.redirectTo({
              url: `/pages/order/orderDetail/orderDetail?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
            })
          }
        }
      })
    })
  },
  /**
   * 倒计时
   */
  _countDown: function (payTime) {
    payTime = parseInt(payTime)
    let newTime = new Date().getTime()
    let timer = 15 * 1000 * 60
    let resultTime = timer - (newTime - payTime)
    let timerHour
    let timerMinutes
    let timerSeconds
    let minutes
    let seconds
    if ((newTime - payTime > timer && this.data.orderDetail.pay_type == 0) && (this.data.orderDetail.status != -1 && this.data.orderDetail.status != -2)) {
      // wx.showToast({
      //     title: '因15分钟内未支付, 您的订单已经自动取消'
      // })
      this.orderAutoCancel()
    } else {
      this.data.timerId = setInterval(() => {
        if (resultTime < 0) {
          this.setData({
            timerFlag: false
          })
          clearInterval(this.data.timeId)
          wx.reLaunch({
            url: '/pages/index/index'
          })
        } else {
          resultTime = resultTime - 1000
          timerHour = resultTime % (24 * 3600 * 1000)
          timerMinutes = timerHour % (3600 * 1000)
          minutes = Math.floor(timerMinutes / (60 * 1000))
          minutes >= 10 ? minutes = minutes : minutes = '0' + minutes
          timerSeconds = timerMinutes % ((60 * 1000))
          seconds = Math.round(timerSeconds / 1000)
          seconds >= 10 ? seconds = seconds : seconds = '0' + seconds
          this.setData({
            firstNum: minutes >= 10 ? 1 : 0,
            secondNum: minutes >= 10 ? minutes % 10 : minutes.toString().substr(1, 2),
            thirdNum: seconds >= 10 ? Math.floor(seconds / 10) : 0,
            fourthNum: seconds >= 10 ? seconds % 10 : seconds.toString().substr(1, 2),
            timerFlag: true
          })
        }
      }, 1000)
    }
  },
  // 选择支付方式
  choosePayType: function (e) {
    let idx = e.currentTarget.dataset.index
    if (idx == 1) {
      if (parseFloat(this.data.info.params.totalprice) > parseFloat(this.data.info.credit)) {
        this.data.isDisabled = true
      }
    } else {
      this.data.isDisabled = false
    }
    this.setData({
      selectIndex: idx,
      isDisabled: this.data.isDisabled
    })
  },
  // 支付行为
  payAction: function () {
    if (app.globalData.isClickBtn) {
      return
    }
    app.globalData.isClickBtn = true
    setTimeout(function () {
      app.globalData.isClickBtn = false
    }, 1000)
    wx.showLoading({
      title: '加载中',
      mask: true
    })
    if (this.data.paying) {
      return
    }
    this.data.paying = true
    setTimeout(() => {
      this.data.paying = false
    }, 1000)
    let that = this
    let params = {
      order_id: this.data.orderId,
      type: (this.data.selectIndex == 0) ? 'wechat' : 'credit',
      totalprice: this.data.info.params.totalprice,
      source: 'order'
    }
    app.globalData.wtApi.apiStore_confirmPay(params, {}).then((data) => {
      console.log(data)
      if (data.type == 'success') {
        // 判断是什么支付方式
        if (this.data.selectIndex == 0) {
          // 微信支付
          wx.requestPayment({
            'timeStamp': data.data.timeStamp + '',
            'nonceStr': data.data.nonceStr,
            'package': data.data.package,
            'signType': data.data.signType,
            'paySign': data.data.paySign,
            'success': function (res) {
              if (res.errMsg == 'requestPayment:ok') {
                // 微信支付成功
                wx.showToast({
                  title: '支付成功',
                  mask: true, duration: 1500
                  // image: '/images/icon_warning.png'
                })
                let storeId = wx.getStorageSync('store_id')
                app.globalData.wtApi.apiStore_getPayInfo({order_id: params.order_id, storeid: storeId})
                setTimeout(function () {
                  if (that.data.isFromreserve == 'reserve') {
                    wx.redirectTo({
                      url: `/pages/order/orderAppointment/orderAppointment?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
                    })
                  } else if (that.data.isNew) {
                    wx.redirectTo({
                      url: `/pages/order/orderDetail/orderDetail?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
                    })
                    // wx.navigateBack({
                    //     delta: 6
                    // })
                  } else {
                    wx.navigateBack()
                  }
                }, 1000)
              }
            },
            'fail': function (res) {
              app.globalData.wtApi.apiStore_orderFailPay({order_id: params.order_id}, {}).then()
              wx.showToast({
                title: '支付失败',
                mask: true,
                image: '/images/icon_warning.png', duration: 1500
              })
              setTimeout(function () {
                // wx.switchTab({
                //     url: '/pages/order/index'
                // })
                let storeId = wx.getStorageSync('store_id')
                if (that.data.isFromreserve == 'reserve') {
                  wx.redirectTo({
                    url: `/pages/order/orderAppointment/orderAppointment?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
                  })
                } else if (that.data.isNew) {
                  wx.redirectTo({
                    url: `/pages/order/orderDetail/orderDetail?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
                  })
                  // wx.navigateBack({
                  //     delta: 6
                  // })
                } else {
                  wx.navigateBack()
                }
              }, 1000)
            }
          })
        } else {
          // 余额支付
          wx.showToast({
            title: '支付成功',
            mask: true, duration: 1500
          })
          setTimeout(function () {
            // wx.switchTab({
            //     url: '/pages/order/index'
            // })
            let storeId = wx.getStorageSync('store_id')
            if (that.data.isFromreserve == 'reserve') {
              wx.redirectTo({
                url: `/pages/order/orderAppointment/orderAppointment?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
              })
            } else if (that.data.isNew) {
              wx.redirectTo({
                url: `/pages/order/orderDetail/orderDetail?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
              })
              // wx.navigateBack({
              //     delta: 6
              // })
            } else {
              wx.navigateBack()
            }
          }, 1000)
        }
      } else {
        // confirmPay接口回文不正确
        if (data.data && data.data.code == '-5001') {
          wx.showToast({
            title: data.message,
            image: '/images/icon_warning.png',
            mask: true, duration: 1500
          })
          setTimeout(function () {
            let storeId = wx.getStorageSync('store_id')
            if (that.data.isNew) {
              wx.redirectTo({
                url: `/pages/order/orderDetail/orderDetail?orderId=${that.data.orderId}&storeId=${storeId}&orderSn=${that.data.orderSn}`
              })
            } else {
              wx.navigateBack()
            }
          }, 1000)
        }
      }
      wx.hideLoading()
    })
  }
})