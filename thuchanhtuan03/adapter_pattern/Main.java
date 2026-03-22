package adapter_pattern;

public class Main {
   public static void main(String[] args) {
      StudentPointService service = new StudentPointServiceAdapter(new LegacyPointSystem());
      String request = "{\"id\": \"SV01\"}";
      String response = service.getPointById(request);
      System.out.println(response);
   }
}
