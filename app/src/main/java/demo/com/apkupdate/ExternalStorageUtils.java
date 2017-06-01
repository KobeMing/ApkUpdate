package demo.com.apkupdate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.os.StatFs;

public class ExternalStorageUtils {

    /**
     * 1.检查外部存储是否挂载
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        // 1.获取状态
        String state = Environment.getExternalStorageState();
        // 2.判断是否是挂载的值
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardMounted()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * @param dirName
     * @param fileName
     * @param data
     * @return
     */
    public static boolean writeToCustomDir(String dirName, String fileName,
                                           byte[] data) {
        // mnt/sdcard/memeda/xx.jpg
        // 1.判断是否sd卡挂载
        if (isSDCardMounted()) {
            // 2.创建自定义目录
            File rootFile = Environment.getExternalStorageDirectory();// mnt/sdcard
            File parentFile = new File(rootFile, dirName);// mnt/sdcard/memeda
            if (!parentFile.exists()) {
                parentFile.mkdirs();// 如果自定义目录不存在，就创建文件夹
            }
            File file = new File(parentFile, fileName);// mnt/sdcard/memeda/xx.jpg
            // 3.存储数据
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 将data中的数据存储到sd卡的根目录下
     *
     * @param fileName
     * @param data
     * @return
     */
    public static boolean writeDataToRoot(String fileName, byte[] data) {
        // 1.判断是否挂载sd卡
        if (isSDCardMounted()) {
            // 2.获取路径
            File parentFile = Environment.getExternalStorageDirectory();
            File file = new File(parentFile, fileName);
            // 3.写
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    // 3.取

    /**
     * 从sd卡的根部目录获取文件
     *
     * @param fileName
     * @return
     */
    public static byte[] readDataFromRoot(String fileName) {
        if (isSDCardMounted()) {
            File parentFile = Environment.getExternalStorageDirectory();
            File file = new File(parentFile, fileName);
            FileInputStream fis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                fis = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                return baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
