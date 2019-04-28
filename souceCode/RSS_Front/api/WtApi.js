import Promise from 'es6-promise.min'
// 定义需要store_id参数的url
const storeIdArray = ['store.storeInfo', 'store.queue', 'goods.find', 'store.reservation',
    'order.reserveOrder', 'store.set_queue', 'store.cancel_queue', 'cart.menu', 'cart.clean',
    'cart.update_dish_number_of_category', 'table.get_name', 'meal_time.list_all',
    'order.pre_confirm', 'order.order', 'order.confirm_pay', 'order.quick_pay_get_order_id', 'table.open'];

class WtApi {
    // 定义构造函数
    constructor() {
        this._initConfig()

    }

    /**
     * 初始化对象
     */
    _initConfig() {
        this.extConfig = wx.getExtConfigSync ? wx.getExtConfigSync() : {};
        this.headerConfig = {
            'Content-Type': 'application/html'
        }
        // //  测试用初始化
        // this.extConfig = {
        //   "uniacid": 12,	//公众号ID
        //   "ver": 2,	//接口版本
        //   "api_prefix": 'https://wxfx.lashou.com'	//接口前缀
        // }

        //  初始化店铺id和桌位id
        // wx.setStorageSync('store_id', 11)
        // this.extConfig.store_id = 11;
        // this.setSessionId().then()
    }

    /**
     * 发起wx.request请求
     * @param path             跟路径后的路由路径
     * @param method           请求的方式[get,post]
     * @param header           添加的请求头，对象格式{key:value}
     * @param action           action字段   String
     * @param data             request里面的数据   对象格式   如果是method为get则解析后拼接到url中，如果是method为post，则放到request数据包中
     * @param queryObject      额外需要拼接到url中的字段，   对象格式{key:value}
     * @param isShowLoading    是否显示loading蒙层，boolean类型  [true,false]
     * @returns {Promise}      返回promise对象
     * @private
     */
    _api(path, method, header, action, data, queryObject, isShowLoading) {
        let that = this;
        //  header配置
        let sessionId = wx.getStorageSync('session_id');
        if (sessionId) {
            this.headerConfig = Object.assign(this.headerConfig, { 'X-Wd-Session-Id': sessionId })
        }
        let headerObject = Object.assign({}, this.headerConfig, header);
        //  请求地址配置
        let urlString = this.extConfig.api_prefix + path;
        //  请求地址内容配置
        let params = Object.assign({
            c: 'entry',
            m: 'weisrc_dish',
            ver: this.extConfig.ver,
            i: this.extConfig.uniacid,
        });
        let obj = this._checkPath(action);
        urlString += (urlString.indexOf('?') < 0 ? '?' : '&') + this._param(Object.assign({}, params, obj, queryObject ? queryObject : {}));
        return new Promise(function (resolve, reject) {
            if (isShowLoading) {
                wx.showLoading({
                    title: '加载中',
                    mask: true
                })
            }
            console.log(`${method}->${action} == ${urlString}`);
            wx.request({
                url: urlString,
                data: data,
                method: method,
                header: headerObject,
                success: function (res) {
                    if (res.data.type && res.data.type == 'info' && res.data.data && res.data.data.code == '401') {
                        // 用户未登录
                        console.log('3.用户未登录');
                        that.setSessionId().then((session_id) => {
                            console.log('4.setsessionid 方法 then 接收');
                            headerObject = Object.assign({}, headerObject, { 'X-Wd-Session-Id': session_id });
                            wx.request({
                                url: urlString,
                                data: data,
                                method: method,
                                header: headerObject,
                                success: function (res) {
                                    resolve(res.data)
                                }
                            })
                        })
                    } else {
                        resolve(res.data)
                    }
                },
                fail: function (res) {
                    reject(res)
                },
                complete: function (res) {
                    let that = this;
                    console.log('res = ', res);
                    if (isShowLoading) {
                        wx.hideLoading()
                    }
                    if (res.data && res.data.type != 'success') {
                        if (res.data.data && res.data.data.code == '401') {
                        }
                        else if (res.data.data && res.data.data.code == '-41003') {
                            // 用户不存在
                            wx.getSetting({
                                success(res) {
                                    if (res.authSetting['scope.userInfo']) {
                                        wx.getUserInfo({
                                            success: (res) => {
                                                that.apiStore_userSubmit({
                                                    'userInfo': res.userInfo,
                                                    'rawData': res.rawData,
                                                    'signature': res.signature,
                                                    'encryptedData': res.encryptedData,
                                                    'iv': res.iv
                                                }).then(function (res) {
                                                    console.log('用户提交信息回文', res)
                                                })
                                            }
                                        })
                                    }
                                }
                            })
                        } else if (res.data.data && /库存不足啦/.test(res.data.message)) {
                        // 跳出不显示toast
                        }
                        else {
                            setTimeout(function () {
                                wx.showToast({
                                    title: res.data.message || '',
                                    image: '/images/icon_warning.png',
                                    mask: false,
                                    duration: 1500
                                })
                            }, 1200)
                        }
                    }
                }
            })
        })
    }

