package com.sayalala.wework.wehook.utils;

public class MarkdownBuffer {

    private static final String NEXT_LINE = "\n";
    private StringBuffer buffer = new StringBuffer();

    public MarkdownBuffer h6(String text) {
        buffer.append("###### " + text);
        return this;
    }

    public MarkdownBuffer h5(String text) {
        buffer.append("##### " + text);
        return this;
    }

    public MarkdownBuffer h4(String text) {
        buffer.append("#### " + text);
        return this;
    }

    public MarkdownBuffer h3(String text) {
        buffer.append("### " + text);
        return this;
    }

    public MarkdownBuffer h2(String text) {
        buffer.append("## " + text);
        return this;
    }

    public MarkdownBuffer h1(String h1Text) {
        buffer.append("# " + h1Text);
        return this;
    }

    public MarkdownBuffer code(String code) {
        buffer.append("`" + code + "`");
        return this;
    }

    public MarkdownBuffer link(String link, String url) {
        buffer.append("[" + link + "](" + url + ")");
        return this;
    }

    public MarkdownBuffer text(String text) {
        buffer.append(text);
        return this;
    }

    public MarkdownBuffer quote(String text) {
        buffer.append("> " + text);
        return this;
    }

    public MarkdownBuffer orange(String orangeText) {
        buffer.append("<font color=\"warning\">" + orangeText + "</font>");
        return this;
    }

    public MarkdownBuffer green(String greenText) {
        buffer.append("<font color=\"info\">" + greenText + "</font>");
        return this;
    }

    public MarkdownBuffer gray(String grayText) {
        buffer.append("<font color=\"comment\">" + grayText + "</font>");
        return this;
    }

    public MarkdownBuffer bold(String boldText) {
        buffer.append("**" + boldText + "**");
        return this;
    }

    public MarkdownBuffer nextLine() {
        buffer.append(NEXT_LINE);
        return this;
    }

    public MarkdownBuffer quoteEnd() {
        buffer.append(NEXT_LINE).append(NEXT_LINE);
        return this;
    }

    @Override
    public String toString() {
        return this.buffer.toString();
    }


}
