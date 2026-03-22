package adapter_pattern;

public class StudentPointServiceAdapter implements StudentPointService{
   private LegacyPointSystem legacyPointSystem;

   public StudentPointServiceAdapter(LegacyPointSystem legacyPointSystem) {
      this.legacyPointSystem = legacyPointSystem;
   }

   public StudentPointServiceAdapter() {
   }

   @Override
   public String getPointById(String idJson) {
      // 1. Convert JSON to XML
      String studentId = idJson.replace("{\"id\": \"", "").replace("\"}", "");
      String xmlId = "<ID>" + studentId + "</ID>"; // Đã sửa thẻ đóng </ID>

      // 2. Gọi hệ thống cũ
      String xmlResponse = legacyPointSystem.getPoint(xmlId);
      // 3. Convert XML to JSON
      String id = extract(xmlResponse, "ID");
      String name = extract(xmlResponse, "Name");
      String score = extract(xmlResponse, "Score");

      return String.format("{\"id\": \"%s\", \"name\": \"%s\", \"score\": %s}", id, name, score);
   }

   // Hàm hỗ trợ bóc tách XML đơn giản
   private String extract(String xml, String tag) {
      return xml.split("<" + tag + ">")[1].split("</" + tag + ">")[0];
   }
}
