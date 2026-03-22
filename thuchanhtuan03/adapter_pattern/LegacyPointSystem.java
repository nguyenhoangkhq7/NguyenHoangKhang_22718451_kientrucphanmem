package adapter_pattern;

import java.util.Random;

public class LegacyPointSystem {
   private final String[] names = {"Nguyen Van An", "Tran Thi Binh", "Le Van Cuong", "Pham Minh Duc", "Hoang Lan Anh"};
   private final Random random = new Random();
   public String getPoint(String idXml) {
      String randomName = names[random.nextInt(names.length)];
      double randomScore = 5 + (5 * random.nextDouble());
      String formattedScore = String.format("%.1f", randomScore);
      return "<Data>" + idXml + "<Name>" + randomName + "</Name><Score>" + formattedScore + "</Score></Data>";
   }
}
