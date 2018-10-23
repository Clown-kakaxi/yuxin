package com.yuchengtech.bob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 系统资源表，包括菜单、按钮、数据列权限等
 */
@Entity
@Table(name = "OCRM_SYS_RESOURCE")
public class SystemResource implements Serializable {

    private static final long serialVersionUID = -5345976426609681557L;

    protected enum ResourceType {
        
        /** 主菜单条 */
        MENUBAR,
        
        /** 菜单项 */
        MENUITEM,
        
        /** 子菜单 */
        SUBMENU,
        
        /** 菜单分割线 */
        MENULINE,
        
        /** 按钮 */
        BUTTON,
        
        /** 数据项 */
        DATACOLUMN
        
    }
    
    /** 资源ID */
    @Id
    @SequenceGenerator(name = "sequnce_resource", sequenceName = "ID_RESOURCE", initialValue = 10000, allocationSize = 1)
    @GeneratedValue(generator = "sequnce_resource", strategy = GenerationType.SEQUENCE)
    @Column(name = "F_ID")
    private Long id;
    
    /** 上级资源ID，如果没有上级资源则为0 */
    @Column(name = "F_PARENT_ID", nullable = false)
    private Long parentId;
    
    /** 资源类型 */
    @Column(name = "F_TYPE")
    private ResourceType type;

    /** 资源名称（请不要使用中文） */
    @Column(name = "F_NAME", length = 100)
    private String name;
    
    /** 资源标题 */
    @Column(name = "F_CAPTION", length = 200)
    private String caption;
    
    /** 资源URL，如果为数据项则用“表名.字段名” */
    @Column(name = "F_URL", length = 200)
    private String url;
    
    /** 排序，资源的直接下属资源不能超过9999 */
    @Column(name = "F_SORT")
    private int sort;
    
    /** 资源备注 */
    @Column(name = "F_COMMENT", length = 1000)
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
   
}
