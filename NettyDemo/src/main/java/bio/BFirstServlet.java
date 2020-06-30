package bio;

public class BFirstServlet extends BServlet {
    @Override
    public void doGet(BRequest request, BResponse response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(BRequest request, BResponse response) throws Exception {
        response.write("This is First Servlet");
    }
}
