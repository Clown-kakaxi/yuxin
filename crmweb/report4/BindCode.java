import com.runqian.report4.model.engine.ExtNormalCell;
import java.io.File;
import java.io.PrintStream;

public class BindCode {
	public static void main(String[] args) {
		int code = ExtNormalCell.getBindCode();
		if (File.separator.equals("\\")) {
			if (code < 0)
				System.out.println("不能取到电脑特征码，可能系统出错，请看日志，或者没有权限，请用root登录再取！");
			else {
				System.out.println("你的电脑的特征码是：" + code);
			}
		} else if (code < 0) {
			System.out.println("\n Cannot get the computer code, maybe has no right, please login using root. \n");
		} else {
			System.out.println("\n Your computer code is " + code + " \n");
		}
	}
}