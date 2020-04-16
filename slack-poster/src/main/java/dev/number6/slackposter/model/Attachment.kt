package dev.number6.slackposter.model

fun String.asTitleCase(): String {
    return Character.toTitleCase(this[0]).toString() + this.toLowerCase().substring(1)
}

open class Attachment(var fallback: String,// = "#2eb886";
                      var color: String? = null,// = "Optional text that appears above the attachment block";
                      var pretext: String? = null,// = "Not Bobby Tables";
                      var authorName: String? = null,// = "http://flickr.com/bobby/";
                      var authorLink: String? = null,// = "http://flickr.com/icons/bobby.jpg";
                      var authorIcon: String? = null,// = "Slack API Documentation";
                      var title: String? = null,// = "https://api.slack.com/";
                      var titleLink: String? = null,// = "Optional text that appears within the attachment";
                      var text: String? = null,//
                      var fields // = new Field[]{new Field(), new Field(), new Field()};
                      : Array<Field>,// = "http://my-website.com/path/to/image.jpg";
                      var imageUrl: String? = null,// = "http://example.com/path/to/thumb.png";
                      var thumbUrl: String? = null,// = "Slack API";
                      var footer: String? = null,// = "https://platform.slack-edge.com/img/default_application_icon.png";
                      var footerIcon: String? = null,// = 123456789L;
                      var ts: Long? = null) {

    protected fun asTitleCase(input: String): String {
        return Character.toTitleCase(input[0]).toString() + input.toLowerCase().substring(1)

    }
}