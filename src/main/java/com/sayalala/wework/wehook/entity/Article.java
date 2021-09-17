package com.sayalala.wework.wehook.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Article {

    private String title;

    private String description;

    private String url;

    private String picurl;

}