    /**
     * 检查路径是否需要增加storeid
     * @param action          action方法，string类型
     * @returns {{}}        返回为对象格式，用于拼接url字符串
     * @private
     */
    _checkPath(action) {
        let obj = {};
        // 判断是否有store_id
        if (!(storeIdArray.indexOf(action) === -1)) {
            obj.store_id = wx.getStorageSync('store_id')
        }
        // 判断a还是do
        if (action === 'session_id') {
            obj.a = action;
            obj.c = 'auth'
        } else {
            obj.do = action
        }
        return obj
    }

    /**
     * 将对象解析成url格式
     * @param data            需要解析url字符串数据     对象格式
     * @returns {string}      返回字符串类型  用于追加到url上
     * @private
     */
    _param(data) {
        let url = '';
        for (var k in data) {
            let value = data[k] !== undefined ? data[k] : '';
            url += '&' + k + '=' + encodeURIComponent(value)
        }
        return url ? url.substring(1) : ''
    }

    //  获取session_id 并将其放入请求头中
    setSessionId() {
        let that = this;
        return new Promise((resolve, reject) => {
            wx.login({
                success: (res) => {
                    console.log('1.进入success');
                    this.apiPost('session_id', { code: res.code }).then((data) => {
                        console.log('2.进入了then 方法获取sessionid');
                        // 添加到sessionStroge
                        if (data.message === '') {
                            try {
                                wx.setStorageSync('session_id', data.data.session_id)
                            } catch (e) {
                                console.error(e);
                                reject(e)
                            }
                            // 添加到header
                            this.headerConfig = Object.assign(this.headerConfig, { 'X-Wd-Session-Id': data.data.session_id });
                            // 提交用户信息到服务器

                            // that.getUserInfoSync().then(function (res) {
                            //   console.log('获取用户信息', res)
                            //   that.apiStore_userSubmit({
                            //     'userInfo': res.userInfo,
                            //     'rawData': res.rawData,
                            //     'signature': res.signature,
                            //     'encryptedData': res.encryptedData,
                            //     'iv': res.iv
                            //   }).then(function (res) {
                            //     console.log('用户提交信息回文', res)
                            //     // console.log('提交用户信息成功')
                            //     resolve(data.data.session_id)
                            //   })
                            // })

                            wx.getUserInfo({
                                success: (res) => {
                                    this.apiStore_userSubmit({
                                        'userInfo': res.userInfo,
                                        'rawData': res.rawData,
                                        'signature': res.signature,
                                        'encryptedData': res.encryptedData,
                                        'iv': res.iv
                                    }).then(function (res) {
                                        console.log('用户提交信息回文', res);
                                        // console.log('提交用户信息成功')
                                        resolve(data.data.session_id)
                                    })
                                },
                                fail: () => {
                                    wx.hideLoading()
                                     //wx.openSetting()
                                }
                            })
                        }
                    })
                }
            })
        })
    }

    //
    // getUserInfoSync() {
    //     let that = this
    //     return new Promise(function (resolve, reject) {
    //         wx.getUserInfo({
    //             success(res) {
    //                 resolve(res)
    //             },
    //             fail() {
    //                 that.openSetting(resolve)
    //             }
    //         })
    //     })
    // }
    //
    //
    // openSetting(resolve) {
    //     let that = this
    //     wx.getSetting({
    //         success(res) {
    //             if (!res.authSetting['scope.userInfo']) {
    //                 wx.openSetting({
    //                     // success(res) {
    //                     //   that.openSetting(resolve)
    //                     // },
    //                     // fail() {
    //                     //   that.openSetting(resolve)
    //                     // },
    //                     complete(res) {
    //                         if (!res.authSetting['scope.userInfo']) {
    //                             wx.showToast({
    //                                 title: '请允许使用用户信息',
    //                                 image: '/images/icon_warning.png',
    //                                 mask: false,
    //                                 duration: 1500
    //                             })
    //                         }
    //                         setTimeout(function () {
    //                             that.openSetting(resolve)
    //                         }, 1500)
    //                     }
    //                 })
    //             } else {
    //                 wx.getUserInfo({
    //                     success(res) {
    //                         resolve(res)
    //                     }
    //                 })
    //             }
    //         }
    //     })
    // }


    /**
     * 通用请求配置 GET && POST
     */
    //  通用请求 -> GET
    apiGet(action, data, queryObject, isShowLoading) {
        return this._api('/wxa/index.php', 'GET', {}, action, data, queryObject, isShowLoading)
    }

    //  通用请求 -> POST
    apiPost(action, data, queryObject, isShowLoading) {
        return this._api('/wxa/index.php', 'POST', { 'content-type': 'application/json' }, action, data, queryObject, isShowLoading)
    }

