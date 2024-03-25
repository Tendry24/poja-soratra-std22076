package hei.school.soratra.endpoint.rest.controller.health;

import hei.school.soratra.file.BucketComponent;
import hei.school.soratra.file.BucketComponent;
import hei.school.soratra.file.FileHash;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/soratra")
public class SoratraController {

    @Autowired
    private BucketComponent bucketComponent;

    @PutMapping("/{id}")
    public ResponseEntity<Void> uploadPoeticPhrase(@PathVariable String id, @RequestBody String phrase) {
        try {

            Path tempFile = Files.createTempFile("poetic_phrase", ".txt");
            Files.write(tempFile, phrase.getBytes());


            File file = tempFile.toFile();
            bucketComponent.upload(file, "poetic_phrases/" + id);

            Files.deleteIfExists(tempFile);


            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getPoeticPhrase(@PathVariable String id) {
        try {

            File originalFile = bucketComponent.download("poetic_phrases/" + id);


            String transformedContent = Files.lines(originalFile.toPath())
                    .collect(Collectors.joining("\n"))
                    .toUpperCase();


            Path tempTransformedFile = Files.createTempFile("poetic_phrase_transformed", ".txt");
            Files.write(tempTransformedFile, transformedContent.getBytes());


            File transformedFile = tempTransformedFile.toFile();
            bucketComponent.upload(transformedFile, "poetic_phrases_transformed/" + id);


            Files.deleteIfExists(tempTransformedFile);

            String originalUrl = bucketComponent.presign("poetic_phrases/" + id, Duration.ofHours(1)).toString();
            String transformedUrl = bucketComponent.presign("poetic_phrases_transformed/" + id, Duration.ofHours(1)).toString();


            return ResponseEntity.ok(new PoeticPhraseResponse(originalUrl, transformedUrl));
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Setter
    @Getter
    private static class PoeticPhraseResponse {
        private String original_url;
        private String transformed_url;

        public PoeticPhraseResponse(String originalUrl, String transformedUrl) {
            this.original_url = originalUrl;
            this.transformed_url = transformedUrl;
        }
    }
}
