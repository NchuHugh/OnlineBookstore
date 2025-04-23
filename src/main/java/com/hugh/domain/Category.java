package com.hugh.domain;

/**
 * 图书分类实体类
 * 对应数据库中的t_category表
 */
public class Category {
    /**
     * 分类ID，主键，自增
     */
    private Integer categoryId;
    
    /**
     * 分类名称，唯一
     */
    private String name;
    
    /**
     * 分类描述
     */
    private String description;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
