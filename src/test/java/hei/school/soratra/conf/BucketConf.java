package hei.school.soratra.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import hei.school.soratra.PojaGenerated;

@PojaGenerated
public class BucketConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.s3.bucket", () -> "dummy-bucket");
  }
}
