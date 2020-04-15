package dev.number6.slackposter.model

open class Attachment {
    var fallback = "Required plain-text summary of the attachment."
    var color // = "#2eb886";
            : String? = null
    @JvmField
    var pretext // = "Optional text that appears above the attachment block";
            : String? = null
    var authorName // = "Not Bobby Tables";
            : String? = null
    var authorLink // = "http://flickr.com/bobby/";
            : String? = null
    var authorIcon // = "http://flickr.com/icons/bobby.jpg";
            : String? = null
    var title // = "Slack API Documentation";
            : String? = null
    var titleLink // = "https://api.slack.com/";
            : String? = null
    var text // = "Optional text that appears within the attachment";
            : String? = null
//    @JvmField
    lateinit var fields // = new Field[]{new Field(), new Field(), new Field()};
            : Array<Field>
    var imageUrl // = "http://my-website.com/path/to/image.jpg";
            : String? = null
    var thumbUrl // = "http://example.com/path/to/thumb.png";
            : String? = null
    var footer // = "Slack API";
            : String? = null
    var footerIcon // = "https://platform.slack-edge.com/img/default_application_icon.png";
            : String? = null
    var ts // = 123456789L;
            : Long? = null

    protected fun asTitleCase(input: String): String {
        return Character.toTitleCase(input[0]).toString() + input.toLowerCase().substring(1)
    }
}