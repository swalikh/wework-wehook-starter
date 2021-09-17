package com.sayalala.wework.wehook.utils;

public class TextBuffer {

    private static final String NEXT_LINE = "\n";

    private StringBuffer buffer = new StringBuffer();

    public TextBuffer nextLine() {
        buffer.append( NEXT_LINE);
        return this;
    }

    public TextBuffer append(String text) {
        buffer.append(text);
        return this;
    }

    @Override
    public String toString() {
        return this.buffer.toString();
    }
}
