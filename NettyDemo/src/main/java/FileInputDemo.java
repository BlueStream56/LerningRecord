import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileInputDemo {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("D:\\WorkSpace\\LerningRecord\\NettyDemo\\src\\main\\java\\test.txt");

        FileChannel fileChannel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()){
            System.out.print(byteBuffer.get()+" ");
        }

        inputStream.close();
    }

}
