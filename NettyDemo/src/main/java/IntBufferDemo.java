import java.nio.IntBuffer;

public class IntBufferDemo {

    public static void main(String[] args) {
        //本质上这个还是数组，而IntBuffer.allocate可以设定数组的长度
        IntBuffer intBuffer = IntBuffer.allocate(8);

        int num = 0;
        //循环IntBuffer，分别进行赋值
        for (int i = 0; i < intBuffer.capacity(); i++) {
            num = 2 * (i + 1);
            //将数据添加到当前下标位置，并将当前位置往下递增
            intBuffer.put(num);
        }
        //将当前指定的位置重置起始位置0
        intBuffer.flip();
        //获取当前位置和限制位置之间是否有值，然而实际本质是判断当前位置是否超过最大长度，position < limit
        while (intBuffer.hasRemaining()){
            //获取当前位置的值，然后向下一位递增
            num = intBuffer.get();
            System.out.print(num + " ");
        }
    }

}
