package cn.pmsc.business;

public class PrintStatusCode {
	public static enum StatusCode {
        /*通过括号赋值,而且必须有带参构造器和一属性跟方法，否则编译出错
         * 赋值必须是都赋值或都不赋值，不能一部分赋值一部分不赋值
         * 如果不赋值则不能写构造器，赋值编译也出错*/
        normal(0), nonewdata(1), error(2), partial_normal(3);
        
        private final int value;
        public int getValue() {
            return value;
        }
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        StatusCode(int value) {
            this.value = value;
        }
    }
	
	private static void print(StatusCode name) {
		System.out.println("<running_status>" + name.getValue() + "</running_status>");
	}
	
	public static void print_normal() {
		print(StatusCode.normal);
	}
	
	public static void print_nonewdata() {
		print(StatusCode.nonewdata);
	}
	
	public static void print_error() {
		print(StatusCode.error);
	}
	
	public static void print_partial_normal() {
		print(StatusCode.partial_normal);
	}
}
