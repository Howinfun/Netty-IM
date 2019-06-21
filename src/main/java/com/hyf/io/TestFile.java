package com.hyf.io;

import java.io.File;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/18
 */
public class TestFile {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\87623\\Desktop\\1.txt");
        /*boolean success = file.renameTo(new File("D:\\1.txt"));
        System.out.println("移动文件"+(success == true ? "成功":"失败"));*/

        file.delete();

        boolean exists = file.exists();
        System.out.println("文件"+(exists == true ? "存在":"不存在"));
    }
}
