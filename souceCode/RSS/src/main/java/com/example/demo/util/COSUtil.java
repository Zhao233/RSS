package com.example.demo.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.util.Date;

public class COSUtil{
    private static String secretId = "AKIDUnrgGoBZy82g4DArVxFIAk8FBsOjonV1";
    private static String secretKey = "RhhTzFWaP0PbaN0Zy99tTpyrgIfmfIzS";

    static COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    static ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
    static COSClient cosClient = new COSClient(cred, clientConfig);
    static String bucketName = "rss-1252828635";

    public static String uploadFile(File file){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        //设置存储位置
        String key = "image/"+new Date().getTime()+"."+suffix;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,key,file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        String file_url = "https://rss-1252828635.cos.ap-beijing.myqcloud.com/"+key;

        return file_url;
    }


    public static void getFileUrl(){

    }


}
