#!/bin/bash

for loop in CookerDeliveryRecord DiscountRecord frequentlyUsedMenu NonPaymentRecord OrderRecord PreferenceRecord WaiterDeliveryRecord

do
	echo "package com.example.demo.repository.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface $loop extends JpaRepository<$loop,Long> {
}
" >> $loop.java

done
