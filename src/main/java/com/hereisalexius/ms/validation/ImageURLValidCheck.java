package com.hereisalexius.ms.validation;

public class ImageURLValidCheck extends ValidCheck {
    public ImageURLValidCheck() {
        super("imageUrl", "(http(s?):/)(/[^/]+)+\\.(?:jpg|gif|png)");
    }
}
