package com.example.hoang.hoabanfood1.Connect;

/**
 * Created by hoang on 9/30/2017.
 */
public class Server {
    public static String localhost = "hoaban.net";
//    public static String localhost = "192.168.0.106";
//    public static String localhost = "192.168.1.24";
    public static String duongdanloaisp ="http://"+localhost+"/server/getloaisp.php";
    public static String duongdanspmoi ="http://"+localhost+"/server/getspmoi.php";
    public static String duongdanspgiare ="http://"+localhost+"/server/getspgiare.php";
    public static String duongdanspcaocap ="http://"+localhost+"/server/getspcaocap.php";
    public static String duongdandssp ="http://"+localhost+"/server/getdssanpham.php?page=";
    public static String duongdanlogin ="http://"+localhost+"/server/login.php";
    public static String duongdanregister ="http://"+localhost+"/server/register.php";
    public static String duongdanusername="http://"+localhost+"/server/getusername.php";
    public static String duongdandonhang="http://"+localhost+"/server/donhang.php";
    public static String duongdanchitietdonhang="http://"+localhost+"/server/chitietdonhang.php";
    public static String duongdandsdonhang="http://"+localhost+"/server/getdsdonhang.php";
    public static String duongdangetchitietdonhang="http://"+localhost+"/server/getchitietdonhang.php";
    public static String duongdanhuydonhang="http://"+localhost+"/server/huydonhang.php";
    public static String duongdansendcoment="http://"+localhost+"/server/sendcomment.php";
    public static String duongdangetcoment="http://"+localhost+"/server/getcomment.php?page=";
}