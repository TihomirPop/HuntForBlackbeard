package hr.tpopovic.huntforblackbeard.adapter.out;

public class HtmlDocumentationTemplates {

    private HtmlDocumentationTemplates() {
    }

    public static final String HTML_TEMPLATE = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>HuntForBlackbeard</title>
            </head>
            <body>
                <h1>Hunt For Blackbeard docs</h1>
                %s
            </body>
            </html>
            """;

    public static final String CLASS_SECTION_TEMPLATE = """
            <hr>
            <h2 id="%s">%s</h2>
            """;

    public static final String TYPE_TEMPLATE = "Type: %s";

    public static final String EXTENDS_TEMPLATE = "<p>Extends: %s</p>";

    public static final String EXTENDS_TEMPLATE_WITH_LINK = """
            <p>Extends: <a href="#%s">%s</a></p>
            """;

    public static final String LIST_ITEM_TEMPLATE = "<li>%s</li>";

    public static final String LIST_ITEM_TEMPLATE_WITH_LINK = """
            <li><a href="#%s">%s</a></li>
            """;

    public static final String IMPLEMENTS_TEMPLATE = """
            <p>Implemented interfaces:</p>
                <ul>
                    %s
                </ul>
            """;

    public static final String LINK = """
            <a href="#%s">%s</a>
            """;

    public static final String CONSTRUCTORS_TEMPLATE = """
            <p>Constructors:</p>
                <ul>
                    %s
                </ul>
            """;

    public static final String METHODS_TEMPLATE = """
            <p>Methods:</p>
                <ul>
                    %s
                </ul>
            """;

    public static final String FIELDS_TEMPLATE = """
            <p>Attributes:</p>
                <ul>
                    %s
                </ul>
            """;

}
