package com.example.news.Util;


import org.springframework.util.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author Peter on 2017-05-07.
 */
public class PropertyUtil {


    public static Properties loadPropertiesByFileName(String fileName) {
        Properties prop = new Properties();
        InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);

        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }

    /**
     * 是否是线上环境
     */
    public static boolean isProduction() {
        Properties properties = loadPropertiesByFileName("application.properties");
        return properties.get("spring.profiles.active").toString().equals("production");

    }

    public static boolean isDev() {
        return !isProduction();
    }

    public static String get(String propertyName) {

        String config = "production";
        if (isDevByIp()){
            config = "dev";
        }
        String fileName = "application-" + config + ".properties";
        return loadPropertiesByFileName(fileName).get(propertyName).toString();
    }

    private static boolean isDevByIp(){
        InetAddress address;//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
        String hostAddress;//192.168.0.121
        String config;
        try {
            address = InetAddress.getLocalHost();
            hostAddress = address.getHostAddress();
            if (hostAddress.startsWith("192.168.") || hostAddress.equals("127.0.0.1") || hostAddress.startsWith("169"))
                return true;
            else
                return false;

        } catch (UnknownHostException e) {
            Properties properties = loadPropertiesByFileName("application.properties");
            config = properties.getProperty("spring.profiles.active");
            if (config.equals("dev"))
                return true;
            else
                return false;
        }

    }

    /**
     * 用于本地调试的时候，生成本地配置文件
     */

    private static String path = "";
    //文件路径+名称
    private static String filenameTemp;

    /**
     * 创建文件
     * @return  是否创建成功，成功则返回true
     */
    public static boolean createFile(String subProjectName){

        if (!isDevByIp()){
            System.out.println("正式环境，无需创建本地测试配置文件");
            return false;
        }

        Boolean bool = false;

        Properties pro = new Properties();
        InputStream in = null;
        try {
            in = Object.class.getResourceAsStream("/ProjectLocation.properties");
            pro.load(in);
            path = pro.getProperty("self.dev.localProjectLocation");
            if (StringUtils.isEmpty(path)){
                System.out.println("读取本地测试项目路径配置文件失败！！！");
                return false;

            }else{
                path = path+subProjectName+"/target/classes/";
//                if (subProjectName.contains("service")){
//                    path = path+subProjectName+"/target/classes/";
//                }else{
//                    path = path+subProjectName+"/target/"+subProjectName+"/WEB-INF/classes/";
//
//                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        filenameTemp = path+"application.properties";//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        String filecontent = "spring.profiles.active=dev";
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                System.out.println("成功创建本地测试配置文件： "+filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);
            }else{
                delFile();
                file.createNewFile();
                bool = true;
                System.out.println("成功创建本地测试配置文件: "+filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 删除文件
     * @return
     */
    public static boolean delFile(){
        Boolean bool = false;
        filenameTemp = path+"application.properties";
        File file  = new File(filenameTemp);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bool;
    }

}