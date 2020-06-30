package netty;

public abstract class NServlet {

    public void service(NRequest request, NResponse response) throws Exception {
        //由service()方法决定是调用doGet()还是调用doPost()
        if ("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request, response);
        }else {
            doPost(request, response);
        }
    }

    public abstract void doGet(NRequest request, NResponse response) throws Exception;

    public abstract void doPost(NRequest request, NResponse response) throws Exception;

}
