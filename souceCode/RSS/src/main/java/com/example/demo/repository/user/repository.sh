#!/bin/bash

for loop in Admin Cooker Customer Waiter

do
	echo "package com.example.demo.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface $loop extends JpaRepository<$loop,Long> {
}
" >> $loop.java

done
