package com.huangwei.core.util.encode;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by HuangWei on 2018/4/4.
 */

public class MD5Util {

    private static final String HEX_NUMBERS_STR="0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;

    //  https://www.cnblogs.com/tingbogiu/p/5916355.html
    //  https://blog.csdn.net/wildpal/article/details/53396030

    /**
     * 将16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMBERS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMBERS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法
     * @param password
     * @param passwordInDb
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String password, String passwordInDb)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(passwordInDb);
        //声明盐变量
        byte[] salt = new byte[SALT_LENGTH];
        //将盐从数据库中保存的口令字节数组中提取出来
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
        //生成输入口令的消息摘要
        byte[] digest = md.digest();
        //声明一个保存数据库中口令消息摘要的变量
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        //取得数据库中口令的消息摘要
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        if (Arrays.equals(digest, digestInDb)) {
            //口令正确返回口令匹配消息
            return true;
        } else {
            //口令不正确返回口令不匹配消息
            return false;
        }
    }

    /**
     * 获得加密后的16进制形式口令
     * @param string
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getEncryptedMD5String(String string)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //声明加密后的口令数组变量
        byte[] pwd = null;
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量   12
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);

        //声明消息摘要对象
        MessageDigest md = null;
        //创建消息摘要
        md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(string.getBytes("UTF-8"));
        //获得消息摘要的字节数组
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];
        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        for(int i=0;i<pwd.length;i++){
            System.out.print(pwd[i]);
        }
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }

    /**
     * 获得未加密的16进制形式口令
     * @param string
     * @return 未加盐的加密口令
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getMD5String(String string)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(string.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }
    private static String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }
}

