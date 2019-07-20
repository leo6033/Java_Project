/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OpenDirTest
 * Author:   ITryagain
 * Date:     2018/12/4 22:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ITryagain
 * @create 2018/12/4
 * @since 1.0.0
 */
import java.io.File;
import java.io.IOException;

public class OpenDirTest {
    public static void main(String[] args) {
        try {
            java.awt.Desktop.getDesktop().open(new File("C:\\Users\\ITryagain\\Desktop\\a"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
