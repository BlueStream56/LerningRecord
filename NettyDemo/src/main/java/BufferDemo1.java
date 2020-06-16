import java.nio.ByteBuffer;

public class BufferDemo1 {

    public static void main(String[] args) {
        /**
         * 缓冲区分片
         */
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //向改该缓冲区中添加数据
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        //创建子缓冲区
        byteBuffer.position(3);
        byteBuffer.limit(7);
        ByteBuffer slice = byteBuffer.slice();

        //修改子缓冲区中的数据
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }

        //需要重置缓冲区的limit和position，否则只能读取子缓冲区前的数据
        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        while (byteBuffer.hasRemaining()){
            System.out.print(byteBuffer.get()+" ");
        }
        //总结，子缓冲实际上就是重新设置开始下标和以缓冲区做limit重新设置，
        //例如byteBuffer.position(3);byteBuffer.limit(7);就是将缓冲区中下标为3开始，一直到7之前的为子缓冲区
        //slice = byteBuffer.slice();通过slice方法直接获取该子缓冲区的操作而不需要直接操作缓冲区
        //不过读取缓冲区时需要手动设置limit和position读取全部数据
    }

}
