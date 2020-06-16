import java.nio.ByteBuffer;

public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //向缓冲区添加数据
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        //创建只读花缓冲区
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        //改变原缓冲区数据
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byte b = byteBuffer.get(i);
            b *= 10;
            byteBuffer.put(i, b);
        }

        //重置只读缓冲区的limit和position
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(byteBuffer.capacity());

        //读取只读缓冲区数据，由于原缓冲区数据发生变化只读缓冲区也会发生对应的变化
        while (readOnlyBuffer.hasRemaining()){
            System.out.print(readOnlyBuffer.get()+" ");
        }

        //当修改只读缓冲区中的数据时会报错java.nio.ReadOnlyBufferException
//        readOnlyBuffer.put(0,(byte)100);
    }

}
