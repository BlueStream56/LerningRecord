import java.nio.ByteBuffer;

public class BufferWrap {

    public static void main(String[] args) {
        //分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //创建指定大小的数组
        byte array[] = new byte[10];
        //将上面创建的数组包装为缓存区
        ByteBuffer byteBuffer1 = ByteBuffer.wrap(array);
        System.out.println(byteBuffer1.remaining());
        //测试修改数组时是否会影响缓冲区
        //直接操作新创建的数组，用于判断是否会影响用这个数组创建的缓冲区
        array[0] = 1;
        array[1] = 2;
        array[2] = 3;
        //重置byteBuffer1各项参数，由于当前下标为0，所以这个时候position=0，limit=0，capacity=10
        byteBuffer1.flip();
        //那么remaining=limit-position=0，这个时候是无法读取数据的
        //byteBuffer1.limit(3); 不过可以通过重新设置limit来读取
        System.out.println(byteBuffer1.remaining());
        while (byteBuffer1.hasRemaining()){
            System.out.print(byteBuffer1.get()+" ");
        }
        System.out.println(byteBuffer1.remaining());
        System.out.println("============================================");
        System.out.println(byteBuffer.position());
        //由于remaining=limit-position=0导致无法使用缓冲写入数据，这个时候可以重置为初始化状态
        byteBuffer1.clear();
        System.out.println(byteBuffer1.remaining());
        byteBuffer1.put(Byte.valueOf("4"));
        byteBuffer1.put(Byte.valueOf("5"));
        byteBuffer1.put(Byte.valueOf("6"));
        byteBuffer1.flip();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
        //得出结论，使用自定义创建数组的形式构建缓冲区，缓冲区和数组之间使用的同一个数组对象
        //当单方修改数组时并不会修改缓冲区其他参数，这样可能会导致很多不必要的单独设置
        //当修改缓冲区时会影响数组，未发现其他问题

        //总结：创建缓冲区时尽量使用对象创建（ByteBuffer.allocate(10)）不要使用单独创建数组的形式，
        // 除非特殊情况，这种时候一定要重新设置limit，用来保证能读取直接写入数组的数据
    }

}
