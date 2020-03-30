package dev.number6.slackposter.model;

class Attachment {

    String fallback = "Required plain-text summary of the attachment.";
    String color;// = "#2eb886";
    String pretext;// = "Optional text that appears above the attachment block";
    String authorName;// = "Not Bobby Tables";
    String authorLink;// = "http://flickr.com/bobby/";
    String authorIcon;// = "http://flickr.com/icons/bobby.jpg";
    String title;// = "Slack API Documentation";
    String titleLink;// = "https://api.slack.com/";
    String text;// = "Optional text that appears within the attachment";
    Field[] fields;// = new Field[]{new Field(), new Field(), new Field()};
    String imageUrl;// = "http://my-website.com/path/to/image.jpg";
    String thumbUrl;// = "http://example.com/path/to/thumb.png";
    String footer;// = "Slack API";
    String footerIcon;// = "https://platform.slack-edge.com/img/default_application_icon.png";
    Long ts;// = 123456789L;

    protected String asTitleCase(String input) {
        return Character.toTitleCase(input.charAt(0)) + input.toLowerCase().substring(1);
    }

}
