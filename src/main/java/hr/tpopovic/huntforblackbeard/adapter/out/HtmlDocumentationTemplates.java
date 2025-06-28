package hr.tpopovic.huntforblackbeard.adapter.out;

public class HtmlDocumentationTemplates {

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

    public final String CLASS_SECTION_TEMPLATE = """
            <hr>
                <h2>%s</h2>
            """;

    public final String EXTENDS_TEMPLATE = "<p>Extends: %</p>";

    public final String LIST_ITEM_TEMPLATE = "<li>%s</li>";

    public final String IMPLEMENTS_TEMPLATE = """
            <p>Implemented interfaces:</p>
                <ul>
                    %s
                </ul>
            """;

    public final String CONSTRUCTORS_TEMPLATE = """
            <p>Constructors:</p>
                <ul>
                    %s
                </ul>
            """;

    public final String METHODS_TEMPLATE = """
            <p>Methods:</p>
                <ul>
                    %s
                </ul>
            """;

    public final String ATTRIBUTES_TEMPLATE = """
            <p>Attributes:</p>
                <ul>
                    %s
                </ul>
            """;

}
