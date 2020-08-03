package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {
    private Integer fileId;
    private String filename;
    private String filesize;
    private Integer userid;
    private Byte[] filedata;

    public File(Integer fileId, String filename, String filesize, Integer userid, Byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(Byte[] filedata) {
        this.filedata = filedata;
    }
}
