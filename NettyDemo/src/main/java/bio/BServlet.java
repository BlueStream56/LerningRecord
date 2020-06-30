package bio;

import bio.BRequest;
import bio.BResponse;

public abstract class BServlet {

    public void service(BRequest request, BResponse response) throws Exception {
        //由service()方法决定是调用doGet()还是调用doPost()
        if ("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request, response);
        }else {
            doPost(request, response);
        }
    }

    public abstract void doGet(BRequest request, BResponse response) throws Exception;

    public abstract void doPost(BRequest request, BResponse response) throws Exception;

}