    //  --------------------------------------------------
    /**
     * 以下为实际调用的接口 && 方法名
     */
    apiStore_storeInfo(data) { return this.apiGet('store.storeInfo', data) }    // 获取店铺信息
    apiStore_storeList(data) { return this.apiGet('store.storeList', data) }    //  获取门店列表
    apiGetGoods(data, queryObject) { return this.apiPost('goods.find', data, queryObject, true) } // 获取商品列表和分类列表
    apiUpdateShopCart(data, queryObject, isShowLoading) { return this.apiPost('cart.update_dish_number_of_category', data, {}, isShowLoading) } // 修改购物车商品
    apigetShopCartList(data, queryObject) { return this.apiPost('cart.menu', data, queryObject) } //获取购物车列表
    apiRemoveAllGoods(data, queryObject) { return this.apiPost('cart.clean', data, queryObject) } // 清空购物车
    apiGetTableName(data, queryObject) { return this.apiGet('table.get_name', data, queryObject) } // 获取桌台信息
    apiTableOpen(data, queryObject) { return this.apiGet('table.open', data, queryObject) } // 开台
    apiStore_getDeliveryTimeList(data) { return this.apiGet('meal_time.list_all', data) } // 获取配送时间列表
    apiStore_getConfirmInfo(data, queryObject) { return this.apiGet('order.pre_confirm', data, queryObject, true) } // 确认订单信息
    apiStore_storeQueue(data) { return this.apiGet('store.queue', data) } // 排队
    apiStore_setQueue(data) { return this.apiPost('store.set_queue', data) } // 取号
    apiStore_cancelQueue(data) { return this.apiPost('store.cancel_queue', data) } // 取消排队
    apiStore_couponCouponList(data) { return this.apiGet('coupon.couponList', data) }  // 过去优惠券列表
    apiStore_storeReservation(data, queryObject) { return this.apiPost('store.reservation', data, queryObject) }    //  预定
    apiStore_orderReserveOrder(data) { return this.apiPost('order.reserveOrder', data) }    //  提交预定
    apiStore_orderList(data, queryObject) { return this.apiGet('order.order_list', data, queryObject) }    //  订单列表
    apiStore_orderDetail(data, queryObject) { return this.apiPost('order.detail', data, queryObject) }    //  订单详情
    apiStore_orderCancel(data, queryObject) { return this.apiPost('order.manual_cancel', data, queryObject) }    //  取消订单
    apiStore_userSubmit(data) { return this.apiPost('user.submit', data) }    //  提交用户信息
    apiStore_adressAdd(data) { return this.apiPost('address.add', data) }  // 添加收货地址
    apiStore_addressListAll(data) { return this.apiGet('address.list_all', data) } //  获取收货地址列表
    apiStore_orderOrder(data, queryObject) { return this.apiPost('order.order', data, queryObject) }    // 下单
    apiStore_getUserInfo(data) { return this.apiGet('user.center', data) }         // 获取用户信息
    apiStore_getUserRecord(data, queryObject) { return this.apiPost('user.trade_record', data, queryObject, true) }         // 获取积分、余额记录
    apiStore_getUserCouponsList(data, queryObject) { return this.apiPost('user.coupon_list', data, queryObject) }         // 已领取的优惠券
    apiStore_receiveCoupon(data, queryObject) { return this.apiGet('coupon.receive_coupon', data, queryObject) }         // 领取优惠券
    apiStore_receiveVipCard(data, queryObject) { return this.apiPost('user.require_member_card', data, queryObject) }    //领取会员卡
    apiStore_payType(data) { return this.apiGet('order.choose_pay_type', data) }       // 支付方式
    apiStore_cardPay(data) { return this.apiGet('card.pay', data) } //  获取会员卡支付页面信息接口
    apiStore_quickPayGetOrderId(data, queryObject) { return this.apiPost('order.quick_pay_get_order_id', data, queryObject) } //  快捷支付生成订单号
    apiStore_getPayInfo(data) { return this.apiGet('order.pay_status', data) } //  获取支付信息
    apiStore_cardDoPay(data) { return this.apiPost('card.do_pay', data) } //  会员卡支付
    apiStore_queryAddress(data) { return this.apiGet('address.get', data) } //  根据ID查询地址
    apiStore_removeAddress(data) { return this.apiPost('address.remove', data) } //  删除收货地址
    apiStore_changeAddress(data) { return this.apiPost('address.update', data) } // 修改收货地址
    apiStore_deleteOrder(data, queryObject) { return this.apiPost('order.order_delete', data, queryObject) } //  删除订单
    apiStore_orderAutoCancel(data, queryObject) { return this.apiPost('order.order_auto_cancel', data, queryObject) } //  超时自动取消订单
    apiStore_selectAddress(data) { return this.apiGet('address.select', data) } //  选择收货地址
    apiStore_confirmPay(data, queryObject) { return this.apiPost('order.confirm_pay', data, queryObject, true) }   // 确认支付
    apiStore_orderCancelPay(data, queryObject) { return this.apiPost('order.cancel', data, queryObject) } // 已支付用户取消订单
    apiStore_orderFailPay(data, queryObject) { return this.apiPost('order.cancel_pay', data, queryObject) } // 取消支付
}

export default WtApi
