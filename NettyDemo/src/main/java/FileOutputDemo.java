import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileOutputDemo {

    static private final byte messsage[] = {83, 111, 109, 101, 32, 98, 121, 116, 101, 115, 46};

    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\WorkSpace\\LerningRecord\\NettyDemo\\src\\main\\java\\test.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        for (int i = 0; i < messsage.length; i++) {
            byteBuffer.put(messsage[i]);
        }

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }

}
