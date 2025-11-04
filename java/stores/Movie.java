package stores;

import java.time.LocalDate;

import structures.*;

public class Movie {

    private boolean adult;
    private Collection belongsToCollection;
    private long budget;
    private Genre[] genres;
    private String homepage;
    private int tmdbId;
    private String imdbId;
    private String originalLanguage; 
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private List<Company> productionCompanies;
    private List<String> productionCountries;
    private LocalDate release;
    private long revenue;
    private double runtime;
    private String[] languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;

    public Movie (int id, String title, String originalTitle, String overview, String tagline, String status, 
    Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, 
    double runtime, String homepage, boolean adult, boolean video, String poster){
        this.adult = adult;
        this.belongsToCollection = new Collection(-1, "", "", "");
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.tmdbId = id;
        this.imdbId = "";
        this.originalLanguage = originalLanguage; 
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = 0.0d;
        this.posterPath = poster;
        this.productionCompanies = new List<>();
        this.productionCountries = new List<>();
        this.release = release;
        this.revenue = revenue;
        this.runtime = runtime;
        this.languages = languages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.voteAverage = 0.0d;
        this.voteCount = 0;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public Collection getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Collection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return tmdbId;
    }

    public void setId(int id) {
        this.tmdbId = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbID) {
        this.imdbId = imdbID;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster() {
        return posterPath;
    }

    public void setPoster(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<String> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public double getRuntime() {
        return runtime;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }



}