package com.triara.jarperreport.beans;

import com.triara.jarperreport.constants.Constants;

public class JasperParamJsonBean {

    private String name;
    private String description;
    private String valueClassName;

    public JasperParamJsonBean(String name, String description, String valueClassName) {
        this.name = name == null ? Constants.EMPTY_STRING : name;
        this.description = description == null ? Constants.EMPTY_STRING : description;
        this.valueClassName = valueClassName == null ? Constants.EMPTY_STRING : valueClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValueClassName() {
        return valueClassName;
    }

    public void setValueClassName(String valueClassName) {
        this.valueClassName = valueClassName;
    }
}
