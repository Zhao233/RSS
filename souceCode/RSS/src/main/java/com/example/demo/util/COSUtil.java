package com.example.demo.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.net.URL;
import java.util.Date;

public class COSUtil {
    private static String secretId = "AKIDSobpvj32RwkY4WHwzrNsCL4beYu9EA1e";
    private static String secretKey = "20otnWAbBh5IKB1h96vb9B6uZEakuxIX";


    // 1 初始化用户身份信息（secretId, secretKey）。
    static COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    // clientConfig中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    static ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));

    // 3 生成 cos 客户端
    static COSClient cosClient = new COSClient(cred, clientConfig);

    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    static String bucketName = "rss-cos-1-1252828635";


    public static String uploadFile(File file){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        //设置存储位置
        String key = "image/"+new Date().getTime()+suffix;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,key,file);

        Date expiration = new Date(new Date().getTime() + 5 * 60 * 10000);

        URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);

        String file_url = "https://rss-cos-1-1252828635.cos.ap-beijing.myqcloud.com"+"/image/"+putObjectRequest.getFile().getPath();

        return file_url;
    }


    public static void getFileUrl(){

    }


}
