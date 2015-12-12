package io.liamju.tangshi.entity;

/**
 * 历史记录和收藏记录的实体类
 *
 * @author LiamJu
 * @version 1.0
 * @since 15/12/6
 */
public class Footprint {
    public static final String _ID = "id";
    public static final String _TITLE = "title";
    public static final String _CREATED_TIME = "created_time";
    public static final String _TYPE = "type";
    public static final int VALUE_COLLECTION = 1;
    public static final int VALUE_HISTORY = 0;
    public static final String TABLE_NAME = "footprint_tb";

    private long id;
    private String title;
    private long createdTime;
    private int type;

    public Footprint(long id, String title, long createdTime, int type) {
        this.id = id;
        this.title = title;
        this.createdTime = createdTime;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
