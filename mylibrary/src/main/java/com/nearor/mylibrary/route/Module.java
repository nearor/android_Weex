package com.nearor.mylibrary.route;

import com.nearor.mylibrary.ValueObject;

/**
 *
 * Created by Nearor on 16/7/7.
 */
public class Module extends ValueObject{

    private String name;
    private String path;
    private String description;
    private ModuleType type;

    public Module(String name, String path, String description) {
        this.name = name;
        this.path = path;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public ModuleType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public enum ModuleType{
        ACTIVITY,FRAGMENT
    }
}
