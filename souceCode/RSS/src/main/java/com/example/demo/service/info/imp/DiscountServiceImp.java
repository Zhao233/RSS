package com.example.demo.service.info.imp;

import com.example.demo.domain.foodInfo.DiscountRecord;
import com.example.demo.repository.info.DiscountRecordDao;
import com.example.demo.service.info.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("discountService")
public class DiscountServiceImp implements DiscountService {
    @Autowired
    private DiscountRecordDao discountRecordDao;

    /**
     * 获取所有折扣
     * 满减: 超过指定金额返回
     * 折扣：直接返回
     * */
    @Override
    public List<DiscountRecord> getDiscount(double account) {
        LinkedList<DiscountRecord> discountRecordLinkedList = new LinkedList<>();
        LinkedList<Integer> list_index = new LinkedList<>();

        discountRecordLinkedList = discountRecordDao.getAll();

        for(int i = 0; i < discountRecordLinkedList.size(); i++){
            DiscountRecord temp_discountRecord = discountRecordLinkedList.get(i);

            /**
             * 去除小于限定金额的满减折扣
             * */
            if(temp_discountRecord.getType() == DiscountRecord.DISCOUNT_ReachedLimitedMoney) {
                if( account < temp_discountRecord.getMoneyLimit() ){
                    list_index.add(i);
                }

            }

            /**
             * 去除小于限定金额的折扣
             * */

        }

        for(int i =0; i < list_index.size(); i++){
            int index = list_index.get(i);

            if( i != 0 ){//删除元素之后,下标也会产生变化，所以-1
                index--;
            }

            discountRecordLinkedList.remove(index);
        }

        return discountRecordLinkedList;
    }

    @Override
    public Double getDiscountNum(Long discountID) {
        return discountRecordDao.getOne(discountID).getDiscount();
    }
}
