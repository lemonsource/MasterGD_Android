

package com.wisedu.xuechengapp.demo.entity;


import java.io.Serializable;

public class Member implements Serializable {
    //证书名称
    public final String name;
    //证书创建时间
    public final String createDate;
    /*************************************************************************/
    //证书内容

    //学生姓名
    public String studentName;
    //学号
    public String studentId;
    //专业
    public String major;
    //毕业时间
    public  String graduateDate;
    //学科类别
    public  String discipline;
    //学位类别
    public  String degreeLevel;
    //颁发机构
    public  String issuer;
    //颁发时间
    public  String issuingDate;

    //证书下载状态位   0表示未下载, 1 表示已下载
    public int downloadStatus;



    public Member(String name, String createDate) {
        this.name = name;
        this.createDate = createDate;
    }

    public Member(String name, String createDate, String studentName, String studentId, String major, String graduateDate, String discipline, String degreeLevel, String issuer, String issuingDate) {
        this.name = name;
        this.studentId=studentId;
        this.createDate = createDate;
        this.studentName = studentName;
        this.major = major;
        this.graduateDate = graduateDate;
        this.discipline = discipline;
        this.degreeLevel = degreeLevel;
        this.issuer = issuer;
        this.issuingDate = issuingDate;
        this.downloadStatus = 0;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
