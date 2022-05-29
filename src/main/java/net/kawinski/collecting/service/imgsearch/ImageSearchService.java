package net.kawinski.collecting.service.imgsearch;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.utils.WebClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.enterprise.util.TypeLiteral;
import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ImageSearchService {

    public void upload(File imageFile, Long imgId, Long elementId) throws IOException {
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(Resources.IMAGE_MATCH_BASE_URL + "/upload");

            final MultipartEntityBuilder bodyBuilder = MultipartEntityBuilder.create();
            bodyBuilder.addTextBody("img_id", imgId.toString(), ContentType.TEXT_PLAIN);
            String metadata = JsonbBuilder.create().toJson(new ImageSearchMetadata(imgId.toString(), elementId.toString()));
            bodyBuilder.addTextBody("metadata", metadata, ContentType.TEXT_PLAIN);
            bodyBuilder.addBinaryBody("file", imageFile);
            httpPost.setEntity(bodyBuilder.build());

            try(final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                checkResponse(response, Response.Status.OK);
            }
        }
    }

    public List<ImageSearchResult> search(final String fileName, final String contentTypeStr, final byte[] image) {
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(Resources.IMAGE_MATCH_BASE_URL + "/search");

            final MultipartEntityBuilder bodyBuilder = MultipartEntityBuilder.create();
            final ContentType contentType = WebClientUtils.getContentType(contentTypeStr);
            bodyBuilder.addBinaryBody("file", image, contentType, fileName);
            httpPost.setEntity(bodyBuilder.build());

            try(final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                final HttpEntity responseEntity = checkResponse(response, Response.Status.OK);

                final List<ImageSearchResult> results = parseSearchResult(responseEntity);
                log.info("Search results:");
                for(final ImageSearchResult result : results) {
                    log.info("- {}", result);
                }
                return results;
            }
        } catch (Exception ex) {
            throw new RuntimeException("ImageSearch failed", ex);
        }
    }

    private static List<ImageSearchResult> parseSearchResult(final HttpEntity entity) throws Exception {
        try(final Jsonb jsonb = JsonbBuilder.create()) {
            final TypeLiteral<List<ImageSearchResult>> resultType = new TypeLiteral<List<ImageSearchResult>>(){};
            return jsonb.fromJson(entity.getContent(), resultType.getType());
//            return (List<ImageSearchResult>) jsonb.fromJson(entity.getContent(), ImageSearchResult.class);
        }
    }

    public void delete(Long uploadId) throws IOException {
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(Resources.IMAGE_MATCH_BASE_URL + "/delete");

            final MultipartEntityBuilder bodyBuilder = MultipartEntityBuilder.create();
            bodyBuilder.addTextBody("img_id", uploadId.toString(), ContentType.TEXT_PLAIN);
            httpPost.setEntity(bodyBuilder.build());

            try(final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                checkResponse(response, Response.Status.OK);
            }
        }
    }

    private HttpEntity checkResponse(CloseableHttpResponse response, Response.Status... expectedStatuses) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        log.info("status: {}, response: {}", statusCode, responseEntity);

        if (Arrays.stream(expectedStatuses)
                .noneMatch(exp -> exp.getStatusCode() == statusCode)) {
            throw new IOException("Unexpected response from the server. status: " + statusCode);
        }
        return responseEntity;
    }
}
