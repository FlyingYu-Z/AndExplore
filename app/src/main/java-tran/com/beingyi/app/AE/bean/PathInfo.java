package com.beingyi.app.AE.bean;

import com.beingyi.app.AE.application.MyApplication;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;


@Table(name = "person", onCreated = "")
public class PathInfo {

    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private int id;


    @Column(name = "Path")
    private String Path;

    public PathInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }



}
