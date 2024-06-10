package com.play.music.song;

public class PlaySong {
    private int id;
    private String title,artistName,songUrl,avtUrl;

    public PlaySong(int id, String title, String artistName, String songUrl, String avtUrl) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.songUrl = songUrl;
        this.avtUrl = avtUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getAvtUrl() {
        return avtUrl;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl = avtUrl;
    }
}
