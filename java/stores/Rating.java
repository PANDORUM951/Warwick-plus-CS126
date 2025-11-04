package stores;

import java.time.LocalDateTime;

public class Rating {
    private int userId;
    private int movieLensId;
    private int tmdbId;
    private float rating;
    private LocalDateTime timestamp;
    
    public Rating(int userId, int tmdbId, float rating, LocalDateTime timestamp) {
        this.userId = userId;
        this.movieLensId = -1;
        this.tmdbId = tmdbId;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieLensId() {
        return movieLensId;
    }

    public void setMovieLensId(int movieLensId) {
        this.movieLensId = movieLensId;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
