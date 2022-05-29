package net.kawinski.collecting.service.imgsearch;

import lombok.Data;

@Data
public class ImageSearchResult {

    private String id;
    private String path;
    private double score;
    private double dist;
    private ImageSearchMetadata metadata;
}
