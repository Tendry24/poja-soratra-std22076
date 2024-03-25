package hei.school.soratra.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import hei.school.soratra.PojaGenerated;

@PojaGenerated
public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}
