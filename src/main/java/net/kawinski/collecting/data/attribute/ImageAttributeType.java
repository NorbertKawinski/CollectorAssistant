package net.kawinski.collecting.data.attribute;

public class ImageAttributeType implements BaseAttributeType<String> {
    @Override
    public boolean canParse(final String value) {
        // TODO: Some validation would be cool.
        // Like: if file.exists(value);
        // But the issue is that we need to @Inject MediaService to query for file location.
        // For now, we can just return 'true' because users don't enter the values,
        // but they upload images, then only once image uploads successfully the raw value is modified to represent the path.
        // In other words, it's the server who's writing into this field, not the user so we can assume that the path is correct.
        return true;
    }

    @Override
    public String parse(final String value) {
        return value;
    }

    @Override
    public String toString(final String value) {
        return value;
    }
}
