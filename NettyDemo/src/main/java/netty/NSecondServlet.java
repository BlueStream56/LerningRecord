package netty;

import bio.BRequest;
import bio.BResponse;
import bio.BServlet;

public class NSecondServlet extends NServlet {
    @Override
    public void doGet(NRequest request, NResponse response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(NRequest request, NResponse response) throws Exception {
        response.write("This is Second NServlet");
    }
}
